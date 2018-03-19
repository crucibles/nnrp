import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class NRPP {
	public GUI gui;

	private FileHandler fileHandler;

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

	public NRPP() {
		gui = new GUI();
		initializeVariables();
	}

	private void initializeVariables() {
		fileHandler = new FileHandler();

		gui.btnLoaded.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fileHandler.chooseFile(gui.frame);
				String fileExt = fileHandler.getRecentLoadedFileExtension();
				if (!fileExt.isEmpty()) {
					String text = "";
					
					try {
						text = fileHandler.getFileContent();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					if (fileExt.equals("prod")) {
						gui.setProductionTable(text);
						gui.setProductionFileName(fileHandler.getFileName());
					} else if (fileExt.equals("ptbl")) {
						gui.setParseTable(text);
						gui.setParseTableFileName(fileHandler.getFileName());
					}
				}
			}
		});
		
		gui.btnParse.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				parseInput();
			}
		});
	}
	
	public void parseInput(){
		if(!fileHandler.isFileLoaded()){
			System.out.println("empty");
		} else {
			
		}
	}
}
