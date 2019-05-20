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
import misc.Const;
import service.FieldService;
import service.GhostService;
import service.GridService;
import service.LinkService;
import service.ReadTxtService;

public class GamePanel extends JPanel implements Runnable {

    private static GamePanel instance;

    public static synchronized GamePanel getInstance() throws IOException {
        if (instance == null)
            return new GamePanel();
        return instance;
    }

    private Thread t;
    private GridService gs;
    private ReadTxtService read;
    private AbstractPersonnage pacMan;
    private FieldService fs;
    private LinkService ls;

    private GhostService ghs;

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

        t = new Thread(this);
        t.start();
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

    @Override
    public void run() {
        long beginTime;
        int sleepTime = 0;
        int framesSkipped;
        long timeDiff;
        while (true) {
            try {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;
                this.update();
                this.repaint();
                timeDiff = System.currentTimeMillis() - beginTime;
                sleepTime = (int) (Const.FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
                while (sleepTime < 0 && framesSkipped < Const.MAX_FRAM_SKIPS) {
                    this.update();
                    sleepTime += Const.FRAME_PERIOD;
                    framesSkipped++;
                }
            } finally {
            }
        }
    };

    public void setPacMan(AbstractPersonnage pacMan) {
        this.pacMan = pacMan;
    }

    public void setThread(Thread t) {
        this.t = t;
    }

    private void update() {
        for (AbstractPersonnage personnage : personnages) {
            personnage.move();
        }
        for (Ghost ghost : Ghost.getGhosts()) {
            ghost.move();
        }
    }

}
