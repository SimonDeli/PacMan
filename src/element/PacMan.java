package element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import misc.TypeNode;
import service.Dijkstra;
import service.NodeService;

public class PacMan extends AbstractPersonnage implements KeyListener {

	private boolean tmpRight = false, tmpLeft = false, tmpUp = false, tmpDown = false;
	private boolean centered;
	private boolean wait = false;

	private Dimension destinationPos;

	private String dir = "";
	private AbstractNode currentEndNode;

	private static NodeService ns;

	public PacMan(int x, int y, int width, int height, int gap) {
		super(x, y, width, height, gap);
		ns = NodeService.getInstance();
		this.destinationPos = actualPos;
		this.currentEndNode = ns.getNodeByPos(this.actualPos);
	}

	@Override
	public void paint(Graphics g) {
		paintPacMan(g);
	}

	public void paintPacMan(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(this.x, this.y, width, height);
	}

	@Override
	public void move() {
		this.centered = gs.isCentered(this);
		this.actualPos = gs.getPositionFromPixel(new Dimension(this.anchor.width, this.anchor.height));
		interval();
		if (wait)
			waitToMove();
		if (!isCollide())
			direction();

	}

	private void interval() {
		if (centered) {
			setDestinationPosFromDir(this.dir);
			setIntervalEndNode();
		}

	}

	// A OPTIMISER
	private void setIntervalEndNode() {
		for (AbstractNode node : AbstractNode.getNodes()) {
			if (this.actualPos.equals(node.getPos())) {
				node.setType(TypeNode.END);
				AbstractNode current = node;
				for (AbstractNode otherNode : AbstractNode.getNodes())
					if (!current.equals(otherNode) && !otherNode.getType().equals(TypeNode.BEGIN))
						otherNode.setType(TypeNode.NODE);
				if (!this.currentEndNode.equals(current)) {
					this.currentEndNode = current;
					for (Ghost ghost : Ghost.getGhosts()) {
						Dijkstra d = new Dijkstra(ghost.getCurrentNode());
						ghost.setChemin(d.runAlgorithm());
					}
				}
			}
		}
	}

	private boolean isCollide() {
		if (Wall.getDim().contains(this.destinationPos) && centered)
			return true;
		return false;
	}

	private void waitToMove() {
		if (tmpRight && centered) {
			resetDirection(false);
			right = true;
			wait = false;
			resetDirection(true);
		} else if (tmpLeft && centered) {
			resetDirection(false);
			left = true;
			wait = false;
			resetDirection(true);
		} else if (tmpUp && centered) {
			resetDirection(false);
			up = true;
			wait = false;
			resetDirection(true);
		} else if (tmpDown && centered) {
			resetDirection(false);
			down = true;
			wait = false;
			resetDirection(true);
		}
	}

	private void setDestinationPosFromDir(String dir) {
		switch (dir) {
		case "up":
			this.destinationPos.setSize(this.actualPos.width, this.actualPos.height - 1);
			break;
		case "down":
			this.destinationPos.setSize(this.actualPos.width, this.actualPos.height + 1);
			break;
		case "right":
			this.destinationPos.setSize(this.actualPos.width + 1, this.actualPos.height);
			break;
		case "left":
			this.destinationPos.setSize(this.actualPos.width - 1, this.actualPos.height);
			break;
		}
	}

	@Override
	protected void resetDirection(boolean tmp) {
		if (!tmp) {
			this.right = false;
			this.left = false;
			this.up = false;
			this.down = false;
		} else {
			this.tmpRight = false;
			this.tmpLeft = false;
			this.tmpUp = false;
			this.tmpDown = false;
		}

	}

	@Override
	public void keyPressed(KeyEvent key) {
		int code = key.getKeyCode();
		if (!tmpUp && !tmpDown && !tmpRight && !tmpLeft)
			switch (code) {
			case KeyEvent.VK_UP:
				if (up || down) {
					resetDirection(false);
					up = true;
				} else {
					tmpUp = true;
					wait = true;
				}
				dir = "up";
				break;
			case KeyEvent.VK_DOWN:
				if (up || down) {
					resetDirection(false);
					down = true;
				} else {
					tmpDown = true;
					wait = true;
				}
				dir = "down";
				break;
			case KeyEvent.VK_LEFT:
				if (left || right) {
					resetDirection(false);
					left = true;
				} else {
					tmpLeft = true;
					wait = true;
				}
				dir = "left";
				break;
			case KeyEvent.VK_RIGHT:
				if (left || right) {
					resetDirection(false);
					right = true;
				} else {
					tmpRight = true;
					wait = true;
				}
				dir = "right";
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
