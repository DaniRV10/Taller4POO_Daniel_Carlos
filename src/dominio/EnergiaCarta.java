package dominio;

public class EnergiaCarta extends Carta {
	
	private String elemento;

	public EnergiaCarta(String nombre, int rareza, String elemento) {
		super(nombre, rareza);
		this.elemento = elemento;
	}

	public String getElemento() {
		return elemento;
	}
	
	@Override
	public double accept(CartaVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public String FormatoString() {
		
		return nombre + ";" + rareza + ";Energy;" + elemento;
	}
	

}
