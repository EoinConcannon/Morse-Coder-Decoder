package ie.atu.sw;

/*
In-order Traversal Algorithm
------------------------------------------
If Tree is Empty Return
Else
   In-order traverse the left subtree
   Visit the root node
   In-order traverse the right subtree

*/
import static java.lang.System.*;

public class InOrderTraverser<E> implements Visitor<E>{
	public void visit(BinaryNode<E> node){
		if (node == null) return;
		visit(node.getLeftSubtree());
		out.println(node.getData());
		visit(node.getRightSubtree());
	}
}