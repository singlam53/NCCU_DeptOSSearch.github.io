import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

public class WebScraper {
	private String keyword;
	private String urlStr;
	private Elements results;
	private String link;
	private ArrayList<String> urls;
	private String title;
	private ArrayList<String> titles;
	private String WebName;
	private ArrayList<String> WebNames;
	private int noR = 0;// number of result

	public WebScraper(String keyword) throws IOException {
		// search
		this.keyword = keyword;
		urlStr = "https://www.google.com/search?q=" + this.keyword + "&oe=utf8&num=100";
		init(urlStr);
		urls = new ArrayList<String>();
		titles = new ArrayList<String>();
		WebNames = new ArrayList<String>();
	}



	public void init(String url) throws IOException {
		try {
			String userAgents[] = {
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
	                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36", };
				int rnd = (int) (Math.random() * userAgents.length);
	            Document doc = Jsoup.connect(url).userAgent(userAgents[rnd]).get();

	            results = doc.select("div.g");
	            System.out.println("Searching for " + keyword);
	        } catch (UnsupportedMimeTypeException e) {
	            System.out.println("跳過不支援的url: " + e.getUrl());
	        } catch (HttpStatusException e) {
	            System.out.println("無法獲得的 URL: " + e.getUrl() + "，狀態碼: " + e.getStatusCode());
	        }   
	    }
	public void get() {
	        int c = 0;
	        System.out.println("how many results: " + results);
	        for (Element result : results) {
	            try {
	                
	                String title = result.select("h3").text();
	                String link = result.select("a").get(0).attr("href").replace("/url?q=", "");
	                String WebName = result.select(".VuuXrf").text();
	                String snippet = result.select(".VwiC3b").text();

	                
	                if (!isExcludedFileType(link)) {
	                    urls.add(link);
	                    WebNames.add(WebName);
	                    titles.add(title);
	                    noR++;
	                } else {
	                    break; 
	                }

	                System.out.println("WebName: " + WebName.substring((WebName.length() + 1) / 2));
	                System.out.println("Title: " + title);
	                System.out.println("Link: " + link);
	                System.out.println("Snippet: " + snippet);
	                System.out.println("Position: " + (c + 1));
	                System.out.println("\n");
	            } catch (Exception e) {
	                System.out.println(": " + e.getMessage());
	            }

	            c++; 
	        }

	    }
		private boolean isExcludedFileType(String url) {
			String[] excludedFileTypes = {".pdf", ".doc", ".docx", ".ppt", ".pptx", ".youtube"};
			for (String fileType : excludedFileTypes) {
				if (url.toLowerCase().contains(fileType)) {
					return true;
				}
			}
			return false;
		}

	    public int getNoR() {
	        return noR;
	    }

	    public ArrayList<String> getUrl() throws IOException {
	        return urls;
	    }

	    public ArrayList<String> getWebName() {
	        if (results == null) {
	            return new ArrayList<>();
	        }

	        return WebNames;
	    }

	    public ArrayList<String> getTitle() {
	        if (results == null) {
	            return new ArrayList<>();
	        }

	        return titles;
	    }
}