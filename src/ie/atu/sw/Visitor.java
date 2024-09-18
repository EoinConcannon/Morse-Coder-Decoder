package ie.atu.sw;

public interface Visitor<E> {
	public void visit(BinaryNode<E> node);
}
