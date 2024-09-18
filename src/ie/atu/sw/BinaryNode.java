package ie.atu.sw;

public class BinaryNode<E>{
	private BinaryNode<E> parent;
	private BinaryNode<E> left;
	private BinaryNode<E> right;
	private E data;
	
	public BinaryNode() {
	}

	public BinaryNode(E data) {
		this.data = data;
	}

	public BinaryNode(BinaryNode<E> parent) {
		super();
		this.parent = parent;
	}

	public BinaryNode<E> getParent() {
		return parent;
	}

	public void setParent(BinaryNode<E> parent) {
		this.parent = parent;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public BinaryNode<E> getLeftSubtree(){
		return this.left;
	}
	
	public void setLeftSubtree(BinaryNode<E> left){
		left.setParent(this);
		this.left = left;
	}

	public BinaryNode<E> getRightSubtree(){
		return this.right;
	}
	
	public void setRightSubtree(BinaryNode<E> right){
		right.setParent(this);
		this.right = right;
	}

	public boolean isRoot(){
		return parent == null;
	}
	
	public boolean isLeaf(){
		return left == null && right == null;
	}
	
	public boolean replace(BinaryNode<E> node, BinaryNode<E> other){
		if (left != node && right != node) return false;
		
		if (left == node) {
			left = other;
		}else {
			right = other;
		}
		return true;
	}
}