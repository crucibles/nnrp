import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.table.TableModel;

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
				System.out.println(parseInput());
				String text = parseInput();
				gui.setOutputTable(text);
			}
		});
	}
	
	public String parseInput(){
		if(!fileHandler.isFileLoaded()){
			System.out.println("empty");
			return "";
		} else {
			System.out.println("Parsing...======================");
			// @index is the index of the current word being matched from the input
			// @rules is the index of the nonTerminal to be used, from the parse table. Identifying what row it is in Production table.
			int index = 0;
			int rules = 0;

			// @result is the string to be returned by the parser to fill in the Output table
			// @input is the input to be parsed
			// @inputWords is the tokenized input of words for parsing
			// @inputBuffer is the running input for the output table
			// @currentWord is the currentWord to be matched in the production
			// @action is the action description, what is being done during parsing
			// @production is the current NonTerminal or terminal to be matched to the currentWord or to be expanded
			// @currentStack is the stack per line for the outputTable
			// @produced is the produced words of the nonterminals
			// @pdt is the production table
			// @prt is the parse table
			String result = "";
			String input = gui.getInputText() + " $";
			String inputWords[] = input.trim().split("\\s");
			String currentWord = inputWords[0];
			String inputBuffer = input;
			String action = "";
			String production = "";
			String currentStack = "";
			String produced = "";
			TableModel pdt = gui.tblProduction.getModel();
			TableModel prt = gui.tblParse.getModel();
			production = pdt.getValueAt(index, 1).toString();
			currentStack = production + " $";

			result += currentStack + ",";
			result += inputBuffer + ",";
			result += action + "\n";

			while (!action.equals("Match $")) {
				System.out.println("RUNNING RESULT:\n"+result+"\n:=====================================:");
				if (production.equals(currentWord)) {
					if (production.equals("" + "$")) {
						action = "Match $";
						result += ",";
						result += ",";
						result += action + "\n";
						System.out.println("Ending of input");
						return result;
					}

					System.out.println("stack:" + currentStack + ":");
					System.out.println("prod:" + production + ":");
					System.out.println("curWord:" + currentWord + ":");
					System.out.println("I matched something, current word is: " + currentWord);

					action = "Match " + currentWord;
					index++;
					inputBuffer = inputBuffer.substring(production.length(), inputBuffer.length());
					inputBuffer = inputBuffer.trim();
					currentStack = currentStack.substring(production.length(), currentStack.length());
					currentStack = currentStack.trim();
					currentWord = inputWords[index];
					production = currentStack.split("\\s")[0];
					System.out.println("New word is: " + currentWord);
					result += currentStack + ",";
					result += inputBuffer + ",";
					result += action + "\n";
				} else {
					System.out.println("stack:" + currentStack + ":");
					System.out.println("prod:" + production + ":");

					System.out.println("curWord:" + currentWord + ":");
					String prodNum = prt.getValueAt(getRowIndex(prt, production), getColumnIndex(prt, currentWord))
							.toString();
					System.out.println("prodNum: " + prodNum);
					if (prodNum != "") {
						rules = Integer.parseInt(prodNum);
						produced = pdt.getValueAt(rules - 1, 2).toString();
						if (rules != 0) {
							action = "Output " + production + " > " + produced;
						}

						System.out.println("action: " + action);

						if (produced.equals("" + "e")) {

							currentStack = currentStack.replaceFirst(production, "");
							currentStack = currentStack.trim();
							production = currentStack.split("\\s")[0];

						} else {

							currentStack = currentStack.replaceFirst(production, produced);
							currentStack = currentStack.trim();
							production = currentStack.split("\\s")[0];

						}

						result += currentStack + ",";
						result += inputBuffer + ",";
						result += action + "\n";
					} else {
						action = "Error on " + currentStack + " trying to parse " + currentWord;
						result += currentStack + ",";
						result += inputBuffer + ",";
						result += action + "\n";
						System.out.println("ERROR on " + currentStack + " trying to parse " + currentWord);
						return result;
					}

					System.out.println("runningStack:" + currentStack + ":");
					System.out.println("=================================next loop");
				}

			}
			return result;
		}
	}

	private int getRowIndex(TableModel table, String rowName) {
		System.out.println(":" + rowName + ":");

		for (int i = 0; i < table.getRowCount(); i++) {
			String currentRowName = table.getValueAt(i, 0).toString();
			System.out.println("r:" + currentRowName + ":");
			if (currentRowName.equals(rowName)) {
				System.out.println("returning now.." + i);
				return i;
			}
		}

		return -1;
	}

	private int getColumnIndex(TableModel table, String columnName) {
		System.out.println(":" + columnName + ":");

		for (int i = 0; i < table.getColumnCount(); i++) {
			String currentColumnName = table.getColumnName(i);
			System.out.println("r:" + currentColumnName + ":");
			if (currentColumnName.equals(columnName)) {
				System.out.println("returning now.." + i);
				return i;
			}
		}

		return -1;
	}
}
