package element;

public class Node extends AbstractNode {

	public Node(int x, int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return super.toString() + " [type] = node";
	}

}
