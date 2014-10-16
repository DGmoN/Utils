package quickLinks.gui;

import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import DataTypes.ByteConventions;
import quickLinks.QuickLinks;

public class KeyEdit extends TextField {

	final byte[] Keys;
	final String[] TARGETS;
	Object[] args;

	public KeyEdit(String[] Targets, byte[] keys) {
		TARGETS = Targets;
		this.Keys = keys;
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

				for (int x = 0; x < TARGETS.length; x++) {
					if ((byte) arg0.getKeyChar() == Keys[x]) {
						QuickLinks.invoke(TARGETS[x], GatherData(x));
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public Object[] GatherData(int Case) {
		return null;
	}

}
