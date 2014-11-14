package quickLinks.gui;

/**
 * 
 * 		UNDER FUKNING CONSTRUCTION
 * 
 * */

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.text.JTextComponent.KeyBinding;

import quickLinks.QuickLinks;

public class SimpleConsole extends JFrame{
	
	JTextArea ConsoleDisplay;
	KeyEdit Input = new KeyEdit(new String[]{"ConsoleLinks:passText"}, new byte[]{KeyEvent.VK_ENTER}){
		@Override
		public Object[] GatherData(int Case) {
			Object[] ret = null;
			switch (Case){
			case 0:
				ret = new Object[1];
				ret[0] = Input.getText();
				Input.setText("");
				break;
			}
			return ret;
		}
	};
	
	public SimpleConsole(){
		
	}

	private static class Links extends QuickLinks{

		public Links() {
			super("ConsoleLinks");
		}
		
		
		public void passText(String data){
			
		}
	}

}
