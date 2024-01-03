import java.io.IOException;
import java.net.MalformedURLException;

import org.htmlunit.BrowserVersion;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;

import org.htmlunit.BrowserVersion;
import org.htmlunit.FailingHttpStatusCodeException;
import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;

public class JSPFetch {
    private String url;
    private String q;

    public JSPFetch(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        this.url = url;
        WebClient wc = new WebClient(BrowserVersion.CHROME);
        wc.getOptions().setUseInsecureSSL(true);
        wc.getOptions().setJavaScriptEnabled(true);
        wc.getOptions().setCssEnabled(false);
        wc.getOptions().setThrowExceptionOnScriptError(false); 
        wc.getOptions().setTimeout(100000);
        wc.getOptions().setDoNotTrackEnabled(false);


        HtmlPage page;
        try {
            page = wc.getPage(this.url);
            q = page.asXml().toString();
        } catch (FailingHttpStatusCodeException e) {
            System.out.println("Error loading page: " + e.getMessage());
            q = "Error loading page.";
        }

    }

    public String getXml() {
        return q;
    }
}



