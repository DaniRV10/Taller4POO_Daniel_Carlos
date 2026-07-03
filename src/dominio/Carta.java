package dominio;

public abstract class Carta {
	
	protected String nombre;
	protected int rareza;
	
	
	public Carta(String nombre, int rareza) {
		super();
		this.nombre = nombre;
		this.rareza = rareza;
	}
	
	public abstract double accept(CartaVisitor visitor);
	
	
}
