package decoratorIP;

public class IspisPodatakaPaketa extends DecoratorIspis {

  public IspisPodatakaPaketa(IZaprimljeniPaket zaprimljenPaket) {
    super(zaprimljenPaket);
  }

  public IspisPodatakaPaketa() {}

  @Override
  public void ispisiZaglavlje() {
    System.out.format(
        "| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
        "Oznaka paketa", "Posiljatelj", "Primatelj", "Vrsta paketa", "Visina", "Sirina", "Duzina",
        "Tezina", "Cijena", "Pouzece");

    System.out.println("+-----------------+-----------------+-----------------+-----------------+"
        + "-----------------+-----------------+-----------------+"
        + "-----------------+-----------------+-----------------+");
  }

  @Override
  public void ispisiSadrzaj() {
    System.out.format(
        "| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
        super.getPaket().getOznaka(), super.getPaket().getPosiljatelj(),
        super.getPaket().getPrimatelj(), super.getPaket().getVrstaPaketa(),
        super.getPaket().getVisina(), super.getPaket().getSirina(), super.getPaket().getDuzina(),
        super.getPaket().getTezina(), super.getPaket().getCijena(), super.getPaket().getPouzece());

  }

  @Override
  public void ispisiPodnozje() {
    System.out.println("+-----------------+-----------------+-----------------+-----------------+"
        + "-----------------+-----------------+-----------------+"
        + "-----------------+-----------------+-----------------+");
  }
}
