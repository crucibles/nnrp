import java.awt.EventQueue;

public class NRPP {
	public GUI gui;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NRPP window = new NRPP();
					window.gui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public NRPP(){
		gui = new GUI();
		String text = "1,E,T E'\n2,E',+ T E'\n3,E',e\n4,T,F T'\n5,T',* F T'\n6,T',e\n7,F,(E)\n8,F,id";
		gui.setParseTable(text);
		text = ",id,+,*,(,),$\nE,1,,,1,,\n"
				+ "E',,2,,,3,3\n"
				+ "T,4,,,4,,\n"
				+ "T',,6,5,,6,6\n"
				+ "F,8,,,7,,";
		gui.setProductionTable(text);
	}
}
