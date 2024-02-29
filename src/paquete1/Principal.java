package paquete1;

import javax.swing.SwingUtilities;

public class Principal {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				AsistenteInversion gui = new AsistenteInversion();
				gui.setLocationRelativeTo(null); 
				gui.setVisible(true);
			}
		});
	}
}
