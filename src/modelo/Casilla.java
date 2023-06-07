package modelo;

import javafx.scene.control.Button;

public class Casilla extends Button {


	private boolean tieneMina;
	
	private String numMinasAlrededor;

	private boolean mostradoNumero;
	


	private boolean bandera;
	
	public Casilla(boolean tieneMina) {
		super();
		this.tieneMina = tieneMina;
		this.numMinasAlrededor = "0";
		this.bandera = false;
		
		this.setStyle("-fx-background-image: url('/vista/boton.png'); -fx-background-size: 100% 100%;");
				
		this.setMinHeight(30);
		this.setMinWidth(30);
		this.setMaxHeight(30);
		this.setMinWidth(30);
	}

	public String getNumMinesAlrededor() {
		return numMinasAlrededor;
	}

	public void setNumMinesAlrededor(String numMinesAlrededor) {
		this.numMinasAlrededor = numMinesAlrededor;
	}

	public boolean isTieneMina() {
		return tieneMina;
	}

	public void setTieneMina(boolean tieneMina) {
		this.tieneMina = tieneMina;
	}

	public boolean isBandera() {
		return bandera;
	}

	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}
	
	public boolean isMostradoNumero() {
		return mostradoNumero;
	}

	public void setMostradoNumero(boolean mostradoNumero) {
		this.mostradoNumero = mostradoNumero;
	}
	
}
