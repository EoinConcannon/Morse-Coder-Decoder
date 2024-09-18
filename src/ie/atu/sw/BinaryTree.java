//all files relating to the binary tree are from binary trees lab
//THIS .java file has been modified to encode/decode while searching for nodes

package ie.atu.sw;

import java.util.Comparator;
import static java.lang.System.*;

public class BinaryTree<E> {
	private Comparator<E> comparator;
	private BinaryNode<E> root = null;
	
	public BinaryTree(Comparator<E> comparator, E node) {
		this.comparator = comparator;
		this.root = new BinaryNode<E>(node);
	}
	
	public BinaryNode<E> root(){ 
		return this.root;
	}
	
	/* The following methods are wrapper methods for the BST search,
	 * insert, remove, traverse and inOrderTraverse. The actual BST
	 * methods are private. This protects the BST from being used
	 * incorrectly and allows the user to use a simplified "Tree"
	 * interface.
	 */
	public E search(E searchItem) {
		return search(root, searchItem);
	}

	public void insert(E searchItem) {
		insert(root, new BinaryNode<E>(searchItem));
	}

	public void traverse() {
		traverse(root, 0, "root");
	}
	
	public void inOrderTraverse() {
		inOrderTraverse(root);
	}
	//----------- End of Wrapper Methods -----------
	
	
	/*	
	Recursive Binary Search - O(log(n))
	------------------------------------
		If root is Null
		     Item is not in tree. Return Null
		Compare the value of target with root's data.
		If they are equal
		     Target found. Return root's data.
		Else if target < root's data
		     Return result of searching left subtree
		Else
		     Return result of searching right subtree
	*/
	public E search(BinaryNode<E> node, E searchItem){
		String encode = "";
		if (node == null) return null;
		
		//encoding process
		//uses the node key to find the correct node
		//when searching, if the search goes left it adds a dot to a global string, right will add a dash
		int result = comparator.compare(searchItem, node.getData());
		if (result == 0){
			encode = encode + " ";
			encodeTxt(encode);
			return node.getData();
		}else if (result < 0){
			//go left
			encode = encode + ".";
			encodeTxt(encode);
			return search(node.getLeftSubtree(), searchItem);
		}else{
			//go right
			encode = encode + "-";
			encodeTxt(encode);
			return search(node.getRightSubtree(), searchItem);
		}

	}
	
	//global string
	public String pubEncode = "";
	
	public String encodeTxt(String encode) {
		pubEncode = pubEncode + encode;
		return pubEncode;
	}
	
	/*
	Inserting into a Binary Search Tree - O(log(n))
	---------------------------------------------
		If root is Null
		     Replace empty tree with new tree with the item at the root. Return True;
		Else If item equals Roots' data
		     Item already in the tree. Return False;
		Else If item < Root's data
		     Recursively insert item into left subtree
		Else
		     Recursively insert item into right subtree
	*/
	private void insert(BinaryNode<E> localRoot, BinaryNode<E> node){
		if (localRoot == null) { //Replace empty tree with new tree with the item at the root.
			localRoot = node;
			return;
		}
		
		int result = comparator.compare(node.getData(), localRoot.getData());
		if (result == 0){
			return; //Item already in the tree.
		}else if (result < 0){ //Recursively insert item into left subtree
			if (localRoot.getLeftSubtree() == null){
				localRoot.setLeftSubtree(node);
			}else{
				insert(localRoot.getLeftSubtree(), node);
			}
		}else{ //Recursively insert item into right subtree
			if (localRoot.getRightSubtree() == null){
				localRoot.setRightSubtree(node);
			}else{
				insert(localRoot.getRightSubtree(), node);
			}
		}
	}
	
	
	/*
	The In-order of a node is that node on the extreme left of its subtree. - O(log(n))
	-----------------------------------------------------------------------------------
	*/
	private BinaryNode<E> getInOrderPredecessor(BinaryNode<E> localRoot){
		if (localRoot.getLeftSubtree() == null) {
			return localRoot;
		}else {
			return getInOrderPredecessor(localRoot.getLeftSubtree());
		}
	}
	
	
	/* 
	 * Depth-First Traversal of Binary Tree. This is a Pre-Order Traversal
	 * --------------------------------------------------------------------
	 * Used for printing out the tree.
	 */
	private void traverse(BinaryNode<E> node, int index, String branch){
		for (int i = 0; i < index; i++) out.print("\t");
		out.println("[" + branch + "]" + node.getData());
		if (node.getLeftSubtree() != null) traverse(node.getLeftSubtree(), index + 1, "left");
		if (node.getRightSubtree() != null) traverse(node.getRightSubtree(), index + 1, "right");
	}
	
	
	/*
	In-order Traversal Algorithm
	------------------------------------------
	If Tree is Empty Return
	Else
	   In-order traverse the left subtree
	   Visit the root node
	   In-order traverse the right subtree

	*/
	private void inOrderTraverse(BinaryNode<E> node){
		if (node == null) return;
		inOrderTraverse(node.getLeftSubtree());
		out.println(node.getData());
		inOrderTraverse(node.getRightSubtree());
	}
}