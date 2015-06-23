package ml.assignment.decisiontree.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import ml.assignment.decisiontree.domain.Node;
import ml.assignment.decisiontree.execution.ApplicationData;
import ml.assignment.decisiontree.execution.Main;

import org.apache.commons.lang3.SerializationUtils;

public class PruneUtils {
	
	public static Node copyTree(Node root)
	{
		
		return SerializationUtils.clone(root);
	}
	
	public static int getRandomNumber(int min, int max)
	{
		    Random rand = new Random();
		    int randomNum = rand.nextInt((max - min) + 1) + min;
		    return randomNum;
	}
	
	public static int getCountOfInternalLeaf(Node root, Integer count)
	{
		HashMap<String,String> childNodes=null;
		Node temNode=null;
		if(root.getNextNodes()!=null && root.getNextNodes().size()>0)
		{
			for(String key : root.getNextNodes().keySet())
			{
			
				temNode = root.getNextNodes().get(key);
				if(temNode!=null)
				{
					if(!temNode.isLeaf())
					{
						count=count+1;
						count = getCountOfInternalLeaf(temNode,count);
					}
				}
			}
		}
		return count;
	}
	
	public static int getTotalNode(Node root, Integer count)
	{
		HashMap<String,String> childNodes=null;
		Node temNode=null;
		if(root.getNextNodes()!=null && root.getNextNodes().size()>0)
		{
			for(String key : root.getNextNodes().keySet())
			{
			
				temNode = root.getNextNodes().get(key);
				if(temNode!=null)
				{
						count=count+1;
						count = getTotalNode(temNode,count);
				}
			}
		}
		return count;
	}
	
	public static ArrayList<String> getOutputForPrunedRoot(Node root, ArrayList<String> outputList)
	{
		//HashMap<String,String> childNodes=null;
		Node temNode=null;
		if(root.getNextNodes()!=null && root.getNextNodes().size()>0)
		{
			for(String key : root.getNextNodes().keySet())
			{
				temNode = root.getNextNodes().get(key);
				if(temNode!=null)
				{
					if(temNode.isLeaf())
					{
						outputList.add(temNode.getOutput());
					}
					else
					{
						getOutputForPrunedRoot(temNode,outputList);
					}
				}
			}
		}
		return outputList;
	}
	
	public static void findRemoveNode(Node root, int nodeNumber)
	{
		Node tempNode=null;
		ArrayList<Node> tempNodeList = new ArrayList<Node>();
		int tempCount=1;
		
		boolean pruneFlag=false;
		boolean exitFlag=false;
		//Node removalNode=null;
		Node removalParentNode=null;
		String removalParentKey=null;
		if(nodeNumber==1)
		{
			root.setLeaf(true);
			root.setNextNodes(null);
			// to do maximum output by initial data;
			root.setOutput(	ApplicationData.appData.get("initial_frequent_value"));
			exitFlag=true;
		}
		else
		{
			while(true)
			{
				if(!root.isLeaf())
				{
					for(String key : root.getNextNodes().keySet())
					{
						tempNode = root.getNextNodes().get(key);
						if(!tempNode.isLeaf())
						{
							tempCount = tempCount+1;
							tempNodeList.add(tempNode);
						}
						if(tempCount==nodeNumber)
						{
							pruneFlag=true;
							removalParentNode = root;
							removalParentKey = key;
							break;
						}
					}
					if(pruneFlag)
						break;
					if(tempNodeList.size()>0)
					{
						root=tempNodeList.get(0);
						tempNodeList.remove(0);
					}
					else
					{
						break;
					}
				}
			}
			if(pruneFlag)
			{
				
			}
			else
			{
				System.out.println("not found");
			}
		}
		
	}
	
	public static Node pruneAlgo(Node root,double correctPercentage, int l, int k, HashMap<String,HashMap<String,String>> validationExamples, String dataSet, String logFileName) throws FileNotFoundException
	{
		System.out.println("---------------------------------Pruning Start---------------------------------------");
		System.out.println(" l : "+ l + " , k : "+k);
		//System.out.println("This goes to the console");
		PrintStream console = System.out;

		File file = new File("Data" + File.separator+ dataSet + File.separator + logFileName);
		//File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW1/DataSet2/information-tree.txt");
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		//System.out.println("This goes to out.txt");
	
		//printTreeTraverse(root,0,null, null);
		
		Node bestTree = root;
		Node tempTree = null;
		int m=0;
		int n=0;
		int p=0;
		double tempCorrectPercentage=0.0;
		Double maximumAccuracy= null;
		int bestL=l;
		int bestK=k;
		double tempTestAccuracyPercentage= 0.0;
		//int validN=0;
		System.out.println("---------------------------------Pruning Start---------------------------------------");
		System.out.println(" l : "+ l + " , k : "+k);
		for(int i=1; i<=l;i++)
		{
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> L : "+i);
			tempTree = copyTree(root);
			m= getRandomNumber(1, k);
			System.out.println("inner loop limit value by random -  M : "+m);
			for(int j=1; j<=m;j++)
			{
				System.out.println("inner loop  with k : "+j);
				n= getCountOfInternalLeaf(tempTree, 1);
				System.out.println("number of internal leaf-  N : "+n);
				if(n!=0)
				{
					/*if(n<2)
						p= getRandomNumber(2, 2);
					else
						p= getRandomNumber(2, n);*/
					p= getRandomNumber(1, n);
					System.out.println("pth node for prune-  P : "+p);
				
					Node tempNode=null;
					ArrayList<Node> tempNodeList = new ArrayList<Node>();
					int tempCount=1;
					boolean pruneFlag=false;
					boolean exitFlag=false;
					//Node removalNode=null;
					Node removalParentNode=null;
					String removalParentKey=null;
					if(p==1)
					{
						System.out.println("root node pruned ");
						tempTree.setLeaf(true);
						tempTree.setNextNodes(null);
						// to do maximum output by initial data;
						tempTree.setOutput(	ApplicationData.appData.get("initial_frequent_value"));
						exitFlag=true;
					}
					else
					{
						Node queueNode=tempTree;
						while(true)
						{
							if(!queueNode.isLeaf())
							{
								for(String key : queueNode.getNextNodes().keySet())
								{
									tempNode = queueNode.getNextNodes().get(key);
									if(!tempNode.isLeaf())
									{
										tempCount = tempCount+1;
										tempNodeList.add(tempNode);
									}
									if(tempCount==p)
									{
										pruneFlag=true;
										removalParentNode = queueNode;
										removalParentKey = key;
										break;
									}
								}
								if(pruneFlag)
									break;
								if(tempNodeList.size()>0)
								{
									queueNode=tempNodeList.get(0);
									tempNodeList.remove(0);
								}
								else
								{
									break;
								}
							}
						}
						if(pruneFlag)
						{
							System.out.println("internal node pruned ");
							ArrayList<String> outputList = new ArrayList<String>();
							outputList = getOutputForPrunedRoot(removalParentNode,outputList);
							Node leafNode = new Node();
							leafNode.setLeaf(true);
							leafNode.setOutput(Utils.maximumValue(outputList));
							removalParentNode.getNextNodes().put(removalParentKey,leafNode);
						}
						else
						{
							System.out.println("internal node not found");
						}
					}
				
				}
			}
			System.out.println("----------inner loop exit---------");
			tempCorrectPercentage= Main.percentageCorrectOutput(ApplicationData.appCompleteDataByRow.get("validation-set"), ApplicationData.appData.get("Class"), tempTree);
			
			if(maximumAccuracy==null)
			{
				bestTree = tempTree;
				maximumAccuracy = tempCorrectPercentage ;
				bestL = i;
				bestK=m;
				tempTestAccuracyPercentage= Main.percentageCorrectOutput(ApplicationData.appCompleteDataByRow.get("test-set"), ApplicationData.appData.get("Class"), tempTree);
			}
			else
			{
				if(tempCorrectPercentage>maximumAccuracy)
				{
					bestTree = tempTree;
					maximumAccuracy = tempCorrectPercentage;
					bestL = i;
					bestK=m;
					tempTestAccuracyPercentage= Main.percentageCorrectOutput(ApplicationData.appCompleteDataByRow.get("test-set"), ApplicationData.appData.get("Class"), tempTree);
				}
			}
			System.out.println(" L : "+ i + " accuracy = "+tempCorrectPercentage +" , maximum accuracy = "+ maximumAccuracy);
		}
		System.out.println(">>>>>>>>>>>>>>>>>outer loop exit>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	
		System.out.println("Maximum Accuracy Achieved after pruning with l : " + bestL + " , k : "+ bestK +" = " + maximumAccuracy + " , Initial accuracy =s "+ correctPercentage + " : test data accuracy : "+ tempTestAccuracyPercentage);
		
		System.out.println("---------------------------------Pruning End---------------------------------------");
	
		System.setOut(console);
		
		System.out.println("Maximum Accuracy Achieved after pruning with l : " + bestL + " , k : "+ bestK +" = " + maximumAccuracy + " , Initial accuracy =s "+ correctPercentage + " : test data accuracy : "+ tempTestAccuracyPercentage);
		
		System.out.println("---------------------------------Pruning End---------------------------------------");
		
		
		return bestTree;
	}
	
	
	
	public static void main(String args[]) throws CloneNotSupportedException
	{
		/*Node n11 = new Node();
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
		
		Main.printTreeTraverse(n ,0);
		System.out.println(getCountOfInternalLeaf(n,0));
		ArrayList<String> outputList = new ArrayList<String>();
		System.out.println(getOutputForPrunedRoot(n13,outputList));*/
		System.out.println(getRandomNumber(2, 2));
		//Node n1 =  org.apache.commons.lang3.SerializationUtils.clone(n);
		//n.getNextNodes().get("1").getNextNodes().put("1", null);
		//System.out.println(n1.getNextNodes().get("1").getNextNodes().get("1"));
	}

}
