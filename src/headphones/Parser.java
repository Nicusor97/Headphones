/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package headphones;

/**
 *
 * @author nicol
 */
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.PrintWriter;

public class Parser implements Runnable {
	
	public Thread thread;
	public String emagUrl;
	public String celUrl;
	public int threadId;

	public Parser(int i)
	{
		threadId = i;
		emagUrl = "https://www.emag.ro/casti-pc/p" + (i+1) + "/c";
		celUrl = "http://www.cel.ro/casti/0a-" + (i+1);
	}
	
	private float ConvertEmagPrice(String p)
	{
		p = p.trim();
		if(p.contains(" "))
		{
			p = p.substring(0, p.indexOf(" "));
		}
		return Float.valueOf(p)/100;
	}
	
	private String ConvertCelId(String id)
	{
		return id.substring(id.indexOf("s") + 1, id.indexOf("-"));
	}
	
	@Override
	public void run() {
		try {
			Document emagDoc = Jsoup.connect(emagUrl).followRedirects(false).ignoreHttpErrors(true).get();
			Document celDoc = Jsoup.connect(celUrl).followRedirects(false).ignoreHttpErrors(true).get();

			Elements emagTitles = emagDoc.getElementsByClass("card-body product-title-zone");
			Elements emagIds = emagDoc.select("input[name=product[]]");
			Elements emagPrices = emagDoc.getElementsByClass("product-new-price");
			Elements emagLinks = emagDoc.getElementsByClass("card-body product-title-zone");
			
			
			Main.dekker.Lock(threadId);
			for(int i =0 ; i < emagIds.size(); i++)
			{
				System.out.println("Am intrat in forul" + i +" threadul" + threadId);
				String title = emagTitles.get(i).text();
				String id = emagIds.get(i).attr("value");
				float price = ConvertEmagPrice(emagPrices.get(i).text());
				String link = emagLinks.get(i).select("a").first().attr("href");
				Headphone headphone = new Headphone(title, id, price, link);
				
				if(Main.HeadphonesList.get(headphone.id) != null)
				{
					Main.HeadphonesList.get(headphone.id).AddPriceListToComponent(headphone.price, headphone.link);
				}
				else
				{
					Main.HeadphonesList.put(headphone.id, new ListComponent(headphone.name, headphone.id, headphone.price, headphone.link));
				}
			}
			
			Elements celTitles = celDoc.getElementsByClass("productTitle");
			Elements celIds = celDoc.getElementsByClass("stoc_list");
			Elements celPrices = celDoc.select("b[itemprop=price]");
			Elements celLinks = celDoc.getElementsByClass("productTitle");
			
			for(int i =0 ; i < celIds.size(); i++)
			{
				String title = celTitles.get(i).text();
				String id = ConvertCelId(celIds.get(i).getElementsByAttribute("id").attr("id"));
				float price = Float.valueOf(celPrices.get(i).text());
				String link = celLinks.get(i).select("a").first().attr("href");
				Headphone headphone = new Headphone(title, id, price, link);
				
				if(Main.HeadphonesList.get(headphone.id) != null)
				{
					Main.HeadphonesList.get(headphone.id).AddPriceListToComponent(headphone.price, headphone.link);
				}
				else
				{
					Main.HeadphonesList.put(headphone.id, new ListComponent(headphone.name, headphone.id, headphone.price, headphone.link));
				}
			}
			Main.dekker.Unlock(threadId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

