package element;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import service.GridService;

public abstract class AbstractNode {

	private static int compteur = 0;
	protected int id;
	protected List<Link> links;
	protected float weight;
	protected boolean visited = false;
	protected int x;
	protected int y;
	protected float width;
	protected float height;
	protected int gap;
	protected AbstractNode previous;
	protected static List<AbstractNode> nodes;
	protected GridService gs;

	public AbstractNode() {
		this(0, 0);
	}

	public AbstractNode(int x, int y) {
		gs = GridService.getInstance();
		this.id = compteur;
		this.links = new ArrayList<>();
		this.weight = -1;
		this.visited = false;
		this.x = x;
		this.y = y;
		this.width = gs.getWidthCol();
		this.height = gs.getHeightCol();
		this.previous = null;
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

	@Override
	public String toString() {
		return "Node -> [id] = " + this.id + //
				" [weight] = " + this.weight + //
				" [visited] = " + this.visited;//

	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, Math.round(width), Math.round(height));
	}
}
