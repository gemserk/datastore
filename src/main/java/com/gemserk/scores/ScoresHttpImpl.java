package com.gemserk.scores;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

public class ScoresHttpImpl implements Scores {

	protected static final Logger logger = LoggerFactory.getLogger(ScoresHttpImpl.class);

	String gameKey;

	String submitUrl;

	String scoresUrl;

	private URI scoresUri;

	public ScoresHttpImpl(String gameKey, String baseUrl) {
		this.gameKey = gameKey;
		
		this.submitUrl = baseUrl + "/submit";
		this.scoresUrl = baseUrl + "/scores";
		
		try {
			scoresUri = new URI(scoresUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Collection<Score> getOrderedByPoints(Set<String> tags, int limit, boolean ascending) {

		try {
			HttpClient httpClient = new DefaultHttpClient();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("gameKey", gameKey));

			for (String tag : tags)
				params.add(new BasicNameValuePair("tag", tag));

			params.add(new BasicNameValuePair("limit", Integer.toString(limit)));
			params.add(new BasicNameValuePair("ascending", Boolean.toString(ascending)));

			String encodedParams = URLEncodedUtils.format(params, "UTF-8");
			
			URI uri = URIUtils.resolve(scoresUri, "?" + encodedParams);

			HttpGet httpget = new HttpGet(uri);

			logger.debug("Scores query uri: " + httpget.getURI());
			
			HttpResponse response = httpClient.execute(httpget);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				logger.error("failed to retrieve scores : " + statusLine.toString());
				throw new RuntimeException("failed to retrieve scores : " + statusLine.toString());
			}

			String scoresJson = EntityUtils.toString(response.getEntity());

			logger.debug("Scores json retrieved from server: " + scoresJson);

			Collection<Score> scores = parseData(scoresJson);

			return scores;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("unchecked")
	Collection<Score> parseData(String scoresJsonString) {
		return JSONArray.toCollection(JSONArray.fromObject(scoresJsonString), Score.class);
	}

	String mapToJson(Map<String, Object> data) {
		return JSONObject.fromObject(data).toString();
	}

	@Override
	public String submit(Score score) {

		try {
			HttpClient httpClient = new DefaultHttpClient();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("gameKey", gameKey));

			params.add(new BasicNameValuePair("name", score.getName()));
			params.add(new BasicNameValuePair("points", Long.toString(score.getPoints())));

			for (String tag : score.getTags())
				params.add(new BasicNameValuePair("tag", tag));

			params.add(new BasicNameValuePair("data", mapToJson(score.getData())));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			HttpPost httppost = new HttpPost(submitUrl);
			httppost.setEntity(entity);
			
			logger.info("submitting new score to the server: " + score);

			HttpResponse response = httpClient.execute(httppost);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				logger.error("failed to submit score: " + statusLine.toString());
				throw new RuntimeException("failed to submit score: " + statusLine.toString());
			}

			HttpEntity responseEntity = response.getEntity();
			String responseEntityContent = EntityUtils.toString(responseEntity);
			logger.info("new score submitted: score.id - " + responseEntityContent);
			return responseEntityContent;

		} catch (Exception e) {
			throw new RuntimeException("failed to submit score", e);
		}

	}

}
