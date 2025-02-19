package GUI;

import java.awt.Color;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import element.PacMan;
import exception.FieldException;
import exception.GridException;
import exception.LinkException;
import misc.Const;

public class Fenetre extends JFrame {

	public GamePanel gp;
	public PacMan pacMan;

	public Fenetre(String name) throws IOException, FieldException, LinkException, GridException {
		super(name);

		gp = GamePanel.getInstance();

		this.setSize(Const.SIZE_F);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(gp);
		this.setBackground(Color.DARK_GRAY);
		this.addKeyListener((KeyListener) gp.getPacMan());
		this.addKeyListener(gp);
		this.setVisible(true);
		this.pack();
	}
}
