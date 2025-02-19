package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import element.AbstractNode;
import element.AbstractPersonnage;
import element.Ghost;
import element.PacMan;
import element.Wall;
import exception.FieldException;
import exception.GridException;
import exception.LinkException;
import main.MainThread;
import misc.Const;
import service.FieldService;
import service.GridService;
import service.LinkService;
import service.ReadTxtService;

public class GamePanel extends JPanel implements KeyListener {

	private static GamePanel instance;

	public static synchronized GamePanel getInstance()
			throws IOException, FieldException, LinkException, GridException {
		if (instance == null)
			instance = new GamePanel();
		return instance;
	}

	private MainThread t;
	private GridService gs;
	private ReadTxtService read;
	private AbstractPersonnage pacMan;
	private FieldService fs;
	private LinkService ls;

	private long fps;

	private Dimension spawnPM;
	private Dimension spawnG;

	private boolean displayChemin = false;

	private List<AbstractPersonnage> personnages = new ArrayList<>();

	private GamePanel() throws IOException, FieldException, LinkException, GridException {
		super();
		gs = GridService.getInstance();
		fs = FieldService.getInstance();
		ls = LinkService.getInstance();
		read = ReadTxtService.getInstance(new File("terrain.txt"));
		Map<Integer, List<String>> data = read.readFile();

		fs.createField(data);
		ls.craeteLink();

		spawnPM = new Dimension(9, 11);
		spawnG = new Dimension(9, 9);
		pacMan = new PacMan(gs.getPixelFromPosition(spawnPM).width, gs.getPixelFromPosition(spawnPM).height,
				(Const.SIZE_F.width / Const.NBR_COL), (Const.SIZE_F.height / Const.NBR_ROW), 6);

		personnages.add(pacMan);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if (personnages.size() <= Const.NBR_GHOSTS)
					try {
						personnages.add(new Ghost(gs.getPixelFromPosition(spawnG).width,
								gs.getPixelFromPosition(spawnG).height, (Const.SIZE_F.width / Const.NBR_COL),
								(Const.SIZE_F.height / Const.NBR_ROW), 6));
					} catch (GridException e) {
						e.printStackTrace();
					}
			}
		}, 3 * 1000, 3 * 1000);

		this.setPreferredSize(Const.SIZE_F);
		t = new MainThread(this);
		t.start();
	}

	public long getFps() {
		return fps;
	}

	public AbstractPersonnage getPacMan() {
		return this.pacMan;
	}

	public Thread getThread() {
		return this.t;
	}

	@Override
	public void paintComponent(Graphics g) {
		this.reset(g);

		for (Wall wall : Wall.getWalls())
			wall.paint(g);

//		for (AbstractNode node : AbstractNode.getNodes())
//			node.paint(g);
		if (displayChemin)
			for (Ghost ghost : Ghost.getGhosts()) {
				for (AbstractNode node : ghost.getChemin())
					node.paint(g);
			}

		for (AbstractPersonnage personnage : personnages)
			personnage.paint(g);
	}

	private void reset(Graphics g) {
		g.fillRect(0, 0, Const.SIZE_F.width, Const.SIZE_F.height);

	}

	public void setFps(long l) {
		this.fps = l;
	}

	public void setPacMan(AbstractPersonnage pacMan) {
		this.pacMan = pacMan;
	}

	public void setThread(MainThread t) {
		this.t = t;
	}

	public void update() {
		for (AbstractPersonnage personnage : personnages) {
			personnage.move();
		}
	}

	@Override
	public void keyPressed(KeyEvent key) {
		int code = key.getKeyCode();
		if (code == KeyEvent.VK_C)
			displayChemin = !displayChemin;

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
