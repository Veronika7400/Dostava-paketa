package visitor;

public class SegmentiVisitor implements Visitor {

  SegmentVoznje s;

  @Override
  public void visit(Vozilo vozilo) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Voznja voznja) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(SegmentVoznje segment) {
    this.s = segment;
    ispisiSadrzaj();
  }

  @Override
  public void ispisiPodnozje() {
    System.out.println(
        "+----------------------+----------------------+----------------------+----------------------+"
            + "----------------------+");
  }

  @Override
  public void ispisiZaglavlje() {
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Početak", "Povratak",
        "Trajanje", "Ukupno km", "Paket");


    System.out.println(
        "+----------------------+----------------------+----------------------+----------------------+"
            + "----------------------+");
  }

  @Override
  public void ispisiSadrzaj() {
    System.out.format("| %-20s | %-20s | %-20s | %-20.6f | %-20s |\n", s.getVrijemePocetka(),
        s.getVrijemeKraja(), sat.pretvoriUStringSate(s.getUkupnoTrajanje()), s.getUdaljenost(),
        s.getIdPaketa());

  }

}
