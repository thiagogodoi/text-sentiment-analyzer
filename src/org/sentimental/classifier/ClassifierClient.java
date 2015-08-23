package org.sentimental.classifier;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

public class ClassifierClient {

	private static final String CREDENTIALS_JSON_ELEMENT_NAME = "credentials";
	private static final String NATURAL_LANGUAGE_JSON_ELEMENT_NAME = "natural_language_classifier";
	//To be used in dev-mode
	private static final String VCAP_PROPERTIES = "";
	private static final String CLASSIFIER_ID = "47A225-nlc-215";
	private NaturalLanguageClassifier service;

	public ClassifierClient() {
		this.service = new NaturalLanguageClassifier();
		try {
			String vcapString = System.getenv("VCAP_SERVICES");
			if (vcapString == null || vcapString.isEmpty()) {
				vcapString = VCAP_PROPERTIES;
			}
			JSONObject credentials = getCredentials(vcapString);

			String user = credentials.getString("username");
			String pass = credentials.getString("password");
			String url = credentials.getString("url");

			service.setUsernameAndPassword(user, pass);
			service.setEndPoint(url);

		} catch (JSONException je) {
			Logger.getLogger(ClassifierClient.class.getName()).log(Level.WARNING,
					"Error setting natural language classifier!", je);
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
