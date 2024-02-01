package decoratorIP;

public class IspisOriginal extends DecoratorIspis {


  public IspisOriginal(IZaprimljeniPaket zaprimljenPaket) {
    super(zaprimljenPaket);
  }

  public IspisOriginal() {}

  @Override
  public void ispisiZaglavlje() {
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
        "Oznaka paketa", "Vrijeme prijema", "Vrsta paketa", "Vrsta usluge", "Status isporuke",
        "Vrijeme preuzimanja", "Iznos dostave", "Iznos pouzeća");


    System.out.println(
        "+----------------------+----------------------+----------------------+----------------------+"
            + "----------------------+----------------------+----------------------+"
            + "----------------------+");
  }

  @Override
  public void ispisiSadrzaj() {
    if (super.getVrijemePreuzimanja() != null) {
      System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
          super.getPaket().getOznaka(), super.getPaket().getVrijemePrijema(),
          super.getPaket().getVrstaPaketa(), super.getPaket().getUslugaDostave(),
          super.getStatusIsporuke(), super.getVrijemePreuzimanja(), super.getPaket().getCijena(),
          super.getPaket().getPouzece());

    } else {
      System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
          super.getPaket().getOznaka(), super.getPaket().getVrijemePrijema(),
          super.getPaket().getVrstaPaketa(), super.getPaket().getUslugaDostave(),
          super.getStatusIsporuke(), null, super.getPaket().getCijena(),
          super.getPaket().getPouzece());
    }
  }

  @Override
  public void ispisiPodnozje() {
    System.out.println(
        "+----------------------+----------------------+----------------------+----------------------+"
            + "----------------------+----------------------+----------------------+"
            + "----------------------+");
  }


}
