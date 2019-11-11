package stockSpider;
import java.util.List;
import java.util.ListIterator;

/*
Author: Ziqi Tan
*/
public class LaunchStockSpiderTest {

	public  void launch() {
		String url = "https://money.cnn.com/data/markets/";
		StockSpider stockSpider = new StockSpider(url);
		List<List<String>> stocks = stockSpider.getStockInfoList();
		
		ListIterator iter = stocks.listIterator();
		while( iter.hasNext() ) {
			System.out.println(iter.next());
		}
		
	}

}
