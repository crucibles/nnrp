import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class FileHandler {

	public String prodName = "";
	public BufferedReader reader;
	private CustomFileChooser fileChooser = new CustomFileChooser("prod");
	private File selectedFile;
	private File prodFile = null;
	private File ptblFile = null;

	/**
	 * Constructor
	 */
	public FileHandler() {
		this.fileChooser
				.setCurrentDirectory(new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()));
		this.fileChooser.setAcceptAllFileFilterUsed(false);
	}

	/**
	 * Choose file from the user's home directory. Checks if file exists as a
	 * .prod or .ptbl file.
	 * 
	 * @author Alvaro, Cedric Y.
	 */
	public File chooseFile(JFrame frame) {
		int file = fileChooser.showOpenDialog(frame);
		if (file == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.isFile() && (getFileExtension(getFileName()).equals("prod")
					|| getFileExtension(getFileName()).equals("ptbl"))) {
				if (getFileExtension(getFileName()).equals("prod")) {
					this.prodName = getFileName();
					String empty = "";
					this.prodFile = new File(empty);
					this.prodFile = selectedFile;
				} else if (getFileExtension(getFileName()).equals("ptbl")) {
					String empty = "";
					this.ptblFile = new File(empty);
					this.ptblFile = selectedFile;
				}
				return selectedFile;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Checks if there is both .ptbl and .prod file loaded.
	 * 
	 * @return true if there is both a .ptbl file and a .prod file loaded; false
	 *         if none or one of them is not loaded.
	 *         
	 * @author Tezuka Kunimitsu
	 */
	public boolean isFileLoaded() {
		return prodFile != null && ptblFile != null;
	}

	/**
	 * Gets the extension of the recently loaded file.
	 * @return {string} extension of the recently loaded file
	 * 
	 * @author Sumandang, AJ Ruth H.
	 */
	public String getRecentLoadedFileExtension() {
		String fileExt = getFileExtension(getFileName());
		if (fileExt.equals("prod")) {
			return "prod";
		} else if (fileExt.equals("ptbl")) {
			return "ptbl";
		} else {
			return "";
		}
	}

	/**
	 * Creates the .out file of the resulting output
	 * 
	 * @param output
	 *            the text to be stored in the .out file
	 * 
	 * @author Alvaro, Cedric Y.
	 * @return FileName of the file created
	 */
	public String createFile(String output, JFrame frame) throws IOException {
		Writer writer = null;

		try {
			// AHJ: unimplemented; #01: weird part here. Filechooser can choose
			// in or out for extension in saving file... so unsaon pagkabalo?
			// (Also, this savefile function does not include saving of .in
			// file)
			String fileName = prodFile.getCanonicalPath();
			System.out.println(fileName);

			selectedFile = new File(fileName.replace(".inp", ".out"));

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(selectedFile)));
			writer.write(output);
			// AHJ: unimplemented; (not properly implemented)refer to comment
			// #01
			return selectedFile.getName();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				;
			}
		}
	}

	/**
	 * Choose file from the user's home directory. Checks if file exists
	 * 
	 * @author Alvaro, Cedric Y.
	 */
	public boolean isCurrFile() {
		selectedFile = fileChooser.getSelectedFile();

		if (selectedFile == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Load the file of the given url
	 * 
	 * @author Alvaro, Cedric Y.
	 * @throws IOException
	 */
	public String getFileContent() throws IOException {
		this.selectedFile = fileChooser.getSelectedFile();

		// stores the selected file and obtained a line
		FileReader fileReader = new FileReader(fileChooser.getSelectedFile().getAbsolutePath());
		reader = new BufferedReader(fileReader);
		String line = reader.readLine();
		String fileContent = "";
		while (line != null) {
			fileContent += line;
			fileContent += "\n";
			line = reader.readLine(); // reads next line
		}

		System.out.println(fileContent);
		reader.close();
		return fileContent;
	}

	/**
	 * 
	 * Gets the name of the file selected.
	 * 
	 * @return name of the file received
	 * 
	 * @author Alvaro, Cedric Y.
	 */
	public String getFileName() {
		if (fileChooser.getSelectedFile() != null) {
			return fileChooser.getSelectedFile().getName();
		} else {
			return "";
		}
	}

	/**
	 * Gets the extension of the file selected.
	 * 
	 * @return file's extension (.e.g. in (file.in), out (file.out))
	 * 
	 * @author Alvaro, Cedric Y.
	 */
	public String getFileExtension(String fileName) {
		if (fileName != null && fileName.length() > 0) {
			int index = fileName.lastIndexOf(".");
			return fileName.substring(index + 1, fileName.length());
		} else {
			return "";
		}
	}
}

/**
 * A CustomFileChooser to implement the overwrite of
 * 
 * @author Alvaro, Cedric Y.
 */
class CustomFileChooser extends JFileChooser {
	private static final long serialVersionUID = -4789704212540593370L;
	private String extension;

	public CustomFileChooser(String extension) {
		super();
		this.extension = extension;
		addChoosableFileFilter(new FileNameExtensionFilter(
				String.format("*prod files and *ptbl files, ", extension, "ptbl"), extension, "ptbl"));
	}

	@Override
	public File getSelectedFile() {
		File selectedFile = super.getSelectedFile();

		if (selectedFile != null) {
			String name = selectedFile.getName();
			if (!name.contains("."))
				selectedFile = new File(selectedFile.getParentFile(), name + '.' + extension);
		}

		return selectedFile;
	}

	@Override
	public void approveSelection() {
		if (getDialogType() == SAVE_DIALOG) {
			File selectedFile = getSelectedFile();
			if ((selectedFile != null) && selectedFile.exists()) {
				int response = JOptionPane.showConfirmDialog(this,
						"The file " + selectedFile.getName()
								+ " already exists. Do you want to replace the existing file?",
						"Ovewrite file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response != JOptionPane.YES_OPTION) {
					return;
				}
			}
		}

		super.approveSelection();
	}
}