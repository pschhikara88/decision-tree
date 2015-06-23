package ml.assignment.decisiontree.execution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ml.assignment.decisiontree.domain.Node;
import ml.assignment.decisiontree.reader.DataReader;
import ml.assignment.decisiontree.service.GainService;
import ml.assignment.decisiontree.service.impl.InformationGainServiceImpl;
import ml.assignment.decisiontree.service.impl.VarianceGainServiceImpl;
import ml.assignment.decisiontree.utility.PruneUtils;
import ml.assignment.decisiontree.utility.Utils;

public class Main {
	
	private GainService gainService;
	
	
	
	public GainService getGainService() {
		return gainService;
	}

	public void setGainService(GainService gainService) {
		this.gainService = gainService;
	}

	public static void main(String args[]) throws IOException
	{
		Main testMain = new Main();
		
		/*Integer l = Integer.valueOf(args[0]);
		Integer k=Integer.valueOf(args[1]);
		String dataSet = args[2];
		String classAttributeName=args[3];
		String toPrint=args[4];
		String trainingSetFileName="Data" + File.separator+ dataSet + File.separator + "training_set.csv";
		String testSetFileName="Data" + File.separator+ dataSet + File.separator + "test_set.csv";
		String validationSetFileName="Data" + File.separator+ dataSet + File.separator + "validation_set.csv";*/
		
		
		
		String trainingSetFileName="C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet1/training_set.csv";
		String testSetFileName="C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet1/test_set.csv";
		String validationSetFileName="C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet1/validation_set.csv";
		Integer l = 10;
		Integer k=15;
		String classAttributeName="Class";
		String toPrint="no";
		
		
		
		ApplicationData.appData.put("Class",classAttributeName);
		
	
		
		DataReader dataReader = new DataReader();
		HashMap<String,HashMap<String,String>> trainingExamples  = dataReader.getCSVDataByRow(trainingSetFileName);
		HashMap<String,HashMap<String,String>> validationExamples  = dataReader.getCSVDataByRow(validationSetFileName);
		HashMap<String,HashMap<String,String>> testExamples  = dataReader.getCSVDataByRow(testSetFileName);
		dataReader.setInitalFrequentClassAttributeValue(trainingExamples);
		
		ApplicationData.appCompleteDataByRow.put("training-set",trainingExamples);
		ApplicationData.appCompleteDataByRow.put("validation-set",validationExamples);
		ApplicationData.appCompleteDataByRow.put("test-set",testExamples);
		
		ArrayList<String> attributeList = new ArrayList<String>();
		if(trainingExamples.size()>0)
		{
			HashMap<String,String> tempRow = trainingExamples.get("1");
			attributeList.addAll(tempRow.keySet());
		}
		else
		{
			return;
		}
		attributeList.remove(classAttributeName);
		
		
		// Information gain tree
		testMain.setGainService(new InformationGainServiceImpl());
		
		Node root = testMain.id3(trainingExamples,classAttributeName,attributeList);
		
		 int internalNode = PruneUtils.getCountOfInternalLeaf(root, 1);
			int totalNode = PruneUtils.getTotalNode(root, 1);
			int leafNode = totalNode-internalNode;
			System.out.println("Number of Total Node : "+ totalNode);
			System.out.println("Number of Internal Node : "+ internalNode);
			System.out.println("Number of leaf Node : "+ leafNode );
		
		
		if(toPrint.equalsIgnoreCase("yes"))
		{
			printTreeTraverse(root,0);
		}
		else
		{
				System.out.println("This goes to the console");
				PrintStream console = System.out;

			//	File file = new File("Data" + File.separator+ dataSet + File.separator + "information-tree.txt");
				File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet2/information-tree.txt");
				FileOutputStream fos = new FileOutputStream(file);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
				//System.out.println("This goes to out.txt");
				printTreeTraverse(root,0);
				//printTreeTraverse(root,0,null, null);
				System.setOut(console);
		}
		
		
	  double initialAccuracy=percentageCorrectOutput( trainingExamples, classAttributeName,root);
	  System.out.println("initialAccuracy with test data : " + initialAccuracy);
	  initialAccuracy =  percentageCorrectOutput( validationExamples, classAttributeName,root);
	  System.out.println("initialAccuracy with validation data : " + initialAccuracy);
	 // Node bestTree = PruneUtils.pruneAlgo(root, initialAccuracy,l,k, validationExamples,dataSet,"information-prune-log.txt");
	    // toPrint="yes";
	  if(toPrint.equalsIgnoreCase("yes"))
		{
			printTreeTraverse(root,0);
		}
		else
		{
				System.out.println("This goes to the console");
				PrintStream console = System.out;

				//File file = new File("Data" + File.separator+ dataSet + File.separator + "information-pruned-best-tree.txt");
				File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet2/information-prune-best-tree.txt");
				FileOutputStream fos = new FileOutputStream(file);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
				//System.out.println("This goes to out.txt");
				printTreeTraverse(root,0);
				System.setOut(console);
			}
	     
	     // information gain tree end 
	     
	     
	     
	     // variance impurity tree start
	
	     testMain.setGainService(new VarianceGainServiceImpl());
	     
	     Node rootVariance = testMain.id3(trainingExamples,classAttributeName,attributeList);
	     
	     	internalNode = PruneUtils.getCountOfInternalLeaf(rootVariance, 1);
			totalNode = PruneUtils.getTotalNode(rootVariance, 1);
			leafNode = totalNode-internalNode;
			System.out.println("Number of Total Node : "+ totalNode);
			System.out.println("Number of Internal Node : "+ internalNode);
			System.out.println("Number of leaf Node : "+ leafNode );
			
			if(toPrint.equalsIgnoreCase("yes"))
			{
				printTreeTraverse(root,0);
			}
			else
			{
				System.out.println("This goes to the console");
				PrintStream console = System.out;

				//File file = new File("Data" + File.separator+ dataSet + File.separator + "variance-tree.txt");
				File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet2/variance-tree-variance.txt");
				FileOutputStream fos = new FileOutputStream(file);
				PrintStream ps = new PrintStream(fos);
				System.setOut(ps);
				//System.out.println("This goes to out.txt");
				
				
				printTreeTraverse(rootVariance,0);
				System.setOut(console);
			}
			
			
		  double initialAccuracyVariance=percentageCorrectOutput( trainingExamples, classAttributeName,rootVariance);
		  System.out.println("initialAccuracy with test data : " + initialAccuracyVariance);
		  initialAccuracyVariance =  percentageCorrectOutput( validationExamples, classAttributeName,rootVariance);
		  System.out.println("initialAccuracy with validation data : " + initialAccuracyVariance);
		//	Node bestTreeVariance = PruneUtils.pruneAlgo(rootVariance, initialAccuracyVariance, l,k, validationExamples,dataSet,"variance-prune-log.txt");
		 //    toPrint="yes";
			if(toPrint.equalsIgnoreCase("yes"))
			{
				printTreeTraverse(root,0);
			}
			else
			{
					System.out.println("This goes to the console");
					PrintStream console = System.out;

				//	File file = new File("Data" + File.separator+ dataSet + File.separator + "variance-pruned-best-tree.txt");
					File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet2/information-prune-best-tree-variance.txt");
					FileOutputStream fos = new FileOutputStream(file);
					PrintStream ps = new PrintStream(fos);
					System.setOut(ps);
					//System.out.println("This goes to out.txt");
				//	printTreeTraverse(bestTreeVariance,0);
					System.setOut(console);
			}
	     // variance impurity tree end
	     
	   
		//ApplicationData.appCompleteDataByRow=rowData;
	/*	Node n11 = new Node();
		n11.setNodeName("N11");
		Node n111 = new Node();
		n111.setNodeName("N111");
		n111.setLeaf(true);
		n111.setOutput("0");
		Node n112 = new Node();
		n112.setNodeName("N112");
		n112.setLeaf(true);
		n112.setOutput("1");
		HashMap<String,Node> nextNode1 = new HashMap<String,Node>();
		nextNode1.put("1", n111);
		nextNode1.put("2", n112);
		n11.setNextNodes(nextNode1);
		
		Node n12 = new Node();
		n12.setNodeName("N12");
		Node n121 = new Node();
		n121.setNodeName("N121");
		n121.setLeaf(true);
		n121.setOutput("0");
		Node n122 = new Node();
		n122.setNodeName("N122");
		n122.setLeaf(true);
		n122.setOutput("1");
		HashMap<String,Node> nextNode2 = new HashMap<String,Node>();
		nextNode2.put("1", n121);
		nextNode2.put("2", n122);
		n12.setNextNodes(nextNode2);
		
		Node n13 = new Node();
		n13.setNodeName("N13");
		Node n131 = new Node();
		n131.setNodeName("N131");
		n131.setLeaf(true);
		n131.setOutput("0");
		Node n132 = new Node();
		n132.setNodeName("N132");
		n132.setLeaf(true);
		n132.setOutput("1");
		Node n133 = new Node();
		n133.setNodeName("N133");
		HashMap<String,Node> nextNode33 = new HashMap<String,Node>();
		Node n1331 = new Node();
		n1331.setNodeName("N1331");
		n1331.setLeaf(true);
		n1331.setOutput("0");
		Node n1332 = new Node();
		n1332.setNodeName("N1332");
		n1332.setLeaf(true);
		n1332.setOutput("1");
		nextNode33.put("5",n1332);
		nextNode33.put("6",n1331);
		n133.setNextNodes(nextNode33);
		//n133.setLeaf(true);
		//n133.setOutput("1");
		HashMap<String,Node> nextNode3 = new HashMap<String,Node>();
		nextNode3.put("1", n131);
		nextNode3.put("2", n132);
		nextNode3.put("3", n133);
		n13.setNextNodes(nextNode3);
		
	
		Node n = new Node();
		n.setNodeName("root");
		HashMap<String,Node> nextNode = new HashMap<String,Node>();
		nextNode.put("1",n11);
		nextNode.put("2",n12);
		nextNode.put("3",n13);
		n.setNextNodes(nextNode);
		printTreeTraverse(n ,0);
		
		HashMap<String,HashMap<String,String>> trainingExample = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> testMap = new HashMap<String,String>();
		testMap.put("root", "3");
		testMap.put("N13", "3");
		testMap.put("N133", "5");
		testMap.put("class", "1");
		trainingExample.put("1", testMap);
		HashMap<String,String> testMap2 = new HashMap<String,String>();
		testMap2.put("root", "3");
		testMap2.put("N13", "3");
		testMap2.put("N133", "6");
		testMap2.put("class", "0");
		trainingExample.put("2", testMap2);
		HashMap<String,String> testMap3 = new HashMap<String,String>();
		//testMap2.put("root", "3");
		testMap3.put("N13", "3");
		testMap3.put("N133", "6");
		testMap3.put("class", "0");
		trainingExample.put("3", testMap3);
		System.out.println(percentageCorrectOutput(trainingExample,"class",n));*/
	//	ArrayList<String> outputList = new ArrayList<String>();
	//	getResultFromTreeForEveryRow(testMap,n, outputList);
		//System.out.println(outputList);
	}
	
	public  Node id3(HashMap<String,HashMap<String,String>> trainingExample, String targetAttributeName, ArrayList<String> attributeList )
	{
		Node root = new Node();
		Map<String,Double> attributeInfoGain = gainService.getInformationGain(trainingExample, attributeList, targetAttributeName);
		if(attributeInfoGain==null)
		{
			String outputValue=null;
			root.setLeaf(true);
			for(String rowKey : trainingExample.keySet())
			{
				for(String attributeKey : trainingExample.get(rowKey).keySet() )
				{
					//outputValue=trainingExample.get(rowKey).get(attributeKey);
					outputValue=trainingExample.get(rowKey).get(targetAttributeName);
					break;
				}
				break;
			}
			
			root.setOutput(outputValue);
			return root;
		}
		else if(attributeList.size()==0)
		{
			root.setLeaf(true);
			root.setOutput(ApplicationData.appData.get("target_frequent_value"));
			//root.setOutput(ApplicationData.appData.get("initial_frequent_value"));
			return root;
		}
		else
		{
			String bestAttributeName = Utils.getBestAttribute(attributeInfoGain);
			ArrayList<String> newAtrributeList = new ArrayList<String>();
			newAtrributeList.addAll(attributeList);
			root.setNodeName(bestAttributeName);
			newAtrributeList.remove(bestAttributeName);
			HashMap<String,ArrayList<String>> valueTrainingExamplesMap = Utils.getAttributeValueWithTrainingExamples(trainingExample, bestAttributeName);
			Node innerNode=null;
			HashMap<String,HashMap<String,String>>  remainingValueRow = null;
			for(String key : valueTrainingExamplesMap.keySet())
			{
				if(valueTrainingExamplesMap.get(key)!=null && valueTrainingExamplesMap.get(key).size()>0)
				{
					if(root.getNextNodes()!=null)
					{
						remainingValueRow = Utils.getAttributeValueWithTrainingExamplesRowObject(trainingExample,valueTrainingExamplesMap.get(key));
						root.getNextNodes().put(key, id3(remainingValueRow,targetAttributeName,newAtrributeList) );
					}
					else
					{
						HashMap<String,Node> nextNodes = new HashMap<String,Node>();
						remainingValueRow = Utils.getAttributeValueWithTrainingExamplesRowObject(trainingExample,valueTrainingExamplesMap.get(key));
						nextNodes.put(key, id3(remainingValueRow,targetAttributeName,newAtrributeList));
						root.setNextNodes(nextNodes);
					}
					
				}
				else
				{
					innerNode.setLeaf(true);
					innerNode.setOutput(ApplicationData.appData.get("target_frequent_value"));
					//innerNode.setOutput(ApplicationData.appData.get("initial_frequent_value"));
					
					if(root.getNextNodes()!=null)
					{
						root.getNextNodes().put(key, innerNode);
					}
					else
					{
						HashMap<String,Node> nextNodes = new HashMap<String,Node>();
						nextNodes.put(key, innerNode);
						root.setNextNodes(nextNodes);
					}
				}
			}
		}
		
		
		return root;
		
	}
	
	public static void printTreeTraverse(Node root, int level)
	{
			//System.out.println(root.getNodeName());
		System.out.println();
		if(root.getNextNodes()!=null)
		{
			for(String key : root.getNextNodes().keySet())
			{
				for(int i=0; i<level;i++)
				{
					System.out.print("|    ");
				}
				Node tempNode=  root.getNextNodes().get(key);
				if(tempNode.isLeaf())
				{
					System.out.print(root.getNodeName() + " = " + key + " : " + tempNode.getOutput());
					System.out.println();
				}
				else
				{
					System.out.print(root.getNodeName() + " = " + key );
					printTreeTraverse(tempNode,level +1);
					
				}
			}
		}
		else
		{
			System.out.print("The only one  node with output = " + root.getOutput());
		}
	
	}
	
	/*public static void printTreeTraverse(Node root, int level, Node parent, String childKey)
	{
		if(root.isLeaf())
		{
			for(int i=0; i<level;i++)
			{
				System.out.print("|    ");
			}
			System.out.print(parent.getNodeName() + " = " + childKey + " : " + root.getOutput());
			System.out.println();
			return;
		}
		else
		{
		for(String key : root.getNextNodes().keySet())
		{

			Node tempNode=  root.getNextNodes().get(key);
			
				
				printTreeTraverse(tempNode,level +1,root,key);
				for(int i=0; i<level;i++)
				{
					System.out.print("|    ");
				}
				System.out.print(root.getNodeName() + " = " + key );
				System.out.println();
		}}
	}*/
	
	public static  void getResultFromTreeForEveryRow(HashMap<String,String> rowData,Node root, ArrayList<String> outputList)
	{
		String tempOutput=null;
		String attributeName = root.getNodeName();
		String attributeValueName = rowData.get(attributeName);
		Node tempNode = null;
		if(attributeValueName!=null)
		{
			if(!root.isLeaf())
				tempNode = root.getNextNodes().get(attributeValueName);
		}
		if(tempNode!=null)
		{
			if(tempNode.isLeaf())
			{
				tempOutput = tempNode.getOutput();
				outputList.add(tempOutput);
			}
			else
			{
				getResultFromTreeForEveryRow(rowData,tempNode,outputList);
			}
		}
		else
		{
			if(root.isLeaf())
			{
				tempOutput = root.getOutput();
				outputList.add(tempOutput);
			}
			else
			{
				for(String key : root.getNextNodes().keySet())
				{
					tempNode = root.getNextNodes().get(key);
					getResultFromTreeForEveryRow(rowData,tempNode,outputList);
				}
			}
		}
		
	}
	
    public static double percentageCorrectOutput(HashMap<String,HashMap<String,String>> rowData,String classAttribute,Node node)
    {
    	HashMap<String,String> row = null;
    	ArrayList<String> rowOutputList = null;
    	int successCount=0;
    	int totalCount=0;
    	for(String rowKey : rowData.keySet())
    	{
    		row  = rowData.get(rowKey);
    		if(row.size()>0)
    		{
    			rowOutputList = new ArrayList<String>();
    			getResultFromTreeForEveryRow(row,node, rowOutputList);
    			if(isCorrect(rowOutputList,row.get(classAttribute)))
    				successCount = successCount+1;
    			totalCount= totalCount+1;
    		}
    	}
    	
    	return successCount/(Double.valueOf(totalCount));
    }
    
    public static boolean isCorrect(ArrayList<String> outputList,String classValue)
    {
    	if(outputList.size()==1)
    	{
    		return outputList.get(0).equals(classValue);
    	}
    	else if(outputList.size()>1)
    	{
    		HashMap<String,Integer> valueCount = new HashMap<String,Integer>();
    		for(String output : outputList)
    		{
    			if(valueCount.get(output)!=null)
    			{
    				valueCount.put(output, valueCount.get(output)+1);
    			}
    			else
    			{
    				valueCount.put(output, 1);
    			}
    		}
    		String finalOutput =null;
    		int maxCount=0;
    		Integer tempCount=0;
    		for(String key : valueCount.keySet())
    		{
    			tempCount = valueCount.get(key);
    			if(tempCount>=maxCount)
    			{
    				maxCount = tempCount;
    				finalOutput = key;
    			}
    		}
    		   		
    		return finalOutput.equals(classValue);
	
    	}
    	else
    	{
    		//need to revisit
    		return true;
    	}
    }

}
