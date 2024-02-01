package decoratorIP;

import java.util.ArrayList;
import java.util.List;
import builderPaketi.Paket;
import observerStatusPaketa.ObserverPaket;
import podaci.VrstaPaketa;
import pomocnici.Sustav;

public class ZaprimljeniPaket implements IZaprimljeniPaket {
  private Paket paket;
  private String statusIsporuke;
  private String vrijemePreuzimanja;
  private String prijevoz;

  private List<ObserverPaket> naciniObavjesti = new ArrayList<>();

  public ZaprimljeniPaket(Paket paket, String status, String vrijeme) {
    this.paket = paket;
    this.statusIsporuke = status;
    this.vrijemePreuzimanja = vrijeme;
  }

  public void setPaket(Paket paket) {
    this.paket = paket;
  }

  @Override
  public Paket getPaket() {
    return paket;
  }

  public void setVrijemePreuzimanja(String vrijemePreuzimanja) {
    this.vrijemePreuzimanja = vrijemePreuzimanja;
  }

  @Override
  public String getVrijemePreuzimanja() {
    return vrijemePreuzimanja;
  }

  public void setStatusIsporuke(String statusIsporuke) {
    this.statusIsporuke = statusIsporuke;
    obavjetiSvePretplatnike();
  }

  @Override
  public String getStatusIsporuke() {
    return statusIsporuke;
  }

  public float izracunajProstor() {
    if (paket.getVrstaPaketa().equals("X")) {
      return this.paket.getSirina() * this.paket.getVisina() * this.paket.getDuzina();
    } else {
      for (VrstaPaketa v : Sustav.vrstePaketa) {
        if (v.oznaka().equals(this.paket.getVrstaPaketa())) {
          return v.sirina() * v.duzina() * v.visina();
        }
      }
    }
    return 0;
  }

  @Override
  public String getPrijevoz() {
    return prijevoz;
  }

  public void setPrijevoz(String prijevoz) {
    this.prijevoz = prijevoz;
  }

  public void dodajNacinObavjesti(ObserverPaket nacinObavjesti) {
    naciniObavjesti.add(nacinObavjesti);
  }

  public void ukloniNacinObavjesti(ObserverPaket nacinObavjesti) {
    naciniObavjesti.remove(nacinObavjesti);
  }

  private void obavjetiSvePretplatnike() {
    for (ObserverPaket observer : naciniObavjesti) {
      observer.obavjesti();
    }
  }

  public void dodajNovogPretplatnika(String imePrezime) {
    for (ObserverPaket observer : naciniObavjesti) {
      observer.dodajPretplatnika(imePrezime);
    }
  }

  public void obrisiPretplatnika(String imePrezime) {
    for (ObserverPaket observer : naciniObavjesti) {
      observer.obrisiPretplatnika(imePrezime);
    }
  }

  public int brojObservera() {
    return naciniObavjesti.size();
  }
}
