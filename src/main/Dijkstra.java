package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import element.AbstractNode;
import element.Link;
import misc.TypeNode;
import service.NodeService;

public class Dijkstra {

	private String name;
	private List<AbstractNode> chemin;
	private NodeService ns;
	private float weightFinal;

	public Dijkstra(String name, List<AbstractNode> node_list) {
		this.ns = NodeService.getInstance();
		this.name = name;
		this.chemin = new ArrayList<>();
		this.weightFinal = 0;
	}

	public void runAlgorithm() {
		ns.initNodeVisited();
		ns.initNodeWeight();
		AbstractNode currentNode = AbstractNode.getNodes().get(0);
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
		getClosestWay();
	}

	private void updateNodeWeight(AbstractNode currentNode) {
		for (Link link : currentNode.getLinks()) {
			AbstractNode noeudTarget = link.getTarget();
			float weight = noeudTarget.getWeight(); // poids du noeuds "target"
			if (weight == -1 || link.getWeight() + currentNode.getWeight() < weight) {
				noeudTarget.setWeight(link.getWeight() + currentNode.getWeight());
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

	public void getClosestWay() {
		AbstractNode current = AbstractNode.getNodes().get(AbstractNode.getNodes().size() - 1);
		this.chemin.add(current);
		while (!(current.equals(AbstractNode.getNodes().get(0)))) {
			AbstractNode previous = null;
			if (current.getPrevious() != null)
				previous = current.getPrevious();
			this.chemin.add(previous);
			current = previous;
		}
		Collections.reverse(this.chemin);
	}

	public float getWeightFinal() {
		return this.weightFinal;
	}

	public void setWeightFinal(float weightFinal) {
		this.weightFinal = weightFinal;
	}

	public List<AbstractNode> getChemin() {
		return this.chemin;
	}

	public void setChemin(List<AbstractNode> chemin) {
		this.chemin = chemin;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
