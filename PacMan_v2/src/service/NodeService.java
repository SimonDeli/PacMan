package service;

import element.AbstractNode;
import element.BeginNode;
import element.EndNode;
import element.Node;
import misc.TypeNode;

public class NodeService {

	private static NodeService instance;

	private NodeService() {

	}

	public static NodeService getInstance() {
		if (instance == null)
			return new NodeService();
		return instance;
	}

	public boolean isAllVisited() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (!node.isVisited())
				return false;
		}
		return true;
	}

//	public void setIdOnAllNodes() {
//		int id = 0;
//		for (AbstractNode node : AbstractNode.getNodes()) {
//			node.setId(id);
//			id++;
//		}
//	}

	public AbstractNode getNodeById(int id) {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (node.getId() == id)
				return node;
		}
		return null;
	}

	public void initNodeWeight() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			node.setWeight((node instanceof BeginNode) ? 0 : -1);
		}
	}

	public void initNodeVisited() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			node.setVisited(false);
		}
	}

	public AbstractNode getNode(TypeNode type, int x, int y) {
		switch (type) {
		case BEGIN:
			return new BeginNode(x, y);
		case END:
			return new EndNode(x, y);
		default:
			return new Node(x, y);
		}
	}
}
