package service;

import java.awt.Dimension;

import element.AbstractNode;
import element.Link;
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
			return new LinkService();
		return instance;
	}

	public void craeteLink() {
		for (AbstractNode currentNode : AbstractNode.getNodes()) {
			// right
			for (int i = currentNode.getPos().width + 1; i <= Const.NBR_COL - 1; i++) {
				Dimension currentPos = new Dimension(i, currentNode.getPos().height);
				if ("node".equals(gs.getTypeFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getTypeFromPos(currentPos)))
					break;
			}
			// left
			for (int i = currentNode.getPos().width - 1; i >= 0; i--) {
				Dimension currentPos = new Dimension(i, currentNode.getPos().height);
				if ("node".equals(gs.getTypeFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getTypeFromPos(currentPos)))
					break;
			}
			// down
			for (int i = currentNode.getPos().height + 1; i <= Const.NBR_ROW; i++) {
				Dimension currentPos = new Dimension(currentNode.getPos().height, i);
				if ("node".equals(gs.getTypeFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getTypeFromPos(currentPos)))
					break;
			}

			// up
			for (int i = currentNode.getPos().height - 1; i >= 0; i--) {
				Dimension currentPos = new Dimension(currentNode.getPos().height, i);
				if ("node".equals(gs.getTypeFromPos(currentPos))) {
					currentNode.addLink(new Link(ns.getNodeByPos(currentPos), -1, currentNode.getId()));
					break;
				}
				if ("wall".equals(gs.getTypeFromPos(currentPos)))
					break;
			}
			System.out.println("-----------");
			System.out.println(currentNode);
			System.out.println(currentNode.getLinks());
		}

	}
}
