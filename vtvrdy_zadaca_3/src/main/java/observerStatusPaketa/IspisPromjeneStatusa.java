package observerStatusPaketa;

import java.util.ArrayList;
import decoratorIP.ZaprimljeniPaket;

public class IspisPromjeneStatusa extends ObserverPaket {

  public IspisPromjeneStatusa(ZaprimljeniPaket zaprimljeniPaket) {
    this.zaprimljeniPaket = zaprimljeniPaket;
    this.pretplatnici = new ArrayList<>();
    this.pretplatnici.add(zaprimljeniPaket.getPaket().getPosiljatelj());
    this.pretplatnici.add(zaprimljeniPaket.getPaket().getPrimatelj());
  }

  @Override
  public void obavjesti() {
    for (String p : pretplatnici) {
      System.out.println(p + ":Promjena statusa paketa " + zaprimljeniPaket.getPaket().getOznaka()
          + " u " + zaprimljeniPaket.getStatusIsporuke());
    }
  }

  @Override
  public void dodajPretplatnika(String imePrezime) {
    if (!pretplatnici.contains(imePrezime)) {
      pretplatnici.add(imePrezime);
      System.out.println(imePrezime + " će primati obavjesti.");
    } else {
      System.out.println(imePrezime + " već prima obavjesti za ovaj paket.");
    }

  }

  @Override
  public void obrisiPretplatnika(String imePrezime) {
    if (pretplatnici.contains(imePrezime)) {
      pretplatnici.remove(imePrezime);
      System.out.println(imePrezime + " neće primati obavjesti.");
    } else {
      System.out.println(imePrezime + " nije niti primao obavjesti za ovaj paket.");
    }
  }

}
