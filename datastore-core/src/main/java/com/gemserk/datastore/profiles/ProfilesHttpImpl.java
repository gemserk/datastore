package com.gemserk.datastore.profiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProfilesHttpImpl implements Profiles {

	protected static final Logger logger = LoggerFactory.getLogger(ProfilesHttpImpl.class);

	private static String registerProfileUrl = "/newProfile";

	private URI baseUri;

	ProfileJsonSerializer profileJsonSerializer = new ProfileJsonSerializer();

	public ProfilesHttpImpl(String baseUrl) {
		try {
			baseUri = new URI(baseUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Profile register(String name, boolean guest) {
		try {
			HttpClient httpClient = new DefaultHttpClient();

			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("guest", Boolean.toString(guest)));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			HttpPost httppost = new HttpPost(URIUtils.resolve(baseUri, registerProfileUrl));
			httppost.setEntity(entity);

			if (logger.isInfoEnabled())
				logger.info(MessageFormat.format("registering profile for name = {0} and guest = {1} ", name, guest));

			HttpResponse response = httpClient.execute(httppost);

			StatusLine statusLine = response.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("failed to register profile: " + statusLine.toString());
			}

			HttpEntity responseEntity = response.getEntity();
			String profileJson = EntityUtils.toString(responseEntity);
			if (logger.isInfoEnabled())
				logger.info("new profile registered: " + profileJson);

			return profileJsonSerializer.parse(profileJson);

		} catch (Exception e) {
			throw new RuntimeException("failed to register profile", e);
		}

	}

}
