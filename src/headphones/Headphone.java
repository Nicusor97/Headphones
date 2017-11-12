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

public class Headphone {
	public String name;
	public String id;
	public float price;
	public String link;
	
	public Headphone(String n, String i, float p, String u)
	{
		name = n;
		id = i;
		price = p;
		link = u;
	}
}

