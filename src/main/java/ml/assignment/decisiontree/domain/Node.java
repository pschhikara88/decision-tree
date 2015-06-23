package ml.assignment.decisiontree.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Node implements Serializable{
	
	private boolean isRoot;
	
	private boolean isLeaf;
	
	private String nodeName;
	
	private ArrayList<String> remainingSet;
	
	private HashMap<String,ArrayList<String>> nodeValues;
	
	private HashMap<String,Node> nextNodes; 
	
	private ArrayList<String> remainingAttributes;
	
	private String output;

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ArrayList<String> getRemainingSet() {
		return remainingSet;
	}

	public void setRemainingSet(ArrayList<String> remainingSet) {
		this.remainingSet = remainingSet;
	}

	public HashMap<String, ArrayList<String>> getNodeValues() {
		return nodeValues;
	}

	public void setNodeValues(HashMap<String, ArrayList<String>> nodeValues) {
		this.nodeValues = nodeValues;
	}

	public HashMap<String, Node> getNextNodes() {
		return nextNodes;
	}

	public void setNextNodes(HashMap<String, Node> nextNodes) {
		this.nextNodes = nextNodes;
	}

	public ArrayList<String> getRemainingAttributes() {
		return remainingAttributes;
	}

	public void setRemainingAttributes(ArrayList<String> remainingAttributes) {
		this.remainingAttributes = remainingAttributes;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}


	
	

}
