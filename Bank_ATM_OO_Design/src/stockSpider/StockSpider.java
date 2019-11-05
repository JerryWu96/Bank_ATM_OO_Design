package stockSpider;
/*
Author: Ziqi Tan
A mini web crawing program for getting stock information.
*/
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StockSpider {
	
	private URL url;
	private HttpURLConnection httpConn;
	private InputStream inputStream;
	private BufferedReader reader;
	private StringBuffer stringBuffer;
	private String htmlCode;
	private List<List<String>> stocksInfoList;
	private Calendar calendar = Calendar.getInstance();
	
	public StockSpider(String _url) {
		this.httpConn = null;
		this.inputStream = null;
		this.reader = null;
		this.stringBuffer = null;
		this.stocksInfoList = new ArrayList<List<String>>();
		try {
			this.url = new URL(_url);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		httpConnection();
		getHtml();
		parseHtml();
	}
	
	private void httpConnection() {	    
	    try {
	    	httpConn = (HttpURLConnection) url.openConnection();
	    	httpConn.setConnectTimeout(5000);
	        httpConn.setReadTimeout(5000);
	        httpConn.setDoInput(true);
	        httpConn.connect();	        
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    } 
	    finally{
	        httpConn.disconnect();	        
	    }	    
	}
	
	private void getHtml() {
		try {
			inputStream = httpConn.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(inputStream));
	        stringBuffer = new StringBuffer();
	        String line = null;
	        while((line = reader.readLine()) != null){
	            stringBuffer.append(line + "\n");
	        }
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			try {
	            inputStream.close();
	            reader.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		
		this.htmlCode = stringBuffer.toString();
	}
	
	/**
	 * Method: parseHtml
	 * Function: parse html code by regular expression.
	 * Remark: we can also use Jsoup (Java HTML Parser, something like 
	 * beautifulsoup4 in Python), but an external package should be downloaded and installed.
	 * */
	private void parseHtml() {
		String expression = "<a .*? class=\"stock\".*?\\n.*?\n.*?\\n.*?\\n.*?\\n.*?\\n";
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(this.htmlCode);
		try {
			while( matcher.find() ) {				
				String string = matcher.group(0);
				String[] stock = string.split("\\n");
				/*for( int i = 0; i < stock.length; i++ ) {
					System.out.println(stock[i].trim());
				}*/
				Date time = calendar.getTime();
				String timestamp = time.toString();	
				String name = null; // stock name
				String price = null; // stock price
				
				// Get stock name
				String nameExpression = ">.*?<";
				Pattern namePattern = Pattern.compile(nameExpression);
				Matcher nameMatcher = namePattern.matcher(stock[1]);
				if( nameMatcher.find() ) {
					name = nameMatcher.group(0);
					name = name.substring(1, name.length()-1);
					// System.out.print(name + " ");
				}
				
				// Get stock price
				String priceExpression = ">.*?<";
				Pattern pricePattern = Pattern.compile(priceExpression);
				Matcher priceMatcher = pricePattern.matcher(stock[2]);
				if( priceMatcher.find() ) {
					price = priceMatcher.group(0);
					price = price.substring(1, price.length()-1);
					// System.out.println(price);
				}
				
				// add stock information to a list
				List<String> stockInfo = new ArrayList<String>();
				stockInfo.add(timestamp);
				stockInfo.add(name);
				stockInfo.add(price);
				this.stocksInfoList.add(stockInfo);							
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * getter()
	 * */
	public String getHtmlCode() {
		return this.htmlCode;
	}
	
	/**
	 * getter()
	 * */
	public List<List<String>> getStockInfoList() {
		return this.stocksInfoList;
	}
	
}
