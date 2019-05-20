package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import element.AbstractNode;
import element.Link;
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

	public List<AbstractNode> runAlgorithm() {
		ns.initNodeVisited();
		ns.initNodeWeight();
		AbstractNode currentNode = beginNode;
		int security = 0;
		while (!ns.isAllVisited()) {
			currentNode.setVisited(true);
			updateNodeWeight(currentNode);
			List<AbstractNode> knownNodes = getAllKnownNodes(AbstractNode.getNodes());
			currentNode = getNodeWithSmallestWeight(knownNodes);
			security++;
			if (security > AbstractNode.getNodes().size())
				break;
		}
		for (AbstractNode node : AbstractNode.getNodes())
			if (node.getType().equals(TypeNode.END))
				this.weightFinal = node.getWeight();
			else
				this.weightFinal = -1;

		return getClosestWay();
	}

	private void updateNodeWeight(AbstractNode currentNode) {
		for (Link link : currentNode.getLinks()) {
			AbstractNode noeudTarget = link.getTarget();
			float weight = noeudTarget.getWeight(); // poids du noeuds "target"
			if (weight == -1 || link.getDistance() + currentNode.getWeight() < weight) {
				noeudTarget.setWeight(link.getDistance() + currentNode.getWeight());
				noeudTarget.setPrevious(currentNode);
			}
		}
	}

	private List<AbstractNode> getAllKnownNodes(List<AbstractNode> list) {
		List<AbstractNode> result = new ArrayList<>();
		for (AbstractNode node : list) {
			if (node.getWeight() != -1 && !node.isVisited()) {
				result.add(node);
			}
		}
		return result;
	}

	private AbstractNode getNodeWithSmallestWeight(List<AbstractNode> knownNodes) {
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

	public List<AbstractNode> getClosestWay() {
		List<AbstractNode> result = new ArrayList<>();
		AbstractNode current = AbstractNode.getNodes().get(0);
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (node.getType().equals(TypeNode.END))
				current = node;
		}
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
