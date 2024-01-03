import java.io.IOException;
import java.util.ArrayList;
//import java.util.Collections;

public class WebTree {
	public WebNode root;
	public ArrayList<WebNode> SortWeb;

	public WebTree(WebPage rootPage) {
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords,String searchText) throws IOException {
		setPostOrderScore(root, keywords,searchText);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords ,String searchText) throws IOException {
		startNode.setNodeScore(keywords);
		for (WebNode w : startNode.children) {
			w.setNodeScore(keywords);
			
		}

	}

	public void rankNode() {
		rankNode(root);
	}

	public void rankNode(WebNode startNode) {
		ArrayList<WebNode> rank = new ArrayList<WebNode>();
		for (WebNode w : startNode.children) {
			rank.add(w);
		}
	}

	public ArrayList<WebNode> getSortWeb() {
		SortWeb = new ArrayList<WebNode>();
		for (WebNode w : root.children) {
			SortWeb.add(w);
		}
		sort(SortWeb);

		return SortWeb;
	}

	public void eularPrintTree() {
		eularPrintTree(root);
	}

	void eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		System.out.print("(");
		System.out.print(startNode.webPage.name + "," + startNode.nodeScore);

		for (WebNode w : startNode.children) {
			eularPrintTree(w);
		}

		System.out.print(")");

		if (startNode.isTheLastChild())
			System.out.print("\n" + repeat("\t", nodeDepth - 2));
	}

	public void sort(ArrayList<WebNode> lst) {
		if (lst.size() == 0) {
			System.out.println("InvalidOperation");
		} else {
			quickSort(lst, 0, lst.size() - 1);
		}
	}

	private void quickSort(ArrayList<WebNode> lst, int leftbound, int rightbound) {
		if (leftbound < rightbound) {

			double pivot = lst.get(rightbound).nodeScore;
			for (int i = 0; i < rightbound; i++) {
				if (lst.get(i).nodeScore < pivot) {
					for (int j = rightbound - 1; j > leftbound; j--) {

						if (i == j) {
							swap(lst,rightbound, i);
							quickSort(lst,leftbound, i - 1);
							quickSort(lst,i, rightbound);
							return;
						}
						if (lst.get(j).nodeScore > pivot) {
							swap(lst,i, j);
							break;

						}
					}
				}
			}
		}
	}

	private void swap(ArrayList<WebNode> lst,int aIndex, int bIndex) {
		WebNode temp = lst.get(aIndex);
		lst.set(aIndex, lst.get(bIndex));
		lst.set(bIndex, temp);
	}

	private String repeat(String str, int repeat) {
		String retVal = "";
		for (int i = 0; i < repeat; i++) {
			retVal += str;
		}
		return retVal;
	}
}