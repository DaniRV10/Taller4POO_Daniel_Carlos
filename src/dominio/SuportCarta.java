package dominio;

public class SuportCarta extends Carta {
	
	private int efectos;

	public SuportCarta(String nombre, int rareza, int efectos) {
		super(nombre, rareza);
		this.efectos = efectos;
	}

	public int getEfectos() {
		return efectos;
	}
	
	@Override
	public double accept(CartaVisitor visitor) {
		return visitor.visit(this);
	}
	
	
	
	
	

}
