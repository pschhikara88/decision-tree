package ml.assignment.decisiontree.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface GainService {
	
	public  Map<String,Double> getInformationGain(Map<String,HashMap<String,String>> rowSet, ArrayList<String> attributeNameList, String classAttribute);

}
