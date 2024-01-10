import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/TestProject")
public class TestProject extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public TestProject() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        
        String[][] test = new String[100][4];
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            String requestUri = request.getRequestURI();
            request.setAttribute("requestUri", requestUri);
            request.getRequestDispatcher("Search.jsp").forward(request, response);
            return;
        }

        
        String searchNumParam = request.getParameter("searchNum");
        int searchNum = searchNumParam != null ? Integer.parseInt(searchNumParam) : 10; // default: 10
        
        /*CreateNode node = new CreateNode(keyword,searchNum);
        String[][] s = node.getOrderList();*/
        String[][] orderList = new String[searchNum][3];
		WebScraper scraper = new WebScraper(keyword);
		
		String text = null;
		text += ".edu.tw 官網";
		
		WebPage rootPages = new WebPage("https://www.google.com/search?q=" + keyword + text + "&oe=utf8&num=100",
				"SearchPage");

		WebTree trees = new WebTree(rootPages);
		scraper.get();
       
    	ArrayList<String> urls = new ArrayList<>();
    	ArrayList<String> titles = new ArrayList<>();
    	ArrayList<String> webNames = new ArrayList<>();
    	for(String u:scraper.getUrl()) {
    		urls.add(u);
    	}
    	System.out.println("urls.size()"+urls.size());
    	for(String t:scraper.getTitle()) {
    		titles.add(t);
    	}
    	System.out.println("titles.size()"+titles.size());
    	for(String w:scraper.getWebName()) {
    		webNames.add(w);
    	}
    	System.out.println("NoR"+scraper.getNoR());
    	if (searchNum > scraper.getNoR()) {
    		searchNum = scraper.getNoR();
		}
    	
		if (urls.isEmpty()) {
			System.out.println("error");
			scraper.get();
		}
		int listSize = Math.min(urls.size(), titles.size());
		
		System.out.println("titles.size()"+titles.size());
		System.out.println("listSize"+listSize);
		int displayNum = 0;
		for (int i = 0; i < scraper.getNoR(); i++) {
//			tree.root.addChild(new WebNode(new WebPage(urls.get(i),titles.get(i))));
//			if (urls.get(i).contains("edu.tw") && !urls.get(i).contains(".pdf") && !urls.get(i).contains("psy.fju")&&!urls.get(i).contains(".nchu")) {
			if (urls.get(i).contains("edu.tw") && !urls.get(i).contains(".pdf")) {
				trees.root.addChild(new WebNode(new WebPage(urls.get(i), titles.get(i))));
//				tree.root.addChild(new WebNode(new WebPage("http://soslab.nccu.edu.tw/Projects.html", "Projects")));
//				tree.root.children.get(1).addChild(new WebNode(new WebPage("https://vlab.cs.ucsb.edu/stranger/", "Stranger")));
				System.out.println(trees.root.children.get(displayNum).webPage.url + " " + displayNum);
				trees.root.children.get(displayNum).webPage.plScore = 100 * (searchNum - displayNum);
				System.out.println(trees.root.children.get(displayNum).webPage.plScore);
				for (String temp : trees.root.children.get(displayNum).webPage.getChildUrl()) {
//					trees.root.children.get(displayNum).addChild(new WebNode(new WebPage(temp, webNames.get(i)+" subWeb")));
					trees.root.children.get(displayNum).addChild(new WebNode(new WebPage(temp, temp)));
				}
				if (urls.get(i).contains(".html")) {

					test[i][0] = webNames.get(i);
					test[i][1] = titles.get(i);
					test[i][2] = urls.get(i);
				}
				if (titles.get(i).contains(text)) {
					test[i][3] = "detect!";
				}
				displayNum++;
				if (displayNum >= searchNum) {
					break;
				}
			}

		}
		for (int i = 0; i < listSize; i++) {
		    if (urls.get(i).contains(".edu") && (urls.get(i).contains(".tw") || urls.get(i).contains(".com"))) {
		        trees.root.addChild(new WebNode(new WebPage(urls.get(i), titles.get(i))));
		        System.out.println("Added to tree: " + urls.get(i));
		    } else {
		        System.out.println("Skipped: " + urls.get(i));
		    }
		}


		System.out.println("Sorting");
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		keywords.add(new Keyword("大學", 1));
		keywords.add(new Keyword("系", 1));
		keywords.add(new Keyword("所", 1));
		keywords.add(new Keyword("系所", 1));
		keywords.add(new Keyword("研究所", 1));
		keywords.add(new Keyword("台灣", 1));
		keywords.add(new Keyword("官網", 1));
		keywords.add(new Keyword("首頁", 1));
		keywords.add(new Keyword("校徽", 1));
		keywords.add(new Keyword("網站導覽", 10));
		keywords.add(new Keyword("校友", 10));
		keywords.add(new Keyword("另開新視窗", 10));
		keywords.add(new Keyword("行政單位", 8));
		keywords.add(new Keyword("圖書館", 7));

		trees.setPostOrderScore(keywords,keyword);
		trees.eularPrintTree();
		
		int i = 0;
		for (WebNode w : trees.getSortWeb()) {
			if (i < orderList.length) {
			    orderList[i][0] = w.webPage.name;
			    //System.out.println(orderList[i][0]);
			    orderList[i][1] = w.webPage.url;
			    //System.out.println(orderList[i][1]);
			    orderList[i][2] = "" + w.nodeScore;
			    //System.out.println(orderList[i][2]);
			    i++;
			} else {
			    break;
			}

		}
        
        /*GoogleQuery google = new GoogleQuery(keyword, searchNum); 
        HashMap<String, String> query = google.query();
	
        String[][] s = new String[query.size()][2];*/
        
        request.setAttribute("query", orderList);
        /*int num = 0;
        for (Entry<String, String> entry : query.entrySet()) {
            s[num][0] = entry.getKey();
            s[num][1] = entry.getValue();
            num++;
        }*/
        request.getRequestDispatcher("googleitem.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}