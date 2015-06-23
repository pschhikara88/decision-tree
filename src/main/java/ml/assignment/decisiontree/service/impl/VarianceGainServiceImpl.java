package ml.assignment.decisiontree.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import ml.assignment.decisiontree.execution.ApplicationData;
import ml.assignment.decisiontree.service.GainService;

public class VarianceGainServiceImpl implements GainService{
	
	public  Map<String,Double> getInformationGain(Map<String,HashMap<String,String>> rowSet, ArrayList<String> attributeNameList, String classAttribute)
	{
		Map<String,Double> informationGainMap = new TreeMap<String,Double>();
		HashMap<String,HashMap<String,HashMap<String,Integer>>> dataSetCountMap = new HashMap<String,HashMap<String,HashMap<String,Integer>>>();
		HashMap<String,Integer> classValueCount=null;
		HashMap<String,HashMap<String,Integer>> atrributeValue=null;
		HashMap<String,String>  row=null;
		Integer newClassValCount=0;
		String classValueName=null;
		String attributeValueName=null;
		HashMap<String,Integer> classValCountTotalEnt=new HashMap<String,Integer>();
		for(String rowKey : rowSet.keySet())
		{
			row = rowSet.get(rowKey);
			for(String attributeKey : row.keySet())
			{
				classValueName = row.get(classAttribute);
				attributeValueName = row.get(attributeKey);
				if(classValCountTotalEnt.get(classValueName)!=null)
				{
					classValCountTotalEnt.put(classValueName, classValCountTotalEnt.get(classValueName)+1);
				}
				else
				{
					classValCountTotalEnt.put(classValueName,1);
				}
				
				if(dataSetCountMap.get(attributeKey)==null)
				{
					 atrributeValue = new HashMap<String,HashMap<String,Integer>>();
					 classValueCount = new HashMap<String,Integer>();
					 classValueCount.put(classValueName, 1);
					 atrributeValue.put(attributeValueName, classValueCount);
					 dataSetCountMap.put(attributeKey, atrributeValue);
				}
				else
				{
				  if(dataSetCountMap.get(attributeKey).get(attributeValueName)==null)
				  {
					 // atrributeValue = new HashMap<String,HashMap<String,Integer>>();
					  classValueCount = new HashMap<String,Integer>();
					  classValueCount.put(classValueName, 1);
					  dataSetCountMap.get(attributeKey).put(attributeValueName, classValueCount);
				  }
				  else
				  {
					  if(dataSetCountMap.get(attributeKey).get(attributeValueName).get(classValueName)==null)
					  {
						  dataSetCountMap.get(attributeKey).get(attributeValueName).put(classValueName, 1);
					  }
					  else
					  {
						  newClassValCount = dataSetCountMap.get(attributeKey).get(attributeValueName).get(classValueName)+1;
						  dataSetCountMap.get(attributeKey).get(attributeValueName).put(classValueName, newClassValCount);
					  }
				  }
				}
			}
		}
		
		double totalEntropy = calculateTotalVarianceImpurityByInteger(classValCountTotalEnt,true);
		
		if(totalEntropy==0.0)
		{
			return null;
		}
		Double secondTerm =null;
		double gain=1.0;
		for(String attributeName : attributeNameList)
		{
			secondTerm = calculateSecondTerm(dataSetCountMap.get(attributeName));
			gain = totalEntropy-secondTerm;
			informationGainMap.put(attributeName, gain);
		}
		//System.out.println(informationGainMap);
		return informationGainMap;
	  }
	
	private  double calculateSecondTerm(HashMap<String,HashMap<String,Integer>> attributeDataSet)
	{
		double secondTerm = 1.0;
		double tempSum=0.0;
		int attributeValueCount=0;
		int attributeTotalCount=0;
		HashMap<String,Integer> tempClassCount=null;
		int totalNumerator=1;
		//int denominator=0;
		Integer temp=null;
		double impurityVarianceWithPRNumerator=0.0;
		
		for(String attributeValueKey : attributeDataSet.keySet())
		{
			totalNumerator=1;
			attributeValueCount=0;
			tempClassCount = attributeDataSet.get(attributeValueKey);
			for(String key : tempClassCount.keySet())
			{
				temp = tempClassCount.get(key);
				totalNumerator = totalNumerator * temp;
				attributeValueCount = attributeValueCount + temp;
			}
		//	impurityVarianceWithPRNumerator = impurityVarianceWithPRNumerator + (attributeValueCount*(totalNumerator/Math.pow(attributeValueCount, tempClassCount.size())));
			impurityVarianceWithPRNumerator = impurityVarianceWithPRNumerator + attributeValueCount*calculateTotalVarianceImpurityByInteger(tempClassCount,false);
			attributeTotalCount = attributeTotalCount + attributeValueCount;
		}
		secondTerm =  impurityVarianceWithPRNumerator/Double.valueOf(attributeTotalCount);
		return secondTerm;
	}
	
	private double calculateTotalVarianceImpurityByInteger(HashMap<String,Integer> dataSet,boolean isTarget)
	{
		int totalNumerator=1;
		int denominator=0;
		Integer tempCount=null;
		String frequentString=null;
		int maxCount=0;
		if(dataSet.size()==1)
		{
			
			for(String key : dataSet.keySet())
			{
				frequentString = key;
			}
			if(isTarget)
				ApplicationData.appData.put("target_frequent_value",frequentString);
			return 0.0;
		}
		for(String key : dataSet.keySet())
		{
			tempCount = dataSet.get(key);
			totalNumerator = totalNumerator * tempCount;
			denominator = denominator + tempCount;
			if(maxCount<=tempCount)
			{
				maxCount = tempCount;
				frequentString = key;
			}
			
		}
		if(isTarget)
			ApplicationData.appData.put("target_frequent_value",frequentString);
		
		return totalNumerator/Math.pow(denominator, dataSet.size());
	}
	

}
