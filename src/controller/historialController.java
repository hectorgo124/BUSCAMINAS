package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class historialController implements Initializable {

    @FXML
    private Label label_todoHistorial;

    @FXML
    private ScrollPane panelScroll;
    
    @FXML
    private Button bttn_reiniciar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		this.bttn_reiniciar.setOnMouseClicked((event) -> reiniciarHistorial());
		
		this.label_todoHistorial.setText("");
		
		File fichero = new File("src/historial.txt");
		
		Scanner sc;
		try {
			sc = new Scanner(fichero);
			while (sc.hasNextLine()) {
				
				this.label_todoHistorial.setText(this.label_todoHistorial.getText() + sc.nextLine() + "\n");
				
			}
			
			sc.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	}

	private void reiniciarHistorial() {
		
		this.label_todoHistorial.setText("");
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter("src/historial.txt", false));
						
			PrintWriter escribir = new PrintWriter (bw);
			
			escribir.write("");

			escribir.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
