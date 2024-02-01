package decoratorIP;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import builderPaketi.Paket;
import vrijeme.VirtualniSat;

public abstract class DecoratorIspis implements IZaprimljeniPaket {

  static VirtualniSat sat = VirtualniSat.getInstance();
  private IZaprimljeniPaket zaprimljenPaket;

  DecoratorIspis() {
    this.zaprimljenPaket = null;
  }

  DecoratorIspis(IZaprimljeniPaket zaprimljenPaket) {
    this.zaprimljenPaket = zaprimljenPaket;
  }

  public void dodjeliPaket(IZaprimljeniPaket zaprimljenPaket) {
    this.zaprimljenPaket = zaprimljenPaket;
  }

  @Override
  public Paket getPaket() {
    return zaprimljenPaket.getPaket();
  }

  @Override
  public String getPrijevoz() {
    return zaprimljenPaket.getPrijevoz();
  }

  @Override
  public String getVrijemePreuzimanja() {
    return zaprimljenPaket.getVrijemePreuzimanja();
  }

  @Override
  public String getStatusIsporuke() {
    return zaprimljenPaket.getStatusIsporuke();
  }

  public abstract void ispisiZaglavlje();

  public abstract void ispisiSadrzaj();

  public abstract void ispisiPodnozje();

  protected static boolean PaketDostavljen(String vrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime trenutnoVrijeme = LocalDateTime.parse(sat.getTrenutnoVrijeme(), formatter);
    LocalDateTime vrijemePrijema = LocalDateTime.parse(vrijeme, formatter);
    if (vrijemePrijema.isBefore(trenutnoVrijeme)) {
      return true;
    }
    return false;
  }


}
