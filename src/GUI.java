import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class GUI {

	public JFrame frame;
	private JTable tblOutput;
	private JTable tblProduction;
	private JTable tblParse;
	private JTextField txtInput;
	private JTextField txtLoaded;

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel rulesPanel = new JPanel();
		rulesPanel.setPreferredSize(new Dimension(10, 200));
		frame.getContentPane().add(rulesPanel, BorderLayout.NORTH);
		rulesPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel productionPanel = new JPanel();
		productionPanel.setPreferredSize(new Dimension(340, 10));
		rulesPanel.add(productionPanel, BorderLayout.WEST);
		productionPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel prodTablePanel = new JPanel();
		productionPanel.add(prodTablePanel, BorderLayout.CENTER);
		prodTablePanel.setLayout(null);
		
		JScrollPane spProductionTable = new JScrollPane();
		spProductionTable.setBounds(10, 0, 320, 161);
		prodTablePanel.add(spProductionTable);
		
		tblProduction = new JTable();
		tblProduction.setEnabled(false);
		tblProduction.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NT", "P"
			}
		));
		spProductionTable.setViewportView(tblProduction);
		
		JPanel prodTextPanel = new JPanel();
		productionPanel.add(prodTextPanel, BorderLayout.NORTH);
		prodTextPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblProductions = new JLabel("Productions:");
		prodTextPanel.add(lblProductions, BorderLayout.NORTH);
		
		JLabel lblProdFileName = new JLabel("none");
		prodTextPanel.add(lblProdFileName, BorderLayout.SOUTH);
		
		JPanel parseTablePanel = new JPanel();
		parseTablePanel.setPreferredSize(new Dimension(340, 10));
		rulesPanel.add(parseTablePanel, BorderLayout.EAST);
		parseTablePanel.setLayout(new BorderLayout(0, 0));
		
		JPanel parTablePanel = new JPanel();
		parseTablePanel.add(parTablePanel, BorderLayout.CENTER);
		parTablePanel.setLayout(null);
		
		JScrollPane spParseTable = new JScrollPane();
		spParseTable.setBounds(0, 0, 320, 161);
		parTablePanel.add(spParseTable);
		
		tblParse = new JTable();
		spParseTable.setViewportView(tblParse);
		
		JPanel parTextPanel = new JPanel();
		parseTablePanel.add(parTextPanel, BorderLayout.NORTH);
		parTextPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblParseFileName = new JLabel("none");
		parTextPanel.add(lblParseFileName, BorderLayout.SOUTH);
		
		JLabel lblParseTable = new JLabel("Parse Table:");
		parTextPanel.add(lblParseTable, BorderLayout.NORTH);
		
		JPanel inputPanel = new JPanel();
		frame.getContentPane().add(inputPanel, BorderLayout.CENTER);
		inputPanel.setLayout(null);
		
		JLabel lblInput = new JLabel("INPUT");
		lblInput.setBounds(10, 11, 38, 14);
		inputPanel.add(lblInput);
		
		txtInput = new JTextField();
		txtInput.setBounds(48, 8, 200, 20);
		inputPanel.add(txtInput);
		txtInput.setColumns(10);
		
		JButton btnParse = new JButton("Parse");
		btnParse.setBounds(258, 7, 75, 23);
		inputPanel.add(btnParse);
		
		JLabel lblLoaded = new JLabel("LOADED");
		lblLoaded.setBounds(343, 11, 53, 14);
		inputPanel.add(lblLoaded);
		
		txtLoaded = new JTextField();
		txtLoaded.setEditable(false);
		txtLoaded.setColumns(10);
		txtLoaded.setBounds(390, 8, 192, 20);
		inputPanel.add(txtLoaded);
		
		JButton btnLoaded = new JButton("Loaded");
		btnLoaded.setBounds(592, 7, 83, 23);
		inputPanel.add(btnLoaded);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setPreferredSize(new Dimension(10, 200));
		frame.getContentPane().add(outputPanel, BorderLayout.SOUTH);
		outputPanel.setLayout(null);
		
		JScrollPane spOutput = new JScrollPane();
		spOutput.setBounds(10, 11, 664, 178);
		outputPanel.add(spOutput);
		
		tblOutput = new JTable();
		tblOutput.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Stack", "Input Buffer", "Action"
			}
		));
		spOutput.setViewportView(tblOutput);
	}
	
	public void setParseTable(String text){
		DefaultTableModel model = (DefaultTableModel) tblProduction.getModel();
		String word = "";
		Vector<String> row = new Vector<String>();
		for(int i = 0; i < text.length(); i++){
			if(text.charAt(i) == '\n'){
				System.out.println(word);
				row.add(word);
				word = "";
				model.addRow(row);
				System.out.println("row1" + row);
				row = new Vector<String>();
			} else if(text.charAt(i) == ','){
				System.out.println(word);
				row.add(word);
				word = "";
			} else {
				word += text.charAt(i);							
			}
		}
		
		row.add(word);
		word = "";
		System.out.println("row2" + row);				
		model.addRow(row);		
	}
	
	public void setOutputTable(String text){
		DefaultTableModel model = (DefaultTableModel) tblOutput.getModel();
		String word = "";
		Vector<String> row = new Vector<String>();
		for(int i = 0; i < text.length(); i++){
			if(text.charAt(i) == '\n'){
				System.out.println(word);
				row.add(word);
				word = "";
				model.addRow(row);
				System.out.println("row1" + row);
				row = new Vector<String>();
			} else if(text.charAt(i) == ','){
				System.out.println(word);
				row.add(word);
				word = "";
			} else {
				word += text.charAt(i);							
			}
		}
		
		row.add(word);
		word = "";
		System.out.println("row2" + row);				
		model.addRow(row);		
	}
	
	public void setProductionTable(String text){
		boolean isFirst = true;
		DefaultTableModel model = (DefaultTableModel) tblParse.getModel();
		String word = "";
		Vector<String> row = new Vector<String>();
		for(int i = 0; i < text.length(); i++){
			if(isFirst && text.charAt(i) == '\n'){
				model.addColumn(word);
				word = "";
				isFirst = false;
			} else if(isFirst && text.charAt(i) == ','){
				model.addColumn(word);
				word = "";
			} else if(text.charAt(i) == '\n'){
				System.out.println(word);
				row.add(word);
				word = "";
				model.addRow(row);
				System.out.println("row1" + row);
				row = new Vector<String>();
			} else if(text.charAt(i) == ','){
				System.out.println(word);
				row.add(word);
				word = "";
			} else {
				word += text.charAt(i);							
			}
		}
		
		row.add(word);
		word = "";
		System.out.println("row2" + row);				
		model.addRow(row);	
	}
}
