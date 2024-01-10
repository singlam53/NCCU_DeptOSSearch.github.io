import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public WebPage webPage;
	public double nodeScore;// This node's score += all its children's nodeScore

	public WebNode(WebPage webPage) {
		this.webPage = webPage;
		this.children = new ArrayList<WebNode>();
	}

	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
		// YOUR TURN
		// 2. calculate the score of this node
		// this method should be called in post-order mode

		// You should do something like:
		// 1.compute the score of this webPage
		// 2.set this score to initialize nodeScore
		// 3.nodeScore must be the score of this webPage
		// plus all children's nodeScore
		nodeScore = 0;
		if (children.isEmpty()) {
			webPage.isLast = true;
		}
		webPage.setScore(keywords);
		nodeScore += webPage.score;
		if (children.isEmpty()) {
//			webPage.score/=10;
			nodeScore /= 10;
			System.out.println(webPage.url + " from node: " + nodeScore + " from page: " + webPage.score);
		}
//		for(WebNode w :children) {
//			w.webPage.setScore(keywords);
//			nodeScore+=w.webPage.score;
//		}
		if (!children.isEmpty()) {
			for (WebNode w : children) {
				w.setNodeScore(keywords);
				nodeScore += w.nodeScore;
			}
		}

	}

	public void addChild(WebNode child) {
		// add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}

	public boolean isTheLastChild() {
		if (this.parent == null)
			return true;
		ArrayList<WebNode> siblings = this.parent.children;

		return this.equals(siblings.get(siblings.size() - 1));
	}

	public int getDepth() {
		int retVal = 1;
		WebNode currNode = this;
		while (currNode.parent != null) {
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}