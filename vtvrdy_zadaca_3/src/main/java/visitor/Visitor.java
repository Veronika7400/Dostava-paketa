package visitor;

import vrijeme.VirtualniSat;

public interface Visitor {
  static VirtualniSat sat = VirtualniSat.getInstance();

  public void visit(Vozilo vozilo);

  public void visit(Voznja voznja);

  public void visit(SegmentVoznje segment);

  void ispisiZaglavlje();

  void ispisiPodnozje();

  void ispisiSadrzaj();

}
