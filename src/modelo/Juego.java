package modelo;

public class Juego {

	private Casilla[][] matrizCasilla;
	private int numMinas;
	private boolean gameOver;
	private boolean victoria;
	private String nombre;
	
	// la idea es que li pase una matriu en casilles,
	// a partir de ahi, aleatoriament posem x mines,
	// depenguent de la dificultat
	// Despres de posar les mines, recorreguem la matriu,
	// SI te bomba, no fem res,
	// SI NO te bomba, busquem per el seu alrededor -> en cada casella buscarem
	// SI te bomba, i si la te sumem 1 a un contador de bombes,
	// Al final el contador sera el numero de bombes alrededor i el guardarem en
	// casilla.setnumminasalrededor

	public Juego(Casilla[][] matrizCasilla, int numMinas, String nombre) {
		super();
		this.matrizCasilla = matrizCasilla;
		this.numMinas = numMinas;
		this.gameOver = false;
		this.victoria = false;
		this.nombre = nombre;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void ponerMinas() {

		if (this.numMinas > 100) {

			int x = (int) (Math.random() * this.matrizCasilla.length);
			int y = (int) (Math.random() * this.matrizCasilla[0].length);

			for (int i = 0; i < matrizCasilla.length; i++) {

				for (int j = 0; j < matrizCasilla[i].length; j++) {

					Casilla c = matrizCasilla[i][j];

					if (i == x && j == y) {
						c.setTieneMina(false);
					} else {
						c.setTieneMina(true);
					}

				}

			}
		} else {
			for (int i = 0; i < numMinas; i++) {

				int x = (int) (Math.random() * this.matrizCasilla.length);
				int y = (int) (Math.random() * this.matrizCasilla[0].length);

				// se comproba si la casilla aleatoria te
				// una mina, si la te nia que fer altra

				if (matrizCasilla[x][y].isTieneMina()) {

					i--;
				} else {

					matrizCasilla[x][y].setTieneMina(true);

				}
			}
		}

	}

	public void rellenarCasillasConNum() {

		for (int i = 0; i < matrizCasilla.length; i++) {

			for (int j = 0; j < matrizCasilla[i].length; j++) {

				if (!matrizCasilla[i][j].isTieneMina()) {

					// asi tinc que mirar el alrededor de la casilla, i contar
					// el numero de mines que nia, guardarlo i posarlo en la casilla
					// si no te cap no es posa res.
					alrededorDeCasilla(i, j);
				}
			}

		}
	}

	private void alrededorDeCasilla(int i, int j) {

		int contador = 0;

		for (int x = (i - 1); x <= (i + 1); x++) {

			for (int y = (j - 1); y <= (j + 1); y++) {

				if (x == i && y == j) {

				} else {
					try {

						if (matrizCasilla[x][y].isTieneMina()) {
							contador++;
						}
					} catch (Exception e) {
						// asi es perque sen ix de la matriu
					}
				}
			}
		}

		if (contador > 0) {
			matrizCasilla[i][j].setNumMinesAlrededor(Integer.toString(contador));
		}
	}

	public void ponerBandera(Casilla c) {

		c.setStyle("-fx-background-image: url('/vista/bandera.png'); -fx-background-size: 100% 100%;");

		c.setBandera(true);

	}

	public void quitarBandera(Casilla c) {

		c.setStyle("-fx-background-image: url('/vista/boton.png'); -fx-background-size: 100% 100%;");
		c.setBandera(false);
	}

	public void mostrarMina(Casilla c) {

		c.setStyle("-fx-background-image: url('/vista/mina.png'); -fx-background-size: 100% 100%;");

	}

	public void mostrarNumero(Casilla c) {

		if (Integer.parseInt(c.getNumMinesAlrededor()) > 0) {
			String[] estilos = estilos();

			c.setStyle(estilos[Integer.parseInt(c.getNumMinesAlrededor()) - 1]);

			// pose que la longitud de matrizCasilla > 10 perque quan la dificultad es major
			// que
			// facil, el tamany del boto es mes xicotet i hi ha que afegir al estil que el
			// tamany
			// del text siga mes xicotet.

			if (this.matrizCasilla.length > 10) {

				c.setStyle(c.getStyle() + " -fx-font-size: 9px;");
			}
			c.setText(c.getNumMinesAlrededor());

		} else {

			c.setStyle("-fx-background-image: url('/vista/casillaPulsada.png'); -fx-background-size: 100% 100%;");
			c.setText("");
		}

		c.setMostradoNumero(true);

	}

	private String[] estilos() {

		String[] estilos = new String[8];

		estilos[0] = "-fx-text-fill: #0c0287;";
		estilos[1] = "-fx-text-fill: #24612c;";
		estilos[2] = "-fx-text-fill: #a92029;";
		estilos[3] = "-fx-text-fill: #030656;";
		estilos[4] = "-fx-text-fill: #6a1324;";
		estilos[5] = "-fx-text-fill: #2b7864;";
		estilos[6] = " ";
		estilos[7] = "-fx-text-fill: #4b4b4b;";

		// ara anyadim a tots els estils lo que tenen en comu, per no copiar y pegar 8
		// voltes.

		for (int i = 0; i < estilos.length; i++) {
			estilos[i] = estilos[i]
					+ " -fx-background-image: url('/vista/casillaPulsada.png'); -fx-background-size: 100% 100%; -fx-font-weight: bold;";
		}

		return estilos;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isVictoria() {
		return victoria;
	}

	public void setVictoria(boolean victoria) {
		this.victoria = victoria;
	}

	public void expandirRecursividad(int x, int y) {

		try {
			Casilla c = matrizCasilla[x][y];

			if (c.isBandera() || c.isTieneMina() || !c.getNumMinesAlrededor().equals("0") || c.isMostradoNumero()) {

				if (!c.getNumMinesAlrededor().equals("0")) {
					this.mostrarNumero(c);
					c.setMostradoNumero(true);
				}
			} else {
				for (int i = (x - 1); i <= (x + 1); i++) {

					for (int j = (y - 1); j <= (y + 1); j++) {

						this.mostrarNumero(c);
						c.setMostradoNumero(true);
						expandirRecursividad(i, j);
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
