package ml.assignment.decisiontree.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ml.assignment.decisiontree.execution.ApplicationData;

public class Utils {
	
	public static int getTotalRecordCount(HashMap<String,ArrayList<String>> dataSet)
	{
		int totalCount=0;
		 ArrayList<String> arrayList=null;
		 //   HashMap<String,ArrayList<String>> displayFeatureTraingData =null;
		    for(String key : dataSet.keySet())
		    {
		    	//System.out.println(key + "  ");
		    	arrayList = dataSet.get(key);
		   
		    		//arrayList = new ArrayList<String>();
		    		//System.out.println("  ( " + key2 + "--"+ displayFeatureTraingData.get(key2).size() + " )");
		    		totalCount = totalCount + arrayList.size();
		    }
		    return totalCount;
		    
	}
	
	public static double logValue(double value)
	{
		return Math.log(value)/Math.log(2);
	}
	
	public static double calculateTotalVarianceImpurity(HashMap<String,ArrayList<String>> dataSet)
	{
		int totalCount = getTotalRecordCount(dataSet);
		double varianceImpurity=1.0;
		ArrayList<String> arrayList=null;
		for(String key : dataSet.keySet())
		{
			arrayList = dataSet.get(key);
			varianceImpurity = varianceImpurity*(arrayList.size()/Double.valueOf(totalCount));
		}
		return varianceImpurity;
	}
	
	
	
	public static String maximumValue(ArrayList<String> outputList)
	{
		HashMap<String,Integer> valueFrequency = new HashMap<String,Integer>();
		for(String output : outputList)
		{
			if(valueFrequency.get(output)!=null)
			{
				valueFrequency.put(output, valueFrequency.get(output)+1);
			}
			else
			{
				valueFrequency.put(output,1);
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
		
		return frequentString;
	}
	
	public static double calculateVarianceImpurityPerAttribute(HashMap<String,ArrayList<String>> dataSet)
	{
		return calculateTotalVarianceImpurity(dataSet);
	}
	
	public static HashMap<String,ArrayList<String>> getClassValueForAttributeValue(ArrayList<String> dataSet, HashMap<String,ArrayList<String>> classDataSet)
	{
		//long totalCount = getTotalRecordCount(dataSet);
		HashMap<String,ArrayList<String>> valueSplitData=new HashMap<String,ArrayList<String>>();
		String searchValue=null;
		ArrayList<String> searchList=null;
		ArrayList<String> valueList=null;
		for(String testString : dataSet)
		{
			searchValue=testString;
			for(String key : classDataSet.keySet())
			{
				searchList = classDataSet.get(key);
				if(binarySearch(searchList, searchValue))
				{
					
					if(valueSplitData.get(key)==null)
					{
						valueList = new ArrayList<String>();
						valueList.add(searchValue);
						valueSplitData.put(key, valueList);
					}
					else
					{
						valueSplitData.get(key).add(searchValue);
					}
					
				}
			}
		}
		return valueSplitData;
	}
	
	
	public static double calculateInformationGain(HashMap<String,ArrayList<String>> dataSet, HashMap<String,ArrayList<String>> classDataSet , double totalVarianceImpurity, int totalRecord)
	{
		double informationGain;
		HashMap<String,Integer> valueRecordList = new HashMap<String,Integer>();
		int totalK = getTotalRecordCount(dataSet);
		ArrayList<String> attributeIndividualValueRecord= null;
		HashMap<String,ArrayList<String>> splitValueDataSet= null;
		double secondTermSum=0;
		double secondTermMultiplicationFactor= totalK/Double.valueOf(totalRecord);
		for(String key : dataSet.keySet())
		{
			attributeIndividualValueRecord = dataSet.get(key);
			splitValueDataSet =  getClassValueForAttributeValue(attributeIndividualValueRecord , classDataSet);
			secondTermSum = secondTermSum+ secondTermMultiplicationFactor*(calculateVarianceImpurityPerAttribute(splitValueDataSet));
		}
		informationGain = totalVarianceImpurity - secondTermSum;
		//double individualEntropyMultiplyingFactor = 
		/*ArrayList<String> arrayList=null;
		for(String key : dataSet.keySet())
		{
			arrayList = dataSet.get(key);
			varianceImpurity = varianceImpurity*(arrayList.size()/Double.valueOf(totalCount));
		}*/
		return informationGain;
	}
	
	public static void main(String args[])
	{
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("225");
		testList.add("237");
		testList.add("239");
		testList.add("240");
		testList.add("243");
		testList.add("245");
		testList.add("247");
		testList.add("270");
		System.out.println(binarySearch(testList, "247"));
		
	}
	
	public static boolean binarySearch(ArrayList<String> list, String searchValue)
	{
			int first  = 0;
		    int last   =list.size() - 1;
		    int middle = (first + last)/2;
		 
		    while( first <= last )
		    {
		      if ( list.get(middle).compareTo(searchValue)<0 )
		    	  first = middle + 1;    
		      else if ( list.get(middle).compareTo(searchValue)==0 ) 
		      {
		        System.out.println(searchValue + " found at location " + (middle + 1) + ".");
		        return true;
		      }
		      else
		         last = middle - 1;
		 
		      middle = (first + last)/2;
		   }
		   if ( first > last )
		   {
			   System.out.println(searchValue + " is not present in the list.\n");
			   return false;
		   }
		   return false;
	}



	
	public static String getBestAttribute(Map<String,Double> attributeInfoGain)
	{
		double tempValue=0.0;
		String bestAttributeName=null;
		Double maxValue=null;
		String tempAttributeKey=null;
	/*	for(String key : attributeInfoGain.keySet())
		{
			bestAttributeName = key;
				maxValue = attributeInfoGain.get(key);
				tempAttributeKey=key;
				break;
		}*/
		  String key =null;
		for(Map.Entry<String,Double> entry : attributeInfoGain.entrySet()) {
			 key = entry.getKey();
			  tempValue = entry.getValue();
			  if(maxValue==null)
			  {
				  bestAttributeName = key;
				  maxValue = attributeInfoGain.get(key);
			  }
			  else
			  {
				  if(maxValue<tempValue)
				  {
					maxValue = tempValue;
					bestAttributeName = key;
				  }
			  }
		}
		/*for(String key : attributeInfoGain.keySet())
		{
			tempValue = attributeInfoGain.get(key);
			//if(!bestAttributeName.equals(tempAttributeKey) && tempValue==maxValue)
				//System.out.println(attributeInfoGain);
			if(maxValue<tempValue)
			{
				maxValue = tempValue;
				bestAttributeName = key;
			}
			
			
		}*/
		return bestAttributeName;
		
	}
	
	public static HashMap<String,ArrayList<String>> getAttributeValueWithTrainingExamples(Map<String,HashMap<String,String>> trainingExamples, String attributeName)
	{
		HashMap<String,ArrayList<String>> reaminingTrainingExamples = new HashMap<String,ArrayList<String>>();
		HashMap<String,String> rowColumn = null;
		String attributeValue=null;
		ArrayList<String> valueRowList = null;
		for(String rowKey : trainingExamples.keySet())
		{
			rowColumn = trainingExamples.get(rowKey);
			attributeValue =  rowColumn.get(attributeName);
			if(reaminingTrainingExamples.get(attributeValue)==null)
			{
				valueRowList = new ArrayList<String>();
				valueRowList.add(rowKey);
				reaminingTrainingExamples.put(attributeValue, valueRowList);
			}
			else
			{
				reaminingTrainingExamples.get(attributeValue).add(rowKey);
			
			}
		}
		
		return reaminingTrainingExamples;
		
	}
	
	
	public static HashMap<String,HashMap<String,String>> getAttributeValueWithTrainingExamplesRowObject(Map<String,HashMap<String,String>> trainingExamples, ArrayList<String> rowList)
	{
		HashMap<String,HashMap<String,String>> hashMap = new HashMap<String,HashMap<String,String>>();
		for(String rowKey : rowList)
		{
			hashMap.put(rowKey, trainingExamples.get(rowKey));
		}
		return hashMap;
	
	}
	
	
	
}
