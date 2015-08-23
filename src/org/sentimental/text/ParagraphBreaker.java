package org.sentimental.text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParagraphBreaker {

	private static final String REGEX_FOR_PARAGRAPH = "\\.\\s*\\n+[\\s\\t]*";

	/**
	 * Break a given text into a list of strings, it considerer that a paragraph
	 * is separated from another by a dot, optional spaces, a \n and/or \r, and
	 * unlimited spaces or tab character
	 * 
	 * @param text
	 *            The given text
	 * @return The paragraph list
	 */
	public static List<String> breakTextIntoParagraphs(String text) {

		List<String> paragraphs = new ArrayList<>();

		// Avoid send null parameter to string tokenizer
		if (text == null || text.isEmpty()) {
			return paragraphs;
		}

		// Populate list with all tokens
		String[] paragraphArray = text.split(REGEX_FOR_PARAGRAPH);

		paragraphs.addAll(Arrays.asList(paragraphArray));

		return paragraphs;
	}

}
