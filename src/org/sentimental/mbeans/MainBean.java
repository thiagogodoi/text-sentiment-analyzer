package org.sentimental.mbeans;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.sentimental.chart.SentimentalChart;
import org.sentimental.classifier.ClassifierClient;
import org.sentimental.text.ParagraphBreaker;

@ManagedBean(name="mBean")
@RequestScoped
public class MainBean {

	private static final int PARAGRAPH_MAX_SIZE = 1000;

	private String text;
	
	private String errorMessage = " ";
	
	private SentimentalChart sentimentalChart;
	
	private ClassifierClient classifierClient;

	public String populateChart() {
		
		this.errorMessage = " ";
		
		sentimentalChart = new SentimentalChart();

		if (text != null && !text.isEmpty() && classifierClient != null) {
			Calendar timeStart = Calendar.getInstance();
			try{
				List<String> paragraphList = ParagraphBreaker.breakTextIntoParagraphs(text);
				
				int paragraphNum=1;
				for(String paragraph:paragraphList){
					if(paragraph.length()>PARAGRAPH_MAX_SIZE){
						paragraph = paragraph.substring(0, 1000);
					}
					sentimentalChart.addSeries(paragraphNum++, classifierClient.getClassification(paragraph));
				}

				this.sentimentalChart.addSeriesToChart();
				return "chart";
			}
			catch(Exception e){
				Logger.getLogger(MainBean.class.getName()).log(Level.SEVERE, "Error calling the watson API", e);
				setErrorMessage("Erro chamando aplicação watson, tente novamente mais tarde."); 
				return "index";
			}
			finally {
				Calendar timeEnd = Calendar.getInstance();
				Logger.getLogger(MainBean.class.getName()).log(Level.INFO, "Time spent calling watson (mileseconds) :"
						+ (timeEnd.getTimeInMillis() - timeStart.getTimeInMillis()));
			}
		} else {
			setErrorMessage("Your text is empty, please fill the text and try again."); 
			return "index";
		}
	}

	@PostConstruct
	public void init() {
		this.classifierClient = new ClassifierClient();

	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SentimentalChart getSentimentalChart() {
		return sentimentalChart;
	}

	public void setSentimentalChart(SentimentalChart sentimentalChart) {
		this.sentimentalChart = sentimentalChart;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
}
