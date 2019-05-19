package service;

import java.awt.Dimension;

import element.AbstractNode;
import element.Node;
import misc.TypeNode;

public class NodeService {

	private static NodeService instance;

	private NodeService() {

	}

	public static NodeService getInstance() {
		if (instance == null)
			instance = new NodeService();
		return instance;
	}

	public boolean isAllVisited() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (!node.isVisited())
				return false;
		}
		return true;
	}

	public AbstractNode getNodeById(int id) {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (node.getId() == id)
				return node;
		}
		return null;
	}

	public AbstractNode getNodeByPos(Dimension pos) {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (node.getPos().equals(pos))
				return node;
		}
		return null;
	}

	public void initNodeWeight() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			node.setWeight((node.getType().equals(TypeNode.BEGIN)) ? 0 : -1);
		}
	}

	public void initNodeVisited() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			node.setVisited(false);
		}
	}

	public AbstractNode getNode(TypeNode type, int x, int y) {
		return new Node(x, y, type);
	}
}
