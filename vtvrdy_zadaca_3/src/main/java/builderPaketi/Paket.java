package builderPaketi;

public class Paket {
  private String oznaka;
  private String vrijemePrijema;
  private String posiljatelj;
  private String primatelj;
  private String vrstaPaketa;
  private float visina;
  private float sirina;
  private float duzina;
  private float tezina;
  private String uslugaDostave;
  private float cijena;
  private float pouzece;

  public Paket() {}

  public void setOznaka(String oznaka) {
    this.oznaka = oznaka;
  }

  public void setVrijemePrijema(String vrijemePrijema) {
    this.vrijemePrijema = vrijemePrijema;
  }

  public void setPosiljatelj(String posiljatelj) {
    this.posiljatelj = posiljatelj;
  }

  public void setPrimatelj(String primatelj) {
    this.primatelj = primatelj;
  }

  public void setVrstaPaketa(String vrstaPaketa) {
    this.vrstaPaketa = vrstaPaketa;
  }

  public void setVisina(float visina) {
    this.visina = visina;
  }

  public void setSirina(float sirina) {
    this.sirina = sirina;
  }

  public void setDuzina(float duzina) {
    this.duzina = duzina;
  }

  public void setTezina(float tezina) {
    this.tezina = tezina;
  }

  public void setUslugaDostave(String uslugaDostave) {
    this.uslugaDostave = uslugaDostave;
  }

  public void setCijena(float cijena) {
    this.cijena = cijena;
  }

  public void setPouzece(float pouzece) {
    this.pouzece = pouzece;
  }

  public String getPosiljatelj() {
    return posiljatelj;
  }

  public String getPrimatelj() {
    return primatelj;
  }

  public String getVrstaPaketa() {
    return vrstaPaketa;
  }

  public float getVisina() {
    return visina;
  }

  public float getSirina() {
    return sirina;
  }

  public float getDuzina() {
    return duzina;
  }

  public float getTezina() {
    return tezina;
  }

  public String getUslugaDostave() {
    return uslugaDostave;
  }

  public float getCijena() {
    return cijena;
  }

  public float getPouzece() {
    return pouzece;
  }

  public String getOznaka() {
    return oznaka;
  }

  public String getVrijemePrijema() {
    return vrijemePrijema;
  }
}
