package dominio;

public abstract class Carta {
	
	protected String nombre;
	protected int rareza;
	
	
	public Carta(String nombre, int rareza) {
		super();
		this.nombre = nombre;
		this.rareza = rareza;
	}
	
	public String getNombre() {
		return nombre;
	}

	public int getRareza() {
		return rareza;
	}

	public abstract double accept(CartaVisitor visitor);

	public abstract String FormatoString();
	
	
}
