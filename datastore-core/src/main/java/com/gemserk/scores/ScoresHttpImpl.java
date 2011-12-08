package com.gemserk.scores;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gemserk.datastore.profiles.Profile;

public class ScoresHttpImpl implements Scores {

	protected static final Logger logger = LoggerFactory.getLogger(ScoresHttpImpl.class);

	private static String submitScoreUrl = "/submit";

	private static String queryScoresUrl = "/scores";

	String gameKey;

	private URI baseUri;

	private ScoreSerializer scoreSerializer;

	public ScoresHttpImpl(String gameKey, String baseUrl) {
		this(gameKey, baseUrl, new ScoreSerializerJSONImpl());
	}

	public ScoresHttpImpl(String gameKey, String baseUrl, ScoreSerializer scoreSerializer) {
		this.gameKey = gameKey;
		this.scoreSerializer = scoreSerializer;

		try {
			baseUri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending) {
		return getOrderedByPoints(tags, limit, ascending, Range.All);
	}

	@Override
	public Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending, Range range) {
		try {
			HttpClient httpClient = new DefaultHttpClient();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("gameKey", gameKey));

			for (String tag : tags)
				params.add(new BasicNameValuePair("tag", tag));

			params.add(new BasicNameValuePair("limit", Integer.toString(limit)));
			params.add(new BasicNameValuePair("ascending", Boolean.toString(ascending)));

			params.add(new BasicNameValuePair("range", getRangeString(range)));

			String encodedParams = URLEncodedUtils.format(params, "UTF-8");

			URI uri = URIUtils.resolve(baseUri, queryScoresUrl + "?" + encodedParams);

			HttpGet httpget = new HttpGet(uri);

			if (logger.isDebugEnabled())
				logger.debug("Scores query uri: " + httpget.getURI());

			HttpResponse response = httpClient.execute(httpget);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK)
				throw new RuntimeException("failed to retrieve scores : " + statusLine.toString());

			String scoresJson = EntityUtils.toString(response.getEntity());

			if (logger.isDebugEnabled())
				logger.debug("Scores json retrieved from server: " + scoresJson);

			Collection<Score> scores = parseData(scoresJson);

			return scores;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getRangeString(Range range) {
		switch (range) {
		case Day:
			return "day";
		case Week:
			return "week";
		case Month:
			return "month";
		default:
			return "all";
		}
	}

	Collection<Score> parseData(String scores) {
		return scoreSerializer.parse(scores);
	}

	String mapToJson(Map<String, Object> data) {
		return scoreSerializer.serializeScoreData(data);
	}

	Profile parseProfile(String profileJson) {
		return null;
	}

	@Override
	public String submit(Score score) {
		return submit(null, score);
	}

	@Override
	public String submit(String profilePrivateKey, Score score) {
		try {
			HttpClient httpClient = new DefaultHttpClient();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("gameKey", gameKey));
			params.add(new BasicNameValuePair("name", score.getName()));
			
			if (profilePrivateKey != null)
				params.add(new BasicNameValuePair("profilePrivateKey", profilePrivateKey));

			params.add(new BasicNameValuePair("points", Long.toString(score.getPoints())));

			for (String tag : score.getTags())
				params.add(new BasicNameValuePair("tag", tag));

			params.add(new BasicNameValuePair("data", mapToJson(score.getData())));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			HttpPost httppost = new HttpPost(URIUtils.resolve(baseUri, submitScoreUrl));
			httppost.setEntity(entity);

			if (logger.isInfoEnabled())
				logger.info("submitting new score to the server: " + score);

			HttpResponse response = httpClient.execute(httppost);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK)
				throw new RuntimeException("failed to submit score: " + statusLine.toString());

			HttpEntity responseEntity = response.getEntity();
			String responseEntityContent = EntityUtils.toString(responseEntity);
			if (logger.isInfoEnabled())
				logger.info("new score submitted: score.id - " + responseEntityContent);
			return responseEntityContent;

		} catch (Exception e) {
			throw new RuntimeException("failed to submit score", e);
		}

	}

}
