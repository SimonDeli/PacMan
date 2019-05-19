package element;

public class Link {

	private AbstractNode target;
	private float weight;
	private int nodeId;
	private boolean select;

	public Link() {
		this(null, -1);
	}

	public Link(AbstractNode target, float weight) {
		this(target, weight, 0);
	}

	public Link(AbstractNode target, float weight, int nodeId) {
		this.target = target;
		this.weight = weight;
		this.nodeId = nodeId;
	}

	public boolean isSelect() {
		return this.select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public int getNodeId() {
		return nodeId;
	}

	public AbstractNode getTarget() {
		return target;
	}

	public float getWeight() {
		return weight;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public void setTarget(AbstractNode target) {
		this.target = target;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "Link -> [currentNode_Id] = " + this.nodeId + " [tagerNode_id] = " + this.target.getId() + " [weight] = "
				+ this.weight;
	}

}
