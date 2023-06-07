package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import modelo.Casilla;
import modelo.Juego;

import java.util.Timer;
import java.util.TimerTask;

public class juegoController extends buscaminasController implements Initializable {

	@FXML
	private Label contadorMinas;

	@FXML
	private Label contadorTiempo;

	@FXML
	private ImageView emoji;

	@FXML
	private MenuBar menuBar;

	@FXML
	private AnchorPane panel;

	@FXML
	private AnchorPane panelCasillas;
	@FXML
	private AnchorPane panelJuego;

	@FXML
	private ImageView reset;

	@FXML
	private ImageView salir;

	@FXML
	private ImageView menu;

	@FXML
	private Label label_jugador;

	public Casilla[][] matrizCasillas;

	private int nMinas;
	private int filas;
	private int columnas;

	// difCasilla es la diferencia entre una casilla i altra.
	private int difCasilla;

	private int layoutx;
	private int layouty;

	private int tamanyoCasilla;

	Juego juego;

	private int tiempo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// ASI FER UN SWITCH CASE QUE SEGONS LA DIFICULTAT
		// FASA UN NUMERO DE BOMBES, TAMANY DEL PANEL, ETC.

		this.reset.setLayoutX(413);
		this.reset.setLayoutY(114);
		this.salir.setLayoutX(532);
		this.salir.setLayoutY(106);
		this.menu.setLayoutX(295);
		this.menu.setLayoutY(115);

		switch (this.getDificultad()) {
		case "facil":

			this.nMinas = 10;
			this.filas = 8;
			this.columnas = 8;
			this.difCasilla = 30;
			this.layoutx = 293;
			this.layouty = 141;
			this.emoji.setLayoutX(128);

			iniciarPartida();
			break;

		case "medio":

			this.nMinas = 40;
			this.filas = 16;
			this.columnas = 16;
			this.difCasilla = 20;
			this.layoutx = 254;
			this.layouty = 152;
			this.emoji.setLayoutX(170);
			this.contadorMinas.setLayoutX(290);

			iniciarPartida();

			break;

		case "dificil":

			this.nMinas = 99;
			this.filas = 16;
			this.columnas = 30;
			this.difCasilla = 20;
			this.layoutx = 114;
			this.layouty = 109;
			this.contadorMinas.setLayoutX(530);
			this.contadorTiempo.setLayoutX(50);
			this.emoji.setLayoutX(308);

			iniciarPartida();

			// nia que cambiar el tamany de la scena

			break;

		case "imposible":

			this.nMinas = 479;
			this.filas = 16;
			this.columnas = 30;
			this.difCasilla = 20;
			this.layoutx = 114;
			this.layouty = 109;
			this.contadorMinas.setLayoutX(530);
			this.contadorTiempo.setLayoutX(50);
			this.emoji.setLayoutX(308);

			iniciarPartida();

			break;

		default:
			break;
		}

		this.reset.setOnMouseClicked((event) -> reiniciar());
		this.menu.setOnMouseClicked((event) -> volverMenu());
		this.salir.setOnMouseClicked((event) -> Platform.exit());
	}

	private void volverMenu() {

		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/vista/buscaminas.fxml"));

			this.panel.getChildren().setAll(root);

		} catch (IOException e) {

			System.err.println("Error al leer xml");
		}

	}

	private void reiniciar() {

		try {

			AnchorPane panelDificultad = FXMLLoader.load(getClass().getResource("/vista/juego.fxml"));

			this.panel.getChildren().setAll(panelDificultad);

		} catch (IOException e) {

			System.err.println("Error al leer xml");

		}
	}

	private void iniciarPartida() {

		this.contadorMinas.setText(Integer.toString(nMinas));

		matrizCasillas = new Casilla[filas][columnas];

		int y = -this.difCasilla;

		this.tamanyoCasilla = this.difCasilla;

		for (int i = 0; i < filas; i++) {

			int x = 1;
			y += this.difCasilla;

			for (int j = 0; j < columnas; j++) {

				Casilla c = new Casilla(false);

				c.setPrefSize(tamanyoCasilla, tamanyoCasilla);
				c.setMinSize(tamanyoCasilla, tamanyoCasilla);
				c.setMaxSize(tamanyoCasilla, tamanyoCasilla);
				c.setLayoutX(x);
				c.setLayoutY(y);

				int n1 = i;
				int n2 = j;

				c.setOnMouseClicked((event -> {
					if (event.getButton() == MouseButton.PRIMARY)
						click(n1, n2);
					else if (event.getButton() == MouseButton.SECONDARY)
						clickDerecho(n1, n2);
				}));

				this.panelCasillas.getChildren().add(c);

				c.setVisible(true);

				matrizCasillas[i][j] = c;

				x += this.difCasilla;

			}
		}

		this.panelJuego.setLayoutX(this.layoutx);
		this.panelJuego.setLayoutY(this.layouty);

		String nombre;

		// guardar nombre de jugador
		if (this.getNombre().isEmpty()) {
			nombre = "ANONIMO";
		} else {
			nombre = this.getNombre().toUpperCase();
		}

		this.label_jugador.setText(this.label_jugador.getText() + "\n" + nombre);

		// AHORA CREAR EL JUEGO

		this.juego = new Juego(matrizCasillas, nMinas, nombre);

		this.juego.ponerMinas();
		this.juego.rellenarCasillasConNum();

		this.tiempo = 0;

		Timer timer = new Timer();

		// iniciar temporizador

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {

				Platform.runLater(new Runnable() {

					@Override
					public void run() {

						tiempo++;

						contadorTiempo.setText(String.format("%03d", tiempo));

						if (juego.isGameOver() || juego.isVictoria()) {
							timer.cancel();
						}
					}
				});

			}
			// 1000 milisegundos i sumamos 1 a tiempo para ir sumando segundo a segundo
		}, 0, 1000);

	}

	public int getnMines() {
		return nMinas;
	}

	private void clickDerecho(int i, int j) {

		Casilla c = this.matrizCasillas[i][j];

		if (!c.isMostradoNumero()) {
			if (c.isBandera()) {

				this.juego.quitarBandera(c);
				// asi nia que sumar uno al contador de mines que falten
				this.nMinas++;
			} else {

				this.juego.ponerBandera(c);
				// asi nia que restar uno al contador de mines que falten.
				this.nMinas--;
			}
		}

		this.matrizCasillas[i][j] = c;

		this.contadorMinas.setText(Integer.toString(nMinas));

		darVictoria();
		comprobar();

	}

	private void darVictoria() {

		boolean b = true;

		for (int i = 0; i < matrizCasillas.length; i++) {

			for (int j = 0; j < matrizCasillas[i].length; j++) {

				if (matrizCasillas[i][j].isTieneMina() && !matrizCasillas[i][j].isBandera()) {

					b = false;
				}

			}
		}

		if (this.nMinas == 0) {
			this.juego.setVictoria(b);
		}
	}

	private void click(int i, int j) {

		Casilla c = this.matrizCasillas[i][j];

		if (c.isTieneMina()) {

			this.juego.mostrarMina(c);

			Image img = new Image("/vista/emojiTriste.png");

			this.emoji.setImage(img);

			// asi deu acabar la partida perque has apretat una mina.

			this.juego.setGameOver(true);

		} else {

			if (c.getNumMinesAlrededor().equals("0")) {
				juego.expandirRecursividad(i, j);
			}

			// asi te que apareixer el num de mines que te alrededor.
			this.juego.mostrarNumero(c);

			Image img = new Image("/vista/emojiGafas.png");

			this.emoji.setImage(img);
		}

		comprobar();
	}

	private void comprobar() {

		int time = this.tiempo;

		if (this.juego.isGameOver()) {

			finalizarPartida(true);
			// finalizar partida true es porque has perdido
			// sera false si has ganado

			Alert alertVictoria = new Alert(Alert.AlertType.INFORMATION);
			alertVictoria.setHeaderText("Game Over");
			alertVictoria.setTitle("Derrota");
			alertVictoria.setContentText("Lo siento, has perdido!\n" + "Tiempo total: " + time);
			alertVictoria.showAndWait();

			mostrarMinas();

		} else if (this.juego.isVictoria()) {

			finalizarPartida(false);

			mostrarNumeros();

			Alert alertVictoria = new Alert(Alert.AlertType.INFORMATION);
			alertVictoria.setHeaderText("Enhorabuena");
			alertVictoria.setTitle("Victoria");
			alertVictoria.setContentText("Enhorabuena, has ganado!\n" + "Tiempo total: " + time);
			alertVictoria.showAndWait();

			actualizarRecord();
		}

	}

	private void guardarEnHistorial() {
		// pasem el nom del jugador, el temps de partida, si ha guanyat o no, i la
		// dificultat.

		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter("src/historial.txt", true));
			PrintWriter escribir = new PrintWriter(bw);

			escribir.write("------------------------------\n");

			escribir.write(this.juego.getNombre());

			escribir.write("\n");
			escribir.write("\nVictoria: " + (this.juego.isVictoria() ? "SI" : "NO"));

			escribir.write("\nTiempo: " + this.tiempo);

			escribir.write("\nNumero Minas restantes: " + Integer.parseInt(this.contadorMinas.getText()));

			escribir.write("\nDificultad: " + this.getDificultad());

			escribir.write("\n");

			escribir.close();

		} catch (Exception e) {
			System.err.println("error al escribir historial.");
		}

	}

	private void mostrarNumeros() {
		// mostrar tots els numeros del tablero restants quan has guanyat.

		for (int i = 0; i < matrizCasillas.length; i++) {
			for (int j = 0; j < matrizCasillas[i].length; j++) {

				Casilla c = matrizCasillas[i][j];

				if (!c.isBandera()) {

					this.juego.mostrarNumero(c);
				}
			}
		}

	}

	private void mostrarMinas() {
		// se utilitza quan perds la partida per vore on estaven les mines

		for (int i = 0; i < matrizCasillas.length; i++) {
			for (int j = 0; j < matrizCasillas[i].length; j++) {

				Casilla c = matrizCasillas[i][j];

				if (c.isTieneMina()) {
					this.juego.mostrarMina(c);
				}

			}
		}

	}

	private void finalizarPartida(boolean b) {

		for (int i = 0; i < this.filas; i++) {
			for (int j = 0; j < this.columnas; j++) {

				this.matrizCasillas[i][j].setOnMouseClicked(null);
				this.matrizCasillas[i][j].setOnMouseClicked(null);
			}
		}

		guardarEnHistorial();



	}

	private void actualizarRecord() {
		// TODO Auto-generated method stub

		HashMap<String, String> nombreActual = new HashMap<>();
		HashMap<String, Integer> tiempoActual = new HashMap<>();

		// guardar los datos actuales del fichero.

		File ficheroRecord = new File("src/ficheroRecord.txt");
		Scanner sc;
		try {
			sc = new Scanner(ficheroRecord);

			int contador = 0;

			while (sc.hasNextLine()) {

				if (contador % 3 == 0) {

					String dif = sc.nextLine();

					nombreActual.put(dif, sc.nextLine());
					tiempoActual.put(dif, Integer.parseInt(sc.nextLine()));
				}

			}

			sc.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// leer tiempo de esta partida, su dificultad, i el nombre del jugador.
		// en la funcion se comprueba si es un nuevo record, i si lo es se sobreescribe
		// el fichero.

		cambiarRecord(this.getDificultad(), this.getNombre(), this.tiempo, nombreActual, tiempoActual);

	}

	private void cambiarRecord(String dif, String nombreJugador, int tiempo2, HashMap<String, String> nombreActual,
			HashMap<String, Integer> tiempoActual) {

		if (tiempoActual.get(dif) > tiempo2) {

			BufferedWriter bw;
			try {

				bw = new BufferedWriter(new FileWriter("src/ficheroRecord.txt", false));

				PrintWriter escribir = new PrintWriter(bw);

				String[] dificultades = { "facil", "medio", "dificil", "imposible" };

				for (int i = 0; i < dificultades.length; i++) {
					if (dificultades[i].equals(dif)) {
						System.out.println(dif + "\n" + nombreJugador + "\n" + tiempo2 + "\n");
						escribir.write(dif + "\n" + nombreJugador + "\n" + tiempo2 + "\n");
					} else {
						System.out.println(dificultades[i] + "\n" + nombreActual.get(dificultades[i]) + "\n"
								+ tiempoActual.get(dificultades[i]) + "\n");
						escribir.write(dificultades[i] + "\n" + nombreActual.get(dificultades[i]) + "\n"
								+ tiempoActual.get(dificultades[i]) + "\n");
					}
				}
				
				escribir.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void setImagenEmoji(String estado) {

		Image img = new Image("/vista/emoji" + estado + ".png");

		this.emoji.setImage(img);
	}

}
