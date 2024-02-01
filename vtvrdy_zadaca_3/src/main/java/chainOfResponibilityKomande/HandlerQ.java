package chainOfResponibilityKomande;

public class HandlerQ extends HandlerKomandi {

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiQ(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiQ(String[] a) {
    if (!a[0].equals("Q")) {
      return false;
    }
    System.exit(0);
    return true;
  }



}
