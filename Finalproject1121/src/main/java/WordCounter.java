
public class WordCounter {
	private String content;

	public WordCounter(String content) {
		this.content = content;
	}

	public int BoyerMoore(String T, String P) {
		int i = P.length() - 1;
		int j = P.length() - 1;
		int ans = 0;
		while (i < T.length()) {
			if (T.charAt(i) == P.charAt(j)) {
				if (j == 0) {
					ans++;
					i += P.length();
					j = P.length() - 1;
				} else {
					i--;
					j--;
				}
			} else {
				int last = last(T.charAt(i), P);
				i += P.length() - min(j, 1 + last);
				j = P.length() - 1;
			}
		}
		return ans;
	}

	public int last(char c, String P) {
		for (int i = P.length() - 1; i >= 0; i--) {
			if (P.charAt(i) == c) {
				return i;
			}
		}

		return -1;
	}

	public int min(int a, int b) {
		if (a < b)
			return a;
		else if (b < a)
			return b;
		else
			return a;
	}

	public int countKeyword(String keyword) {
		if (content == null) {
			content = "";
		}
		content = content.toUpperCase();
		keyword = keyword.toUpperCase();
		int retVal = 0;
		retVal = BoyerMoore(content, keyword);

		return retVal;
	}
}