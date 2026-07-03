package dominio;

public class ItemCarta extends Carta {
	private int bonif;

	public ItemCarta(String nombre, int rareza, int bonif) {
		super(nombre, rareza);
		this.bonif = bonif;
	}

	public int getBonif() {
		return bonif;
	}
	
	
	@Override
	public double accept(CartaVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public String FormatoString() {
		
		return nombre + ";" + rareza+";Item;"+bonif;
	}

	@Override
	public void actualizarAtributos(String[] nuevosAtributos) {
		this.bonif = Integer.parseInt(nuevosAtributos[0]);
		
	}
	
	
}
