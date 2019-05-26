package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import exception.GridException;
import misc.TypeNode;
import service.GridService;

public abstract class AbstractNode {

	protected static List<AbstractNode> nodes;
	private static int compteur = 0;

	protected GridService gs;

	protected int id;
	protected List<Link> links;
	protected float weight;
	protected float width;
	protected float height;
	protected boolean visited = false;
	protected int x;
	protected int y;

	protected Dimension pos;

	protected AbstractNode previous;

	protected TypeNode type;

	public AbstractNode() throws GridException {
		this(0, 0, TypeNode.NODE);
	}

	public AbstractNode(int x, int y, TypeNode type) throws GridException {
		gs = GridService.getInstance();
		this.id = compteur;
		this.type = type;
		this.links = new ArrayList<>();
		this.weight = -1;
		this.visited = false;
		this.x = x;
		this.y = y;
		this.width = gs.getWidthCol();
		this.height = gs.getHeightCol();
		this.previous = null;
		this.pos = gs.getPositionFromPixel(new Dimension(this.getCenterX(), this.getCenterY()));
		if (nodes == null)
			nodes = new ArrayList<>();
		nodes.add(this);
		compteur++;
	}

	public void addLink(Link link) {
		link.setNodeId(this.id);
		links.add(link);
	}

	public int getId() {
		return id;
	}

	public List<Link> getLinks() {
		return links;
	}

	public AbstractNode getPrevious() {
		return this.previous;
	}

	public boolean isVisited() {
		return visited;
	}

	public float getWeight() {
		return weight;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void setPrevious(AbstractNode previous) {
		this.previous = previous;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public static List<AbstractNode> getNodes() {
		return nodes;
	}

	public static void setNodes(List<AbstractNode> nodes) {
		AbstractNode.nodes = nodes;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public TypeNode getType() {
		return this.type;
	}

	public void setType(TypeNode type) {
		this.type = type;
	}

	public Dimension getPos() {
		return this.pos;
	}

	public void setPos(Dimension pos) {
		this.pos = pos;
	}

	public int getCenterX() {
		return this.x + (int) this.width / 2;
	}

	public int getCenterY() {
		return this.y + (int) this.height / 2;
	}

	@Override
	public String toString() {
		return "Node -> [id] = " + this.id + //
				" [weight] = " + this.weight + //
				" [visited] = " + this.visited + //
				" [position] = " + this.pos + //
				" [type] = " + this.type;//

	}

	public void paint(Graphics g) {
		if (this.type.equals(TypeNode.NODE))
			g.setColor(Color.RED);
		if (this.type.equals(TypeNode.END))
			g.setColor(Color.GREEN);
		if (this.type.equals(TypeNode.BEGIN))
			g.setColor(Color.ORANGE);

		g.fillRect(x, y, Math.round(width), Math.round(height));
	}
}
