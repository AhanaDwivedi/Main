package com.sas.assignment.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.sas.assignment.bean.MenuItem;
import com.sas.assignment.helper.CSVResourceLoader;

/**
 * @author 149039
 *
 */
public class MenuCombinationServiceImpl implements CombinationService{
	List<String> menuCourseList;
	/**
	 * @param args
	 */
	CSVResourceLoader menuResourceLoader;
	CSVResourceLoader weightageResourceLoader;
	/* Method to find best combination */
	public Map<String, List<MenuItem>> findBestCombination() {
		Map<String, List<MenuItem>> menuMap = null;
		Map<String, Double> weightageMap = null;
		InputStream in = null;
		double firstBudget = 0.0,secBudget = 0.0, thdBudget = 0.0;
		 
		Map<String,List<MenuItem>> finalOptions = null;
		Scanner sc = new Scanner(System.in);

		String firstPrt = null;
		String secPrt = null;
		String thdPrt = null;
		ArrayList<MenuItem> firstPriList = null;
		ArrayList<MenuItem> secPriList = null;
		ArrayList<MenuItem> thdPriList = null;
		ArrayList<MenuItem> selectedItems = null;
		Map<String, Object> outputMap = null;
		try {
			in = this.menuResourceLoader.readResource();
			menuMap = this.readMenuCSV(in);
			in = this.weightageResourceLoader.readResource();
			weightageMap = this.readWeightCSV(in);
			
			
			System.out.println("Enter the max budget: ");
			double budget = sc.nextFloat();
			float sum = 0;
			/*String tempOption = null;
			ArrayList<MenuItem> priMenuList = null;
			Map<String, List<MenuItem>> priMenuMap = null; 
			for(String s: weightageMap.keySet()) {
				System.out.println("Enter your "+s+" priority: ");
				tempOption = sc.next();
				priMenuList = (ArrayList<MenuItem>) menuMap.get(tempOption.toUpperCase());
				if(priMenuMap == null){
					priMenuMap = new HashMap<String, List<MenuItem>>();					
				}
				priMenuMap.put(tempOption.toUpperCase(),priMenuList);
			}
			*/
			//Get first priority from user
			do{
				System.out.println("Enter your first priority: ");
				firstPrt = sc.next();
				if(menuMap.get(firstPrt.toUpperCase()) == null){
					System.out.println("Invalid Option. Please renter ");
				} else {
					firstPriList = (ArrayList<MenuItem>) menuMap.get(firstPrt.toUpperCase());
				}
			}while(menuMap.get(firstPrt.toUpperCase()) == null);
			
			//Get second priority from user
			do{
				System.out.println("Enter your second priority: ");
				secPrt = sc.next();
				if(menuMap.get(secPrt.toUpperCase()) == null){
					System.out.println("Invalid Option. Please renter ");
				} else {
					secPriList = (ArrayList<MenuItem>) menuMap.get(secPrt.toUpperCase());
				}
			}while(menuMap.get(secPrt.toUpperCase()) == null);
			
			//Get third priority from user
			do{
				System.out.println("Enter your third priority: ");
				thdPrt = sc.next();
				if(menuMap.get(thdPrt.toUpperCase()) == null){
					System.out.println("Invalid Option. Please renter ");
				} else {
					thdPriList = (ArrayList<MenuItem>) menuMap.get(thdPrt.toUpperCase());
				}
			}while(menuMap.get(thdPrt.toUpperCase()) == null);
			
			//Distribute budget as per weightage
			if(weightageMap != null){
				firstBudget = weightageMap.get("1") * budget;
				secBudget = weightageMap.get("2") * budget;
				thdBudget = weightageMap.get("3") * budget;
			}
			
			//Loop to get 6 best options
			for (int j = 0; j < 6; j++) {
				switch (j) {
				case 0:
					break;
				case 1:
					firstPriList = resetList(firstPriList);
					thdPriList = resetList(thdPriList);
					break;
				case 2:
					secPriList = resetList(secPriList);
					thdPriList = resetList(thdPriList);
					break;
				case 3:
					secPriList = resetList(secPriList);
					firstPriList = resetList(firstPriList);
					break;
				case 4:
					secPriList = resetList(secPriList);
					break;
				case 5:
					thdPriList = resetList(thdPriList);
					break;
				}
				firstBudget = weightageMap.get("1") * budget;
				secBudget = weightageMap.get("2") * budget;
				thdBudget = weightageMap.get("3") * budget;
				//Pick the items from course which is on first priority
				outputMap = pickItems(firstPriList, firstBudget);
				if (outputMap != null) {
					if (outputMap.get("REMAINING_BUDGET") != null) {
						secBudget += (Double) outputMap.get("REMAINING_BUDGET");
					}
					selectedItems = (ArrayList<MenuItem>) outputMap
							.get("SELECTED_ITEMS");
				}
				//Pick the items from course which is on second priority
				outputMap = pickItems(secPriList, secBudget);
				if (outputMap != null) {
					if (outputMap.get("REMAINING_BUDGET") != null) {
						thdBudget += (Double) outputMap.get("REMAINING_BUDGET");
					}
					selectedItems.addAll((ArrayList<MenuItem>) outputMap
							.get("SELECTED_ITEMS"));
				}
				//Pick the items from course which is on third priority
				outputMap = pickItems(thdPriList, thdBudget);
				if (outputMap != null) {
					if (outputMap.get("REMAINING_BUDGET") != null) {
						thdBudget += (Double) outputMap.get("REMAINING_BUDGET");
					}
					selectedItems.addAll((ArrayList<MenuItem>) outputMap
							.get("SELECTED_ITEMS"));
				}
				
				/*System.out.print("The left out amount::"
						+ outputMap.get("REMAINING_BUDGET"));*/
				if(finalOptions == null){
					finalOptions = new TreeMap<String,List<MenuItem>>();				
				}
				finalOptions.put(String.valueOf(j), selectedItems);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("The following exception occured::"
					+ ex.getMessage());
		}
		return finalOptions;
	}

	public List<String> getMenuCourseList() {
		return menuCourseList;
	}


	public CSVResourceLoader getMenuResourceLoader() {
		return menuResourceLoader;
	}

	public CSVResourceLoader getWeightageResourceLoader() {
		return weightageResourceLoader;
	}
	/* Method to find best option from a single course with budget */
	private Map<String, Object> pickItems(List<MenuItem> avlMenuList,
			Double avlBudget) {
		Map<String, Object> outputMap = null;
		List<MenuItem> selMenuList = null;
		if (avlMenuList != null) {
			Collections.sort(avlMenuList, Collections.reverseOrder());

			selMenuList = new ArrayList<MenuItem>();
			outputMap = new HashMap<String, Object>();
			int n = 1000;
			Double remBudget = avlBudget;
			MenuItem prevItem = null;
			for (MenuItem item : avlMenuList) {
				for (int i = 0; i < n; i++) {
					if (item.isSelected() || item.getItemPrice() > remBudget) {
						if (prevItem != null) {
							prevItem.setSelected(Boolean.TRUE);
						}
						break;
					} else {
						selMenuList.add(item);
						prevItem = item;
						remBudget -= item.getItemPrice();
					}
				}

			}
			outputMap.put("REMAINING_BUDGET", remBudget);
			outputMap.put("SELECTED_ITEMS", selMenuList);
		}
		return outputMap;

	}
	/* Method to read menu from csv*/
	public Map<String,List<MenuItem>> readMenuCSV(InputStream in){
		Map<String,List<MenuItem>> avlMenuMap = null;
		Boolean skipHeader = Boolean.TRUE;
		String line = "";
		ArrayList<MenuItem> menuList = null;
		MenuItem menuItem = null;
		String cvsSplitBy = ",";
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				if(skipHeader) {
					skipHeader = Boolean.FALSE;
					continue;
				}
				String[] inputArr = line.split(cvsSplitBy);
				if(avlMenuMap == null){
					avlMenuMap = new HashMap<String,List<MenuItem>>();
				}
				if(inputArr.length == 4) {
					menuItem = new MenuItem();
					menuItem.setItemName(inputArr[1]);
					menuItem.setItemPrice(Double.valueOf(inputArr[2]));
					if(avlMenuMap != null && avlMenuMap.get(inputArr[3].toUpperCase()) != null){
						menuList = (ArrayList<MenuItem>)avlMenuMap.get(inputArr[3].toUpperCase());
						
					} else {
						menuList = new ArrayList<MenuItem>();								
					}
					menuList.add(menuItem);
					
					avlMenuMap.put(inputArr[3].toUpperCase(), menuList);
					//System.out.println("Course:"+inputArr[3].toUpperCase()+" Number of Menu Items::"+menuList.size());
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return avlMenuMap;
	}
	/* Method to read weightage from csv*/
	public Map<String,Double> readWeightCSV(InputStream in){
		Map<String,Double> avlWeightMap = null;
		Boolean skipHeader = Boolean.TRUE;
		String line = "";		
		String cvsSplitBy = ",";
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			while ((line = br.readLine()) != null) {

			        // use comma as separator
				if(skipHeader) {
					skipHeader = Boolean.FALSE;
					continue;
				}
				String[] inputArr = line.split(cvsSplitBy);
				if(avlWeightMap == null){
					avlWeightMap = new HashMap<String,Double>();
				}
				if(inputArr.length == 2) {												
					avlWeightMap.put(inputArr[0], Double.valueOf(inputArr[1]));					
				}
			}
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return avlWeightMap;
	}
	
	/* Method to reset selection in the previous option*/
	private ArrayList<MenuItem> resetList(ArrayList<MenuItem> avMenuList) {
		for (MenuItem item : avMenuList) {
			item.setSelected(Boolean.FALSE);
		}
		return avMenuList;
	}

	public void setMenuCourseList(List<String> menuCourseList) {
		this.menuCourseList = menuCourseList;
	}
	public void setMenuResourceLoader(CSVResourceLoader menuResourceLoader) {
		this.menuResourceLoader = menuResourceLoader;
	}
	
	public void setWeightageResourceLoader(CSVResourceLoader weightageResourceLoader) {
		this.weightageResourceLoader = weightageResourceLoader;
	}
}
