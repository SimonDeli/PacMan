package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import element.AbstractNode;
import element.Link;
import exception.DijkstraException;
import exception.LinkException;
import misc.TypeNode;

public class Dijkstra {

	private NodeService ns;
	private float weightFinal;
	private AbstractNode beginNode;

	public Dijkstra(AbstractNode beginNode) {
		this.ns = NodeService.getInstance();
		this.weightFinal = 0;
		this.beginNode = beginNode;
	}

	public List<AbstractNode> runAlgorithm() throws DijkstraException, LinkException {
		if (AbstractNode.getNodes() == null || AbstractNode.getNodes().isEmpty())
			throw new DijkstraException();

		ns.initNodeVisited();
		AbstractNode currentNode = beginNode;
		ns.initNodeType(beginNode);
		ns.initNodeWeight();

		int security = 0;
		while (!ns.isAllVisited()) {
			currentNode.setVisited(true);
			updateNodeWeight(currentNode);
			List<AbstractNode> knownNodes = getAllKnownNodes();
			currentNode = getNodeWithSmallestWeight(knownNodes);
			security++;
			if (security > AbstractNode.getNodes().size())
				throw new DijkstraException("Problem on while condition.");
		}
		return getClosestWay();
	}

	private void updateNodeWeight(AbstractNode currentNode) throws LinkException {
		if (currentNode.getLinks() == null || currentNode.getLinks().isEmpty())
			throw new LinkException("Node number " + currentNode.getId() + " has no Links");

		for (Link link : currentNode.getLinks()) {
			AbstractNode noeudTarget = link.getTarget();
			float weight = noeudTarget.getWeight(); // poids du noeuds "target"
			if (weight == -1 || link.getDistance() + currentNode.getWeight() < weight) {
				noeudTarget.setWeight(link.getDistance() + currentNode.getWeight());
				noeudTarget.setPrevious(currentNode);
			}
		}
	}

	private List<AbstractNode> getAllKnownNodes() {
		List<AbstractNode> result = new ArrayList<>();
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (node.getWeight() != -1 && !node.isVisited()) {
				result.add(node);
			}
		}
		return result;
	}

	private AbstractNode getNodeWithSmallestWeight(List<AbstractNode> knownNodes) throws DijkstraException {
		if (knownNodes == null)
			throw new DijkstraException("The know nodes list is null");

		AbstractNode result = null;
		float weight = -1;
		for (AbstractNode node : knownNodes) {
			if (node.getWeight() < weight || weight == -1) {
				weight = node.getWeight();
				result = node;
			}
		}
		return result;
	}

	public List<AbstractNode> getClosestWay() throws DijkstraException {
		List<AbstractNode> result = new ArrayList<>();
		AbstractNode current = AbstractNode.getNodes().get(0);
		for (AbstractNode node : AbstractNode.getNodes())
			if (node.getType().equals(TypeNode.END))
				current = node;

		if (!current.getType().equals(TypeNode.END))
			throw new DijkstraException("There are no EndNode on the list");

		result.add(current);
		while (!(beginNode.equals(current))) {
			AbstractNode previous = null;
			if (current.getPrevious() != null)
				previous = current.getPrevious();

			result.add(previous);
			current = previous;
		}
		Collections.reverse(result);
		return result;
	}

	public float getWeightFinal() {
		return this.weightFinal;
	}

	public void setWeightFinal(float weightFinal) {
		this.weightFinal = weightFinal;
	}
}
