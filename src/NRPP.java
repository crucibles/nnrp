import java.awt.EventQueue;
import java.util.ArrayList;

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
	}

	public void parser(){

		
		String input = "id + id * id $";
		String inputWords[] = input.trim().split("\\s");
		ArrayList<String> stack = new ArrayList<String>();
		
		stack.add("E $");

		


	}
}
