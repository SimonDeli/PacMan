package service;

import java.awt.Dimension;

import element.AbstractNode;
import element.Link;
import exception.GridException;
import exception.LinkException;
import misc.Const;

public class LinkService {

	private GridService gs;
	private NodeService ns;

	private static LinkService instance;

	private LinkService() {
		gs = GridService.getInstance();
		ns = NodeService.getInstance();
	}

	public static LinkService getInstance() {
		if (instance == null)
			instance = new LinkService();
		return instance;
	}

	public void craeteLink() throws LinkException, GridException {
		if (AbstractNode.getNodes() == null || AbstractNode.getNodes().isEmpty())
			throw new LinkException("Impossible to create links cuz' node list is null or empty");

		for (AbstractNode currentNode : AbstractNode.getNodes()) {
			// right
			for (int i = currentNode.getPos().width + 1; i <= Const.NBR_COL - 1; i++) {
				Dimension currentPos = new Dimension(i, currentNode.getPos().height);
				if ("node".equals(gs.getElementFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getElementFromPos(currentPos)))
					break;
			}
			// left
			for (int i = currentNode.getPos().width - 1; i >= 0; i--) {
				Dimension currentPos = new Dimension(i, currentNode.getPos().height);
				if ("node".equals(gs.getElementFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getElementFromPos(currentPos)))
					break;
			}
			// down
			for (int i = currentNode.getPos().height + 1; i <= Const.NBR_ROW; i++) {
				Dimension currentPos = new Dimension(currentNode.getPos().width, i);
				if ("node".equals(gs.getElementFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getElementFromPos(currentPos)))
					break;
			}

			// up
			for (int i = currentNode.getPos().height - 1; i >= 0; i--) {
				Dimension currentPos = new Dimension(currentNode.getPos().width, i);
				if ("node".equals(gs.getElementFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getElementFromPos(currentPos)))
					break;
			}
		}
		initDistanceLink();
	}

	public void initDistanceLink() {
		for (AbstractNode current : AbstractNode.getNodes()) {
			for (Link link : current.getLinks()) {
				int distance = 0;
				if (current.getPos().width == link.getTarget().getPos().width)
					distance = Math.abs((current.getPos().height - link.getTarget().getPos().height));
				else if (current.getPos().height == link.getTarget().getPos().height) {
					distance = Math.abs((current.getPos().width - link.getTarget().getPos().width));
				}
				link.setDistance(distance);
			}
		}
	}

	public void printNodeAndLink() {
		for (AbstractNode currentNode : AbstractNode.getNodes()) {
			System.out.println("-----------");
			System.out.println(currentNode);
			for (Link link : currentNode.getLinks()) {
				System.out.println("__" + link);
			}
		}
	}
}
