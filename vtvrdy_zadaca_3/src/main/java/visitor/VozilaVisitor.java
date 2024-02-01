package visitor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import decoratorIP.ZaprimljeniPaket;
import uredi.PrijemPaketa;

public class VozilaVisitor implements Visitor {

  Double ukupniKm = (double) 0;
  Integer brojHitnih = 0;
  Integer brojObicnih = 0;
  Integer brojIsporucenih = 0;
  Float zauzeceProstora = (float) 0;
  Float zauzeceTezine = (float) 0;
  Integer brojVoznji = 0;
  Vozilo v;

  @Override
  public void visit(Vozilo vozilo) {
    brojVoznji = vozilo.dohvatiSveVoznje().size();
    v = vozilo;
    ispisiSadrzaj();
  }

  @Override
  public void visit(Voznja voznja) {}

  @Override
  public void visit(SegmentVoznje segment) {
    ukupniKm += segment.getUdaljenost();
    if (segment.getIdPaketa() != null) {
      ZaprimljeniPaket paket = PrijemPaketa.dohvatiPaket(segment.getIdPaketa());
      if (paket != null) {
        if (paketDostavljen(paket)) {
          brojIsporucenih++;
        } else if (paket.getPaket().getUslugaDostave().equals("H")) {
          brojHitnih++;
          zauzeceProstora += paket.izracunajProstor();
          zauzeceTezine += paket.getPaket().getTezina();
        } else if (paket.getPaket().getUslugaDostave().equals("S")) {
          brojObicnih++;
          zauzeceProstora += paket.izracunajProstor();
          zauzeceTezine += paket.getPaket().getTezina();
        }
      }
    }
  }

  private boolean paketDostavljen(ZaprimljeniPaket paket) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime trenutnoVrijeme = LocalDateTime.parse(sat.getTrenutnoVrijeme(), formatter);
    LocalDateTime vrijemePreuzimanja =
        LocalDateTime.parse(paket.getVrijemePreuzimanja(), formatter);
    if (vrijemePreuzimanja != null) {
      if (vrijemePreuzimanja.isBefore(trenutnoVrijeme)) {
        return true;
      }
    }
    return false;
  }

  public void resetirajVrijednosti() {
    ukupniKm = (double) 0;
    brojHitnih = 0;
    brojObicnih = 0;
    brojIsporucenih = 0;
    zauzeceProstora = (float) 0;
    zauzeceTezine = (float) 0;
    brojVoznji = 0;
    v = null;
  }

  @Override
  public void ispisiSadrzaj() {
    System.out.format(
        "| %-20s | %-15s | %-15.6f | %-15s | %-15s | %-15s | %-20.2f | %-20.2f | %-15s | %-15s |\n",
        v.getRegistracija(), v.getStatus().toString(), ukupniKm, brojHitnih, brojObicnih,
        brojIsporucenih, postotakProstora(v), postotakTezine(v), brojVoznji, v.dohvatiPouzece());
  }

  private Float postotakTezine(Vozilo v) {
    return zauzeceTezine / v.getKapacitetTezine() * 100;
  }

  private Float postotakProstora(Vozilo v) {
    return zauzeceProstora / v.getKapacitetProstora() * 100;
  }

  @Override
  public void ispisiPodnozje() {
    System.out
        .format("+----------------------+-----------------+-----------------+-----------------+"
            + "-----------------+-----------------+----------------------+----------------------+"
            + "-----------------+" + "-----------------+\n");
  }

  @Override
  public void ispisiZaglavlje() {
    System.out.format(
        "| %-20s | %-15s | %-15s | %-15s | %-15s | %-15s | %-20s | %-20s | %-15s | %-15s |\n",
        "Oznaka vozila", "Status", "Ukupno km", "Hitni paketi", "Obični paketi", "Isporučeni ",
        "Zauzeće prostora(%)", "Zauzeće težine(%)", "Broj vožnji", "Pouzeće");


    System.out
        .format("+----------------------+-----------------+-----------------+-----------------+"
            + "-----------------+-----------------+----------------------+----------------------+"
            + "-----------------+" + "-----------------+\n");
  }
}
