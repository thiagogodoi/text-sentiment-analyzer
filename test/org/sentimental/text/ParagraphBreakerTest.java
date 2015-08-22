package org.sentimental.text;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ParagraphBreakerTest {

	
	@Test
	public void testBreakTextIntoParagraphsEmptyString() {
		String text = "";
		List<String> paragraphs = ParagraphBreaker.breakTextIntoParagraphs(text);
		assertEquals(0, paragraphs.size());
	}
	
	@Test
	public void testBreakTextIntoParagraphsNullString() {
		String text = null;
		List<String> paragraphs = ParagraphBreaker.breakTextIntoParagraphs(text);
		assertEquals(0, paragraphs.size());
	}
	
	@Test
	public void testBreakTextIntoParagraphsOneParaString() {
		String text = "Testing String.";
		List<String> paragraphs = ParagraphBreaker.breakTextIntoParagraphs(text);
		assertEquals(1, paragraphs.size());
	}
	
	@Test
	public void testBreakTextIntoParagraphsTwoParaString() {
		String text = "Testing \n String. Same paragraph. \n\t Another paragraph.";
		List<String> paragraphs = ParagraphBreaker.breakTextIntoParagraphs(text);
		assertEquals(2, paragraphs.size());
		assertEquals("Testing \n String. Same paragraph", paragraphs.get(0));
		assertEquals("Another paragraph.", paragraphs.get(1));
	}
	
	@Test
	public void testBreakTextIntoParagraphsTwoParaOneHaveTwoNewLines() {
		String text = "Testing \n String. Same paragraph. \n\n\t Another paragraph.";
		List<String> paragraphs = ParagraphBreaker.breakTextIntoParagraphs(text);
		assertEquals(2, paragraphs.size());
		assertEquals("Testing \n String. Same paragraph", paragraphs.get(0));
		assertEquals("Another paragraph.", paragraphs.get(1));
	}

}
