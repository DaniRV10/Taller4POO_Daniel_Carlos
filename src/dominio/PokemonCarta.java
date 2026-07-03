package dominio;

public class PokemonCarta extends Carta {
	
	private int danio;
	private int energias;
	
	
	public PokemonCarta(String nombre, int rareza, int danio, int energias) {
		super(nombre, rareza);
		this.danio = danio;
		this.energias = energias;
	}


	public int getDanio() {
		return danio;
	}


	public int getEnergias() {
		return energias;
	}


	@Override
	public double accept(CartaVisitor visitor) {
		return visitor.visit(this);
	}
	
	

}
