package visitor;

public class SegmentVoznje extends VisitorKlijenti {

  String odGPS;
  String doGPS;
  Double udaljenost;
  String vrijemePocetka;
  String vrijemeKraja;
  Integer trajanjeVoznje;
  Integer trajanjeIsporuke;
  Integer ukupnoTrajanje;
  String idPaketa;

  public String getOdGPS() {
    return odGPS;
  }

  public void setOdGPS(String odGPS) {
    this.odGPS = odGPS;
  }

  public String getDoGPS() {
    return doGPS;
  }

  public void setDoGPS(String doGPS) {
    this.doGPS = doGPS;
  }

  public Double getUdaljenost() {
    return udaljenost;
  }

  public void setUdaljenost(Double udaljenost) {
    this.udaljenost = udaljenost;
  }

  public String getVrijemePocetka() {
    return vrijemePocetka;
  }

  public void setVrijemePocetka(String vrijemePocetka) {
    this.vrijemePocetka = vrijemePocetka;
  }

  public String getVrijemeKraja() {
    return vrijemeKraja;
  }

  public void setVrijemeKraja(String vrijemeKraja) {
    this.vrijemeKraja = vrijemeKraja;
  }

  public Integer getTrajanjeVoznje() {
    return trajanjeVoznje;
  }

  public void setTrajanjeVoznje(Integer trajanjeVoznje) {
    this.trajanjeVoznje = trajanjeVoznje;
  }

  public Integer getTrajanjeIsporuke() {
    return trajanjeIsporuke;
  }

  public void setTrajanjeIsporuke(Integer trajanjeIsporuke) {
    this.trajanjeIsporuke = trajanjeIsporuke;
  }

  public Integer getUkupnoTrajanje() {
    return ukupnoTrajanje;
  }

  public void setUkupnoTrajanje(Integer ukupnoTrajanje) {
    this.ukupnoTrajanje = ukupnoTrajanje;
  }

  public String getIdPaketa() {
    return idPaketa;
  }

  public void setIdPaketa(String idPaketa) {
    this.idPaketa = idPaketa;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }
}
