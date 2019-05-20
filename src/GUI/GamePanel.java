package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import element.AbstractPersonnage;
import element.Ghost;
import element.PacMan;
import element.Wall;
import main.MainThread;
import misc.Const;
import service.FieldService;
import service.GhostService;
import service.GridService;
import service.LinkService;
import service.ReadTxtService;

public class GamePanel extends JPanel {

	private static GamePanel instance;

	public static synchronized GamePanel getInstance() throws IOException {
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

	private GhostService ghs;
	private long fps;

	private List<AbstractPersonnage> personnages = new ArrayList<>();

	private GamePanel() throws IOException {
		super();
		gs = GridService.getInstance();
		fs = FieldService.getInstance();
		ls = LinkService.getInstance();
		read = ReadTxtService.getInstance(new File("terrain.txt"));
		Map<Integer, List<String>> data = read.readFile();
		ghs = GhostService.getInstance();

		fs.createField(data);
		ls.craeteLink();

		Dimension spawnPM = new Dimension(9, 11);
		Dimension spawnG = new Dimension(9, 9);

		pacMan = new PacMan(gs.getPixelFromPosition(spawnPM).width, gs.getPixelFromPosition(spawnPM).height,
				(Const.SIZE_F.width / Const.NBR_COL), (Const.SIZE_F.height / Const.NBR_ROW), 6);

		Ghost ghost1 = new Ghost(gs.getPixelFromPosition(spawnG).width, gs.getPixelFromPosition(spawnG).height,
				(Const.SIZE_F.width / Const.NBR_COL), (Const.SIZE_F.height / Const.NBR_ROW));

		personnages.add(pacMan);
		personnages.add(ghost1);

		// ls.printNodeAndLink();

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
		// for (AbstractNode node : AbstractNode.getNodes())
		// node.paint(g);
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
		System.out.println(fps);
		for (AbstractPersonnage personnage : personnages) {
			personnage.move();
		}
	}

}
