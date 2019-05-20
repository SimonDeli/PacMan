package main;

import java.io.IOException;

import GUI.GamePanel;
import misc.Const;

public class MainThread extends Thread {
	private GamePanel gp;

	public MainThread(GamePanel gp) throws IOException {
		super();
		this.gp = gp;
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
				gp.update();
				gp.repaint();
				timeDiff = System.currentTimeMillis() - beginTime;
				sleepTime = (int) (Const.FRAME_PERIOD - timeDiff);
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}
				while (sleepTime < 0 && framesSkipped < Const.MAX_FRAM_SKIPS) {
					gp.update();
					sleepTime += Const.FRAME_PERIOD;
					framesSkipped++;
				}
			} finally {
			}
		}
	};

	private void storeStats() {

	}
}
