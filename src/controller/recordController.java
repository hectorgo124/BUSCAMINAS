package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class recordController implements Initializable{

    @FXML
    private Label label_difcil;

    @FXML
    private Label label_facil;

    @FXML
    private Label label_imposible;

    @FXML
    private Label label_medio;

    File ficheroRecord;
    
    HashMap <String, String> nombre;
    HashMap <String, Integer> tiempo;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		this.ficheroRecord = new File("src/ficheroRecord.txt");			
		
		this.nombre = new HashMap<String, String>();
		this.tiempo = new HashMap<String, Integer>();
		
		Scanner sc;
		try {
			sc = new Scanner(ficheroRecord);
			
			int contador = 0;
			
			while (sc.hasNextLine()) {
				
				if (contador % 3 == 0) {
					
					String dif = sc.nextLine();
					
					nombre.put(dif, sc.nextLine());
					tiempo.put(dif, Integer.parseInt(sc.nextLine()));
				}
				
			}
			
			sc.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.label_facil.setText(this.label_facil.getText() + "\n--------------\n" + record("facil"));
		this.label_medio.setText(this.label_medio.getText() + "\n--------------\n" + record("medio"));
		this.label_difcil.setText(this.label_difcil.getText() + "\n--------------\n" + record("dificil"));
		this.label_imposible.setText(this.label_imposible.getText() + "\n--------------\n" + record("imposible"));
		
	}

	private String record(String dif) {

		String record = nombre.get(dif) + "\nTiempo: " + tiempo.get(dif);
		
		return record;
	}
	
}
