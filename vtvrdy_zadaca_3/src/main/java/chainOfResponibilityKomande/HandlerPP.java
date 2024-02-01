package chainOfResponibilityKomande;

import java.util.List;
import compositePodrucja.GradComposite;
import compositePodrucja.PodrucjaComposite;
import compositePodrucja.UlicaLeaf;
import pomocnici.Sustav;

public class HandlerPP extends HandlerKomandi {
  static Sustav tvrtka = Sustav.getInstance();

  @Override
  public boolean izvrsiKomandu(String[] komanda) {
    if (izvrsiPP(komanda)) {
      return true;
    }
    return posaljiSljedbeniku(komanda);
  }

  private boolean izvrsiPP(String[] a) {
    if (!a[0].equals("PP")) {
      return false;
    }

    List<PodrucjaComposite> podrucja = tvrtka.dohvatiPodrucja();
    for (PodrucjaComposite podrucje : podrucja) {
      System.out.println("Podrucje: " + podrucje.dohvatiId());

      for (GradComposite grad : podrucje.dohvatiDjecu()) {
        System.out.println("    Grad: " + grad.dohvatiId() + " " + grad.dohvatiNaziv());

        for (UlicaLeaf ulica : grad.dohvatiDjecu()) {
          System.out.println("        Ulica: " + ulica.dohvatiId() + " " + ulica.dohvatiNaziv());
        }
      }
    }
    return true;
  }



}
