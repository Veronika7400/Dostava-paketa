package chainOfResponibilityKomande;

import uredi.DostavaPaketa;
import visitor.Visitor;
import visitor.VozilaVisitor;
import visitor.Vozilo;

public class HandlerSV extends HandlerKomandi {

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiSV(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiSV(String[] a) {
    if (!a[0].equals("SV")) {
      return false;
    }

    Visitor visitor = new VozilaVisitor();
    visitor.ispisiZaglavlje();
    for (Vozilo v : DostavaPaketa.dohvatiVozila()) {
      ((VozilaVisitor) visitor).resetirajVrijednosti();
      v.accept(visitor);
    }
    visitor.ispisiPodnozje();

    return true;
  }



}
