package visitor;

import decoratorIP.ZaprimljeniPaket;
import uredi.PrijemPaketa;

public class VoznjeVisitor implements Visitor {

  Voznja v;
  Vozilo vozilo;
  String vrijemePocetka;
  String vrijemePovratka;
  long trajanje;
  float ukupnoKm;
  Integer brojHitnih = 0;
  Integer brojObicnih = 0;
  Integer brojIsporucenih = 0;
  Float zauzeceProstora = (float) 0;
  Float zauzeceTezine = (float) 0;

  @Override
  public void visit(Vozilo vozilo) {
    this.vozilo = vozilo;
  }

  public void resetirajVrijednosti() {
    vrijemePocetka = "";
    vrijemePovratka = "";
    trajanje = 0;
    ukupnoKm = 0;
    brojHitnih = 0;
    brojObicnih = 0;
    brojIsporucenih = 0;
    zauzeceProstora = (float) 0;
    zauzeceTezine = (float) 0;
    v = null;
  }

  public void postaviVozilo(Vozilo vozilo) {
    this.vozilo = vozilo;
  }

  @Override
  public void visit(Voznja voznja) {
    v = voznja;
    vrijemePocetka = voznja.dohavtiPrviSegment().getVrijemePocetka();
    vrijemePovratka = voznja.dohavtiZadnjiSegment().getVrijemeKraja();
    zauzeceProstora = voznja.getZauzeceProstora();
    zauzeceTezine = voznja.getZauzeceTezine();
    ispisiSadrzaj();
    resetirajVrijednosti();
  }

  @Override
  public void visit(SegmentVoznje segment) {
    ukupnoKm += segment.getUdaljenost();
    trajanje += segment.getTrajanjeIsporuke();
    if (segment.getIdPaketa() != null) {
      ZaprimljeniPaket paket = PrijemPaketa.dohvatiPaket(segment.getIdPaketa());
      if (paket != null) {
        if (paketDostavljen(paket)) {
          brojIsporucenih++;
        } else if (paket.getPaket().getUslugaDostave().equals("H")) {
          brojHitnih++;
        } else if (paket.getPaket().getUslugaDostave().equals("S")) {
          brojObicnih++;
        }
      }
    }
  }

  @Override
  public void ispisiPodnozje() {
    System.out.format(
        "+----------------------+----------------------+----------------------+-----------------+"
            + "-----------------+-----------------+----------------------+"
            + "----------------------+----------------------+\n");
  }

  @Override
  public void ispisiZaglavlje() {
    System.out.format("| %-20s | %-20s | %-20s | %-15s | %-15s | %-15s | %-20s | %-20s | %-20s |\n",
        "Početak", "Povratak", "Trajanje", "Ukupno km", "Hitni paketi", "Obični paketi",
        "Isporučeni paketi", "Zauzeće prostora(%)", "Zauzeće težine(%)");
    System.out.format(
        "+----------------------+----------------------+----------------------+-----------------+"
            + "-----------------+-----------------+----------------------+"
            + "----------------------+----------------------+\n");


  }

  @Override
  public void ispisiSadrzaj() {
    System.out.format(
        "| %-20s | %-20s | %-20s | %-15.6f | %-15s | %-15s | %-20s | %-20.2f | %-20.2f |\n",
        vrijemePocetka, vrijemePovratka, sat.pretvoriUStringSate(trajanje), ukupnoKm, brojHitnih,
        brojObicnih, brojIsporucenih, postotakProstora(vozilo), postotakTezine(vozilo));
  }

  private Float postotakTezine(Vozilo v) {
    return zauzeceTezine / v.getKapacitetTezine() * 100;
  }

  private Float postotakProstora(Vozilo v) {
    return zauzeceProstora / v.getKapacitetProstora() * 100;
  }

  private boolean paketDostavljen(ZaprimljeniPaket paket) {
    return paket.getStatusIsporuke().equals("Isporucen");
  }
}
