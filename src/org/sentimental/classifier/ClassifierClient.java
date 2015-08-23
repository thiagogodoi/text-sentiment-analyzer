package org.sentimental.classifier;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

public class ClassifierClient {

	private static final String URL_PROPERTY_NAME = "url";
	private static final String PASSWORD_PROPERTY_NAME = "password";
	private static final String USERNAME_PROPERTY_NAME = "username";
	private static final String VCAP_PROPERTIES_NAME = "VCAP_SERVICES";
	private static final String CREDENTIALS_JSON_ELEMENT_NAME = "credentials";
	private static final String NATURAL_LANGUAGE_JSON_ELEMENT_NAME = "natural_language_classifier";
	//To be used in dev-mode
	private static final String VCAP_PROPERTIES = "";
	private static final String CLASSIFIER_ID = "47A225-nlc-215";
	private NaturalLanguageClassifier service;

	public ClassifierClient() {
		this.service = new NaturalLanguageClassifier();
		try {
			String vcapString = System.getenv(VCAP_PROPERTIES_NAME);
			if (vcapString == null || vcapString.isEmpty()) {
				vcapString = VCAP_PROPERTIES;
			}
			JSONObject credentials = getCredentials(vcapString);

			String user = credentials.getString(USERNAME_PROPERTY_NAME);
			String pass = credentials.getString(PASSWORD_PROPERTY_NAME);
			String url = credentials.getString(URL_PROPERTY_NAME);

			service.setUsernameAndPassword(user, pass);
			service.setEndPoint(url);

		} catch (JSONException je) {
			Logger.getLogger(ClassifierClient.class.getName()).log(Level.WARNING,
					"Error setting natural language classifier parameters!", je);
			;
		}
	}

	private JSONObject getCredentials(String vcapString) throws JSONException {
		JSONObject vcap = new JSONObject(vcapString);
		JSONArray naturalLanguageClassArray = vcap.getJSONArray(NATURAL_LANGUAGE_JSON_ELEMENT_NAME);
		JSONObject naturalLanguageClass = naturalLanguageClassArray.getJSONObject(0);
		JSONObject credentials = naturalLanguageClass.getJSONObject(CREDENTIALS_JSON_ELEMENT_NAME);
		return credentials;
	}

	public int getClassification(String text) {
		Classification classification = service.classify(CLASSIFIER_ID, text);
		int topClass = Integer.valueOf(classification.getTopClass());

		return topClass;
	}

}
