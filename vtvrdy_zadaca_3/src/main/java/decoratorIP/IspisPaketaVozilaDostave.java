package decoratorIP;

public class IspisPaketaVozilaDostave extends DecoratorIspis {

  public IspisPaketaVozilaDostave(IZaprimljeniPaket zaprimljenPaket) {
    super(zaprimljenPaket);
  }

  public IspisPaketaVozilaDostave() {}

  @Override
  public void ispisiZaglavlje() {
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Oznaka paketa",
        "Vozilo", "Vrijeme prijema", "Vrsta paketa", "Vrsta usluge", "Vrijeme preuzimanja");


    System.out.println("+----------------------+----------------------+----------------------+"
        + "----------------------+----------------------+----------------------+");
  }

  @Override
  public void ispisiSadrzaj() {
    if (super.getVrijemePreuzimanja() != null) {
      if (PaketDostavljen(super.getVrijemePreuzimanja())) {
        System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
            super.getPaket().getOznaka(), super.getPrijevoz(), super.getPaket().getVrijemePrijema(),
            super.getPaket().getVrstaPaketa(), super.getPaket().getUslugaDostave(),
            super.getVrijemePreuzimanja());
      }
    }
  }

  @Override
  public void ispisiPodnozje() {
    System.out.println("+----------------------+----------------------+----------------------+"
        + "----------------------+----------------------+----------------------+");
  }

}
