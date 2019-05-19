package element;

public class Link {

	private AbstractNode target;
	private float distance;
	private int nodeId;
	private boolean select;

	public Link() {
		this(null, -1);
	}

	public Link(AbstractNode target, float distance) {
		this(target, distance, 0);
	}

	public Link(AbstractNode target, float distance, int nodeId) {
		this.target = target;
		this.distance = distance;
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

	public float getDistance() {
		return distance;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public void setTarget(AbstractNode target) {
		this.target = target;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "Link -> [currentNode_Id] = " + this.nodeId + //
				" [tagerNode_id] = " + this.target.getId() + //
				" [distance] = " + this.distance;//
	}

}
