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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {
	public static Map<String, ListComponent> HeadphonesList = new HashMap<String, ListComponent>();
	public static Dekker dekker = new Dekker();
	public static void main(String[] args) {
		
		for(int i = 0; i < 106; i++)
		{
			Parser parser = new Parser(i);
			parser.run();
		}
		
		for (Entry<String, ListComponent> entry : HeadphonesList.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue().title + " " + entry.getValue().id + " " + entry.getValue().priceList.get(0).price + " " + entry.getValue().priceList.get(0).url + "\n");
		}
	}

}
