//2019 Java ReMux
//Terminal Emulator for Platforms V1
//written by rwx777

package remux;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class remux_main extends JFrame {

	public static JTextField cli = new JTextField();
	public static JTextArea console = new JTextArea();

	public static Scanner reader = new Scanner(System.in);

	public static void Write() throws IOException {

		String Text_to_Write;
		String Path; // Path to File to be stored

		System.out.println("Please enter the Text you want to write into the File: ");
		Text_to_Write = reader.nextLine();
		console.append("Please enter the Text you want to write into the File: ");

		System.out.println("Please enter the File Path where to want to store the Text: ");
		Path = reader.nextLine();
		console.append("Please enter the File Path where to want to store the Text: ");

		if (!Text_to_Write.isEmpty() && !Path.isEmpty()) {
			try (FileWriter fileWriter = new FileWriter(Path, true)) { // Set true for append mode

				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println(Text_to_Write); // new line
				printWriter.close();

				System.out.println("Wrote to " + Path);
				console.append("Wrote to " + Path);

			} catch (FileNotFoundException e) {
				e.printStackTrace(); // Print out Errors if something goes wrong
			}

		} else {
			System.out.println("Usage: <Text to File> <FileLocation>");
		}
		reader.close();

	}

	public static void Delete() {

		Scanner reader = new Scanner(System.in);

		System.out.println("Please enter File Path: ");

		String FilePath = reader.nextLine();

		if (FilePath.contains(".")) {
			try {
				final long startTime = System.currentTimeMillis();

				Files.deleteIfExists(Paths.get(FilePath));

				final long endTime = System.currentTimeMillis();
				long MillisTook = (endTime - startTime);
				long timetook = ((endTime - startTime) / 1000);

				System.out.println("Deletion successful.");
				System.out.println("Total time: " + timetook + "s/" + MillisTook + "ms"); // prints out time it took

			} catch (NoSuchFileException e) {
				System.out.println("No such file or Directory.");
				System.out.println("Deletion not successful.");
			} catch (DirectoryNotEmptyException e) {
				System.out.println("Directory is not empty.");
				System.out.println("Deletion not successful.");
			} catch (IOException e) {
				System.out.println("Invalid permissions.");
				System.out.println("Deletion not successful.");
			}

		} else {

			Path rootPath = Paths.get(FilePath);
			try {
				final long startTime = System.currentTimeMillis();

				Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder()).map(Path::toFile) // .peek(System.out::println)
						.forEach(File::delete);

				final long endTime = System.currentTimeMillis();
				long MillisTook = (endTime - startTime);
				double SecondsTook = ((endTime - startTime) / 1000);

				System.out.println("Deleted Folder " + rootPath + " succesfully");
				System.out.println("Total time: " + SecondsTook + "s/" + MillisTook + "ms"); // prints out time it took

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		reader.close();
	}

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.setTitle("ReMux");
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(null);
		frame.add(cli);
		frame.add(console);

		// Text Area
		console.setSize(600, 470);
		console.setLocation(2, 2);
		console.setBackground(Color.black);
		console.setForeground(Color.white); // sets text color

		// Text Field
		cli.setSize(600, 30);
		cli.setLocation(2, 500);
		cli.setBackground(Color.black);
		cli.setForeground(Color.white);

		cli.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String gtext = cli.getText().toLowerCase();

				if (gtext.contains("write")) {
					try {
						Write();
						cli.setText(null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (gtext.contains("rm")) {
					Delete();
					cli.setText(null);
				}

			}
		});

	}

}
