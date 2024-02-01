package stateStatusVozila;

import decoratorIP.ZaprimljeniPaket;
import pomocnici.Klijent;
import pomocnici.Sustav;
import visitor.Vozilo;
import visitor.Voznja;
import vrijeme.VirtualniSat;

public class Aktivno implements Status {
  static Sustav sustav = Sustav.getInstance();
  static VirtualniSat sat = VirtualniSat.getInstance();
  String status;

  public Aktivno() {
    this.status = "A";
  }

  @Override
  public void promjeniStatus(Vozilo vozilo, String noviStatus) {
    if (noviStatus.equals("NI")) {
      Status novi = new Neispravno();
      vozilo.setStatus(novi);
      System.out.println(
          "Vozilu " + vozilo.getRegistracija() + " se postavlja status da je neispravno! ");
    } else if (noviStatus.equals("NA")) {
      Status novi = new Neaktivno();
      vozilo.setStatus(novi);
      System.out
          .println("Vozilu " + vozilo.getRegistracija() + " se postavlja status da je neaktivno! ");
    } else if (noviStatus.equals("A")) {
      System.out.println("Vozilo " + vozilo.getRegistracija() + " je već u tom stanju! ");
    } else {
      System.out.println("Status ne postoji! " + noviStatus);
    }

  }

  public String toString() {
    return status;
  }

  @Override
  public void ukrcajPaket(ZaprimljeniPaket paket, Integer podrucjePaket, Vozilo vozilo) {
    Voznja voznja = vozilo.dohvatiZadnjuVoznju();
    if (voznja == null || jeDostavljena(voznja)) {
      Voznja novaVoznja = new Voznja();
      novaVoznja.setIdPodrucja(podrucjePaket);
      novaVoznja.setPaketUVozilu(paket.getPaket().getOznaka());
      paket.setStatusIsporuke("Ukrcan");
      paket.setPrijevoz(vozilo.getRegistracija());
      novaVoznja.setZauzeceProstora(paket.izracunajProstor());
      novaVoznja.setZauzeceTezine(paket.getPaket().getTezina());
      paket.setPrijevoz(vozilo.getRegistracija());
      vozilo.dodajVoznju(novaVoznja);
      System.out.println("Ukrcan paket: " + paket.getPaket().getOznaka() + " u vozilo: "
          + vozilo.getRegistracija() + " u vrijeme: " + sat.getTrenutnoVrijeme());
    } else if (!jeDostavljena(voznja)) {
      voznja.setPaketUVozilu(paket.getPaket().getOznaka());
      paket.setStatusIsporuke("Ukrcan");
      paket.setPrijevoz(vozilo.getRegistracija());
      voznja.setZauzeceProstora(voznja.getZauzeceProstora() + paket.izracunajProstor());
      voznja.setZauzeceTezine(voznja.getZauzeceTezine() + paket.getPaket().getTezina());
      System.out.println("Ukrcan paket: " + paket.getPaket().getOznaka() + " u vozilo: "
          + vozilo.getRegistracija() + " u vrijeme: " + sat.getTrenutnoVrijeme());
    }
  }

  private boolean jeDostavljena(Voznja voznja) {
    if (voznja.dohavtiZadnjiSegment() != null) {
      return voznja.dohavtiZadnjiSegment().getDoGPS()
          .equals(Klijent.parametri.getProperty("gps").trim());
    }
    return false;

  }



}
