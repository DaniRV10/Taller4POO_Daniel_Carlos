package dominio;

public interface CartaVisitor {
	double visit(PokemonCarta carta);
	double visit(ItemCarta carta);
	double visit(SuportCarta carta);
	double visit(EnergiaCarta carta);
}
