package decoratorIP;

public class IspisPrimljenih extends DecoratorIspis {

  public IspisPrimljenih(IZaprimljeniPaket zaprimljenPaket) {
    super(zaprimljenPaket);
  }

  public IspisPrimljenih() {}

  @Override
  public void ispisiZaglavlje() {
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Oznaka paketa",
        "Vrijeme prijema", "Vrsta paketa", "Vrsta usluge", "Iznos dostave", "Iznos pouzeća");


    System.out.println("+----------------------+----------------------+"
        + "----------------------+----------------------+----------------------+"
        + "----------------------+");
  }

  @Override
  public void ispisiSadrzaj() {
    if (super.getVrijemePreuzimanja() != null) {
      if (!PaketDostavljen(super.getVrijemePreuzimanja())) {
        System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
            super.getPaket().getOznaka(), super.getPaket().getVrijemePrijema(),
            super.getPaket().getVrstaPaketa(), super.getPaket().getUslugaDostave(),
            super.getPaket().getCijena(), super.getPaket().getPouzece());
      }
    } else {
      System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
          super.getPaket().getOznaka(), super.getPaket().getVrijemePrijema(),
          super.getPaket().getVrstaPaketa(), super.getPaket().getUslugaDostave(),
          super.getPaket().getCijena(), super.getPaket().getPouzece());
    }
  }

  @Override
  public void ispisiPodnozje() {
    System.out.println("+----------------------+----------------------+"
        + "----------------------+----------------------+----------------------+"
        + "----------------------+");
  }



}
