package dominio;
import factory.*;



public class CalcularPoderVisitor implements CartaVisitor {
    @Override
    public double visit(PokemonCarta p) { return ((double) p.getDanio() / p.getEnergias())* 100; }
    @Override
    public double visit(ItemCarta i) { return i.getBonif() * 20; }
    @Override
    public double visit(SuportCarta s) { return s.getEfectos() * 50; }
    @Override
    public double visit(EnergiaCarta e) { return 1.0; }
}