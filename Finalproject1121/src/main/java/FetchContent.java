import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.*;

public class FetchContent {
	private String urlStr;
	private String content;
	private static int defaultTimeout = 20000;
	private boolean istimeO = false ;
	public FetchContent(String urlStr) {
		this.urlStr = urlStr;
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
			Document doc = Jsoup.connect(urlStr).userAgent(userAgents[rnd]).sslSocketFactory(socketFactory())
					.ignoreHttpErrors(true).timeout(defaultTimeout).get(); 
			content = doc.html();
		} catch (java.net.SocketTimeoutException e) {
			istimeO = true;
		}
		return content;
	}
	public boolean isTimeO() {
		return istimeO;
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
