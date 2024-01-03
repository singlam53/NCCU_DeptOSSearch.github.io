import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.htmlunit.FailingHttpStatusCodeException;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public FetchContent crawler;
	public double score;
	private String content;
	private JSPFetch Jsp;

	public WebPage(String url, String name) throws IOException {
		this.url = url;
		this.name = name;
		this.crawler = new FetchContent(url);
		this.content=this.crawler.getHtml();
		this.counter = new WordCounter(content);
		
	}
	public String getName(){
		return name;
	}
	public String getUrl() {
		return url;
	}

	public void setScore(ArrayList<Keyword> keywords) {
	    score = 0;
	    for (Keyword k : keywords) {
	        score += k.weight * counter.countKeyword(k.name);
	    }

	    if (score == 0 && !crawler.isTimeO()) {
	        try {
	            Jsp = new JSPFetch(url);
	            this.content = Jsp.getXml();
	            this.counter = new WordCounter(content);

	            for (Keyword k : keywords) {
	                score += k.weight * counter.countKeyword(k.name);
	            }
	        } catch (FailingHttpStatusCodeException | IOException e) {
	            System.out.println("An error occurred while processing URL: " + url);
	            System.out.println("Assigning default score of 1 due to error: " + e.getMessage());
	            score = 1; 
	        }
	    }
	}

}