import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.*;

public class FetchContent {
	private String urlStr;
	private String content;
//	private URL url;
	private static int defaultTimeout = 20000;
//	private long totalDuration = 0;
//	private int requestCount = 0;
	private boolean istimeO = false;
	private boolean isJs = false;
	private ArrayList<String> tempUrls;
	private ArrayList<String> urls;
	private Elements links;

//	private JSPFetch JSP;
	public FetchContent(String urlStr) {
		this.urlStr = urlStr;
		this.urls = new ArrayList<String>();
		this.tempUrls = new ArrayList<String>();
	}

	public String getHtml() throws IOException {
		String userAgents[] = {
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36", };
		int rnd = (int) (Math.random() * userAgents.length);
		try {
			Document doc = Jsoup.connect(urlStr).userAgent(userAgents[rnd]).followRedirects(false)
					.sslSocketFactory(socketFactory()).ignoreHttpErrors(true).timeout(defaultTimeout).get();
//			Document doc = Jsoup.connect(urlStr).userAgent(userAgents[rnd]).sslSocketFactory(socketFactory())
//					.ignoreHttpErrors(true).timeout(calculateAdaptiveTimeout()).get();
//			long duration = System.currentTimeMillis() - startTime;
//			totalDuration += duration;
//			requestCount++;
//			if(doc.select("head").text().contains("javascript")) {
//				System.out.println("JS!");
//			}
//			if (doc.select("head").select("script").attr("type").contains("javascript")) {
//				System.out.println(doc.select("head").select("script").attr("type"));
//				System.out.println(doc.select("head").select("title").text());
//				JSP=new JSPFetch(urlStr);
//			}
			content = doc.html();
			links = doc.select("li");
//			System.out.println(doc.select("noscript").text());
//			System.out.println(doc.select("script").attr("language"));
			if (doc.select("noscript").text().contains("javascrip")
					|| doc.select("script").attr("language").contains("javascript")) {
				isJs = true;
			}
		} catch (java.net.SocketTimeoutException e) {
			istimeO = true;
		} catch (java.net.UnknownHostException ue) {
			System.out.println(urlStr + "--UnknownHostException");
		}
		return content;
	}
	
	public ArrayList<String> getUrls() {
		if (links.isEmpty()) {
			System.out.println("no links!");
		} else {
			for (Element link : links) {
				String tempUrl = "";
				tempUrl += link.select("a").attr("href");
				if (tempUrl.length() > 10) {
					if (tempUrl.substring(0, 4).equals("http") && tempUrl.contains("edu.tw")
							&& !tempUrl.substring(tempUrl.length() - 8, tempUrl.length()).contains(".")) {
//						System.out.println(tempUrl);
						tempUrls.add(tempUrl);
					} else if (tempUrl.substring(0, 1).equals("/")) {
						tempUrl = urlStr.substring(0, urlStr.indexOf("edu.tw/") + 7)
								+ tempUrl.substring(1, tempUrl.length());
//						System.out.println(tempUrl);
						tempUrls.add(tempUrl);

					}
				}
			}
//			if(tempUrls.size()>=2) {
//				for(int i =0;i<2;i++) {
////					urls.add(tempUrls.get((int) (Math.random() *tempUrls.size())));
//					urls.add(tempUrls.get(i));
//				}
//			}
//			else {
//				for(int i =0;i<2-tempUrls.size();i++) {
//					urls.add(urlStr);
//				}
//			}

		}
		if (tempUrls.size() >= 2) {
			for (int i = 0; i < 2; i++) {
//				urls.add(tempUrls.get((int) (Math.random() *tempUrls.size())));
				urls.add(tempUrls.get(i));
			}
		} else {
			for (int i = 0; i < 2 - tempUrls.size(); i++) {
				urls.add(urlStr);
			}
		}
		return urls;
	}

	public boolean isTimeO() {
		return istimeO;
	}

	public boolean isJs() {
		return isJs;
	}

	static private SSLSocketFactory socketFactory() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };

		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLSocketFactory result = sslContext.getSocketFactory();

			return result;
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			throw new RuntimeException("Failed to create a SSL socket factory", e);
		}
	}
}
