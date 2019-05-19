package element;

import misc.TypeNode;

public class Node extends AbstractNode {

	public Node(int x, int y, TypeNode type) {
		super(x, y, type);
	}

	@Override
	public String toString() {
		return super.toString() + " [type] = node";
	}

}
