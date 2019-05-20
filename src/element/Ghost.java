package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import misc.Const;
import service.GhostService;
import service.NodeService;

public class Ghost extends AbstractPersonnage {

	private static List<Ghost> ghosts;

	public static List<Ghost> getGhosts() {
		return ghosts;
	}

	public static void setGhosts(List<Ghost> ghosts) {
		Ghost.ghosts = ghosts;
	}

	private GhostService ghs;

	private NodeService ns;

	private List<AbstractNode> chemin;

	private AbstractNode currentNode;

	public Ghost(int x, int y, int width, int height) {
		super(x, y, width, height);
		ns = NodeService.getInstance();
		ghs = GhostService.getInstance();
		if (ghosts == null)
			ghosts = new ArrayList<>();
		ghosts.add(this);
		chemin = new ArrayList<>();
		currentNode = ns.getNodeByPos(this.actualPos);
	}

	private void findDirection() {
		if (!this.chemin.isEmpty()) {
			AbstractNode target = this.chemin.get(0);
			if (!target.getPos().equals(this.actualPos)) {
				if (target.getPos().height == this.actualPos.height) {
					if (target.getPos().width > this.actualPos.width) {
						resetDirection(false);
						right = true;
					} else if (target.getPos().width < this.actualPos.width) {
						resetDirection(false);
						left = true;
					}
				} else if (target.getPos().width == this.actualPos.width) {
					if (target.getPos().height > this.actualPos.height) {
						resetDirection(false);
						down = true;
					} else if (target.getPos().height < this.actualPos.height) {
						resetDirection(false);
						up = true;
					}
				}
			} else {
				this.currentNode = target;
				resetDirection(false);
				this.chemin.remove(0);
			}
		}
	}

	public List<AbstractNode> getChemin() {
		return this.chemin;
	}

	public AbstractNode getCurrentNode() {
		return this.currentNode;
	}

	@Override
	public void move() {
		for (double i = 0; i < speed / Const.MAX_FPS; i++) {
			this.actualPos = gs.getPositionFromPixel(new Dimension(this.anchor.width, this.anchor.height));
			findDirection();
			direction();
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.PINK);
		g.fillOval((int) this.x, (int) this.y, width, height);
	}

	public void setChemin(List<AbstractNode> chemin) {
		this.chemin = chemin;
	}

	public void setCurrentNode(AbstractNode currentNode) {
		this.currentNode = currentNode;
	}

}
