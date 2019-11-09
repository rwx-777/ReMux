package remux;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class remux_main extends JFrame{
	
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

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.setTitle("ReMux");
		frame.setSize(600,600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(null);
		frame.add(cli);
		frame.add(console);
		
		//Text Area
		console.setSize(600, 470);
		console.setLocation(2,2);
		console.setBackground(Color.black);
		console.setForeground(Color.white); //sets text color
		
		//Text Field
		cli.setSize(600,30);
		cli.setLocation(2,500);
		cli.setBackground(Color.black);
		cli.setForeground(Color.white);
		
		cli.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String gtext = cli.getText().toLowerCase();
				
				if(gtext.contains("write")) {
					try {
						Write();
						cli.setText(null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		
	}

}
