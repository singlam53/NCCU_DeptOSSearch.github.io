# [Group 4] DeptOS

Member: 111306079 徐偉哲、111306009 葉家妤、111306011 陳若庭、111306045 林俊聲

![image](https://github.com/singlam53/NCCU_DeptOSSearch.github.io/blob/master/Image%20Sources/Deptos.png)

## About this project

### **Motive**

The motivation behind this project is that we have observed that when individuals search for the official website of their academic department by using its name as the search term, the search results tend to prioritize department introductions that are intended for high school students.

As a result, our objective is to create a dedicated search engine specifically designed for locating the official websites of academic departments.

### **Project Objective**

Developing a search engine with a specific emphasis on prioritizing official websites of university departments. This involves:

- Implementing search functionality
- Designing the user interface (UI) for search and search results

### Challenges & Future Prospects

- Problems to be addressed
    - Captcha
    - Loading Time
    - Numbers of Searching Results
- Enhance search results with content from foreign universities
- Make the engine accessible on the public network

## System Introduction

### **“DeptOS”**

This name is a combination of the abbreviations for "Department" and "Official website." It shows that this search engine is specifically created to search the official websites of academic departments.

### **Fuction**

- Input Keywords: Department Names, Academic Fields
- Results: Official websites of Taiwanese academic departments and programs relevant to your search.

### **Use Case and Scenarios**

- Target user:
    - People who want to find the official websites (OS) of departments

- Example:	
output priority:

When entering department ‘psychology’

1. OS of ‘psychology’

2. OS of related credit programs

3. OS of related departments

## System Design

### Key Technology

- **Front End**
    - UI Design:
    Design the visual style of the interface in Figma and generate the CSS code.
    - WebServlet:
    The WebServlet application acts as a connection between the frontend and backend, handling requests and responses. It provides an easy integration for your project.
    - Handling Invalid URLs:
    If you are using Googlequery.java in hw11, some of the returned URLs may be invalid. This is because the function returns a redirect URL with additional parameters from Google. To obtain a valid URL, attempt to remove unnecessary parameters from the redirect URL.
    
    <aside>
    <img src="https://www.notion.so/icons/arrow-southeast_gray.svg" alt="https://www.notion.so/icons/arrow-southeast_gray.svg" width="40px" /> Example
    
    - Redirect URL:
        
        https://zh.wikipedia.org/wiki/%25E7%25B3%2596%25E6%259E%259C%25E5%2582%25B3%25E5%25A5%2587&sa=U&ved=2ahUKEwieg9itx7ODAxWMr1YBHXnJDOIQFnoECAYQAg&usg=AOvVaw1FSsOyrWZ0mdtPAxtVlZCC
        
    - Clean URL:
        
        https://zh.wikipedia.org/wiki/%E7%B3%96%E6%9E%9C%E5%82%B3%E5%A5%87
        
    </aside>
    
    - Try-Catch:
    To prevent disruptions, especially in JSPFetch.java, it is recommended to use try-catch statements.
    - Extracting URLs from Website's HTML:
    When retrieving the HTML of multiple websites and calculating scores based on the content, it is important to exclude .youtube URLs. Analyzing YouTube URLs can cause issues and result in numerous warnings due to HtmlUnit's inability to handle YouTube JavaScript. Therefore, it is advisable to exclude YouTube URLs.
- Back End
    - Searching Subpages:
    Utilize Jsoup's "select" method to search for subpages and include them in the tree.
    - Score Formula:
        
        
        | Word | Score | Word | Score |
        | --- | --- | --- | --- |
        | 網站導覽 | 10 | 所 | 1 |
        | 校友 | 10 | 系所 | 1 |
        | 另開新視窗 | 10 | 研究 | 1 |
        | 行政單位 | 8 | 台灣 | 1 |
        | 圖書館 | 7 | 官網 | 1 |
        | 大學 | 1 | 首頁 | 1 |
        | 系 | 1 | 校徽 | 1 |
    - Ranking Websites:
    Build a tree structure of the website and its subpages, then visit each page to calculate the rank based on the product of the number of keywords and their respective weights. Sum up the results to determine the website's overall rank.
    - Adjusting Keyword Weights:
    Modify the weights of input keywords and other factors using a specific method.
    - URL Filtering:
    Enhance the rank of a website if its URL contains '.edu.tw'.

### System Structure

![image](https://github.com/singlam53/NCCU_DeptOSSearch.github.io/blob/master/Image%20Sources/layer.png)

**Logic Layer and Object**

### UI Pages

![image](https://github.com/singlam53/NCCU_DeptOSSearch.github.io/blob/master/Image%20Sources/searching%20UI.png)

Searching UI

![image](https://github.com/singlam53/NCCU_DeptOSSearch.github.io/blob/master/Image%20Sources/Result%20UI.png)

Result UI
