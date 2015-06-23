package ml.assignment.decisiontree.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ml.assignment.decisiontree.execution.ApplicationData;

/*import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;*/

public class DataReader {
	
	/*public Map<String,HashMap<String,ArrayList<String>>> getExcelData(String fileName) throws IOException
	{
		 Map<String,HashMap<String,ArrayList<String>>>  dataSet = new HashMap<String,HashMap<String,ArrayList<String>>>();
	
		 File inputFile = new File(fileName);
		    Workbook workBook;
		    try {
		    	workBook = Workbook.getWorkbook(inputFile);
		    	Sheet sheet = workBook.getSheet(0);
		    	HashMap<String,ArrayList<String>> featureTraingData=null;
		    	ArrayList<String> featureTrainingRowData=null;
		    	Cell cell=null;
		    	String cellValue=null;
		    	Cell featureCell=null;
		    	String featureCellValue=null;
		    	for (int j = 0; j < sheet.getColumns(); j++) 
		    	{
		    		featureTraingData = new HashMap<String,ArrayList<String>>();
		    		featureCell = sheet.getCell(j,0);
		    		featureCellValue = cell.getContents();
	    			
		    		for (int i = 0; i < sheet.getRows(); i++) 
		    		{
		    			cell = sheet.getCell(j, i);
		    			cellValue = cell.getContents();
		    			if(i==0)
		    			{
		    				dataSet.put(cellValue, featureTraingData);
		    			}
		    			else
		    			{
		    				if(dataSet.get(featureCellValue).get(cellValue)==null)
		    				{
		    					featureTrainingRowData= new ArrayList<String>();
		    					featureTrainingRowData.add(String.valueOf(i+1));
		    					featureTraingData.put(cellValue, featureTrainingRowData);
		    				}
		    				else
		    				{
		    					dataSet.get(featureCellValue).get(cellValue).add(String.valueOf(i+1));
		    				}
		    			}
		    			
		    			
		    			//CellType type = cell.getType();
		    			   if (type == CellType.LABEL) {
		            	System.out.println("I got a label "
		                + cell.getContents());
		          		}

		          		if (type == CellType.NUMBER) {
		            	System.out.println("I got a number "
		                + cell.getContents());
		          		}

		        }
		      }
		    } catch (BiffException e) {
		      e.printStackTrace();
		    }
		    
		    
		    ArrayList<String> arrayList=null;
		    HashMap<String,ArrayList<String>> displayFeatureTraingData =null;
		    for(String key : dataSet.keySet())
		    {
		    	//System.out.println(key + "  ");
		    	displayFeatureTraingData = dataSet.get(key);
		    	for(String key2 : displayFeatureTraingData.keySet())
		    	{
		    		arrayList = new ArrayList<String>();
		    		//System.out.println("  ( " + key + "--"+ arrayList.size() + " )");
		    	}
		    }
		    
		    
		return dataSet;
	}*/
	
	public Map<String,HashMap<String,ArrayList<String>>> getCSVDataByColumn(String fileName) throws IOException
	{
		 Map<String,HashMap<String,ArrayList<String>>>  dataSet = new HashMap<String,HashMap<String,ArrayList<String>>>();
		 ArrayList<ArrayList<String>> csvRowList = new ArrayList<ArrayList<String>>();
		 BufferedReader br = null;
		 String line = null;
		 String token = ",";
		 
			try {
		 
				Map<String, String> maps = new HashMap<String, String>();
		 
				br = new BufferedReader(new FileReader(fileName));
				ArrayList<String> row = null;
				String[] valueList=null;
				while ((line = br.readLine()) != null) {
		 
					// use comma as separator
					valueList = line.split(token);
					row = new ArrayList<String>();
					for(String value: valueList)
						row.add(value);
					csvRowList.add(row);
					
		 
				}
		 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		    
			HashMap<String,ArrayList<String>> featureTraingData=null;
	    	ArrayList<String> featureTrainingRowData=null;
	    	//Cell cell=null;
	    	String featureValue=null;
	    	//Cell featureCell=null;
	    	String featureLabelValue=null;
	    	for (int j = 0; j < csvRowList.get(0).size(); j++) 
	    	{
	    		featureTraingData = new HashMap<String,ArrayList<String>>();
	    	//	featureCell = sheet.getCell(j,0);
	    		featureLabelValue = csvRowList.get(0).get(j);
    			
	    		for (int i = 0; i < csvRowList.size(); i++) 
	    		{
	    			//cell = sheet.getCell(j, i);
	    			featureValue = csvRowList.get(i).get(j);
	    			if(i==0)
	    			{
	    				dataSet.put(featureValue, featureTraingData);
	    			}
	    			else
	    			{
	    				if(dataSet.get(featureLabelValue).get(featureValue)==null)
	    				{
	    					featureTrainingRowData= new ArrayList<String>();
	    					featureTrainingRowData.add(String.valueOf(i+1));
	    					featureTraingData.put(featureValue, featureTrainingRowData);
	    				}
	    				else
	    				{
	    					dataSet.get(featureLabelValue).get(featureValue).add(String.valueOf(i+1));
	    				}
	    			}
	    			
	    			
	    			//CellType type = cell.getType();
	    			/*   if (type == CellType.LABEL) {
	            	System.out.println("I got a label "
	                + cell.getContents());
	          		}

	          		if (type == CellType.NUMBER) {
	            	System.out.println("I got a number "
	                + cell.getContents());
	          		}*/

	        }
	      }
		    
		    ArrayList<String> arrayList=null;
		    HashMap<String,ArrayList<String>> displayFeatureTraingData =null;
		    for(String key : dataSet.keySet())
		    {
		    	//System.out.println(key + "  ");
		    	displayFeatureTraingData = dataSet.get(key);
		    	for(String key2 : displayFeatureTraingData.keySet())
		    	{
		    		//arrayList = new ArrayList<String>();
		    		//System.out.println("  ( " + key2 + "--"+ displayFeatureTraingData.get(key2).size() + " )");
		    	}
		    }
		    
		    
		return dataSet;
	}
	
	public HashMap<String,HashMap<String,String>> getCSVDataByRow(String fileName) throws IOException
	{
		 HashMap<String,HashMap<String,String>>  dataSet = new HashMap<String,HashMap<String,String>>();
		 ArrayList<ArrayList<String>> csvRowList = new ArrayList<ArrayList<String>>();
		 BufferedReader br = null;
		 String line = null;
		 String token = ",";
		 
			try {
		 
				Map<String, String> maps = new HashMap<String, String>();
		 
				br = new BufferedReader(new FileReader(fileName));
				ArrayList<String> row = null;
				String[] valueList=null;
				while ((line = br.readLine()) != null) {
		 
					// use comma as separator
					valueList = line.split(token);
					row = new ArrayList<String>();
					for(String value: valueList)
						row.add(value);
					csvRowList.add(row);
					
		 
				}
		 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		    
			HashMap<String,String> featureTraingData=null;
	    	ArrayList<String> eachRow=null;
	    	//Cell cell=null;
	    	String featureValue=null;
	    	//Cell featureCell=null;
	    	String featureLabelValue=null;
	    	ArrayList<String> attributeList = csvRowList.get(0);
	    	for (int j = 1; j < csvRowList.size(); j++) 
	    	{
	    		
	    		featureTraingData = new HashMap<String,String>();
	    	//	featureCell = sheet.getCell(j,0);
	    		eachRow = csvRowList.get(j);
    			
	    		for (int i = 0; i <eachRow.size() ; i++) 
	    		{
	    			featureTraingData.put(attributeList.get(i), eachRow.get(i));
	    		}
	    		dataSet.put(String.valueOf(j), featureTraingData);
	    	}
	    	
	    	
		    
		 // System.out.println(dataSet);  
		return dataSet;
	}
	
	public void setInitalFrequentClassAttributeValue(HashMap<String,HashMap<String,String>> trainingExamples)
	{
		HashMap<String,Integer> valueFrequency = new HashMap<String,Integer>();
		HashMap<String,String> row = null;
		String classAttributename = ApplicationData.appData.get("Class");
		for(String key : trainingExamples.keySet())
		{
			row = trainingExamples.get(key);
			
			if(valueFrequency.get(row.get(classAttributename))!=null)
			{
				valueFrequency.put(row.get(classAttributename), valueFrequency.get(row.get(classAttributename))+1);
			}
			else
			{
				valueFrequency.put(row.get(classAttributename),1);
			}
		}
		String frequentString=null;
		int maxCount=0;
		int tempCount=0;
		for(String key : valueFrequency.keySet())
		{
			tempCount = valueFrequency.get(key);
			if(maxCount<=tempCount)
			{
				maxCount = tempCount;
				frequentString = key;
			}
			
		}
		ApplicationData.appData.put("initial_frequent_value",frequentString);
	}
	
	public static void main(String args[]) throws IOException
	{
		DataReader dataReader = new DataReader();
		dataReader.getCSVDataByRow("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet1/training_set.csv");
	}
	
	
	

}
