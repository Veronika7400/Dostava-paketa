package chainOfResponibilityKomande;

public abstract class HandlerKomandi {

  private HandlerKomandi sljedbenik;

  public HandlerKomandi postaviSljedbenika(HandlerKomandi sljedbenik) {
    this.sljedbenik = sljedbenik;
    return sljedbenik;
  }

  public abstract boolean izvrsiKomandu(String[] komanda);

  protected boolean posaljiSljedbeniku(String[] komanda) {
    if (sljedbenik == null) {
      return false;
    }
    return sljedbenik.izvrsiKomandu(komanda);
  }

}
