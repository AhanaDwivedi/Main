package com.sas.assignment.bean;
import java.util.Comparator;


public class MenuItem implements Comparable<MenuItem>{
	String itemName;
	Double itemPrice;
	Boolean selected;
	
	public MenuItem() {
		this.selected = Boolean.FALSE;
	}
	public MenuItem(String itemName, Double itemPrice) {
		super();
		this.selected = Boolean.FALSE;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}
	
	public Boolean isSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	
	public int compareTo(MenuItem arg0) {
		// TODO Auto-generated method stub
		return this.getItemPrice().compareTo(arg0.getItemPrice());
	}
	

	static class MenuItemComparator implements Comparator<MenuItem>{
		public int compare(MenuItem mi1, MenuItem mi2) {
	        return (mi1.itemPrice < mi2.itemPrice ) ? -1: (mi1.itemPrice > mi2.itemPrice) ? 1:0 ;
	    }

		

		
	}


}
