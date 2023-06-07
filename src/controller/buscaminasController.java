package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class buscaminasController implements Initializable {

	@FXML
	private Button bttn_dificil;
	@FXML
	private Button bttn_facil;
	@FXML
	private Button bttn_imposible;
	@FXML
	private Button bttn_medio;

	@FXML
	private Button bttn_historial;

	@FXML
	private Button bttn_record;

	@FXML
	private MenuBar menubar;

	@FXML
	public AnchorPane panel;

	@FXML
	private TextField txtField_alias;

	private static String nombre;

	private static String dificultad;

	public String getDificultad() {
		return dificultad;
	}

	public String getNombre() {

		return nombre;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.bttn_facil.setOnMouseClicked((event) -> jugarDificultad("facil"));
		this.bttn_medio.setOnMouseClicked((event) -> jugarDificultad("medio"));
		this.bttn_dificil.setOnMouseClicked((event) -> jugarDificultad("dificil"));
		this.bttn_imposible.setOnMouseClicked((event) -> jugarDificultad("imposible"));
		this.bttn_historial.setOnMouseClicked((event) -> mostrarHistorial());
		this.bttn_record.setOnMouseClicked((event) -> mostrarRecord());
	}

	private void mostrarRecord() {

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/record.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/vista/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void mostrarHistorial() {
		
		try {
			ScrollPane root = (ScrollPane) FXMLLoader.load(getClass().getResource("/vista/historial.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/vista/application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void jugarDificultad(String dific) {

		buscaminasController.dificultad = dific;
		buscaminasController.nombre = this.txtField_alias.getText();

		try {

			AnchorPane panelDificultad = FXMLLoader.load(getClass().getResource("/vista/juego.fxml"));

			this.panel.getChildren().setAll(panelDificultad);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error al leer xml");
			;
		}

	}

}
