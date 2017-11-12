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
import java.util.List;
import java.util.ArrayList;

public class ListComponent {
	public String title;
	public String id; 
	public List<Price> priceList = new ArrayList<>();
	
	public ListComponent(String t, String i, float p, String l)
	{
		title = t; 
		id = i; 
		priceList.add(new Price(p, l));
	}
	
	public void AddPriceListToComponent(float price, String link)
	{
		priceList.add(new Price(price, link));
	}
}
