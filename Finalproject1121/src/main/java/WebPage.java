import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.htmlunit.FailingHttpStatusCodeException;

public class WebPage {
	public String url;
	public String name;
	public WordCounter counter;
	public FetchContent crawler;
	public double plScore;
	public double score;
	public ArrayList<String> urls;
	public boolean isLast = false;
	private String content;
	private JSPFetch Jsp;
	private int runtime = 0;

	public WebPage(String url, String name) throws IOException {
		this.url = url;
		this.name = name;
		this.crawler = new FetchContent(url);
		this.content = this.crawler.getHtml();
		this.counter = new WordCounter(content);
		this.urls = new ArrayList<String>();
		plScore = 0;
		score = 0;
	}
	public String getName(){
		return name;
	}
	public String getUrl() {
		return url;
	}
	public ArrayList<String> getChildUrl() {
		for (String subUrl : crawler.getUrls()) {
			if (subUrl.isEmpty()) {
				continue;
			}
			urls.add(subUrl);
		}
		return urls;
	}
	public void setScore(ArrayList<Keyword> keywords)
			throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// YOUR TURN
//		1. calculate the score of this webPage
		if (runtime == 0) {
			score = 0;
			for (Keyword k : keywords) {
				score += k.weight * counter.countKeyword(k.name);
			}
			if (!isLast) {
				if (crawler.isJs() || score <= 10) {
					score = 0;
					System.out.println(url);
//			System.out.println(content);
					Jsp = new JSPFetch(url);
					this.content = Jsp.getXml();
					this.counter = new WordCounter(content);
					for (Keyword k : keywords) {
						score += k.weight * counter.countKeyword(k.name);
					}
				}
				score += plScore;
				runtime++;
			}
		}
	}

}