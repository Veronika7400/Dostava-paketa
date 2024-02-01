package builderPaketi;

import java.text.DecimalFormat;
import podaci.VrstaPaketa;
import pomocnici.Sustav;

public class PaketXBuilder implements PaketBuilder {

  private Paket paket;

  public PaketXBuilder() {
    paket = new Paket();
  }

  private float izracunCijene() {
    VrstaPaketa vrsta = null;

    for (VrstaPaketa vp : Sustav.vrstePaketa) {
      if (vp.oznaka().equals(paket.getVrstaPaketa())) {
        vrsta = vp;
      }
    }

    float prvo = vrsta.cijenaP() * volumen();
    float drugo = vrsta.cijenaT() * paket.getTezina();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    String zaokruzenString = decimalFormat.format(vrsta.cijena() + prvo + drugo);
    return Float.parseFloat(zaokruzenString);
  }

  private float volumen() {
    return paket.getVisina() * paket.getSirina() * paket.getDuzina();
  }

  @Override
  public Paket build() {
    return paket;
  }

  @Override
  public PaketBuilder setOznaka(String oznaka) {
    paket.setOznaka(oznaka);
    return this;
  }

  @Override
  public PaketBuilder setVrijemePrijema(String vrijemePrijema) {
    paket.setVrijemePrijema(vrijemePrijema);
    return this;
  }

  @Override
  public PaketBuilder setPosiljatelj(String posiljatelj) {
    paket.setPosiljatelj(posiljatelj);
    return this;
  }

  @Override
  public PaketBuilder setPrimatelj(String primatelj) {
    paket.setPrimatelj(primatelj);
    return this;
  }

  @Override
  public PaketBuilder setVrstaPaketa(String vrstaPaketa) {
    paket.setVrstaPaketa(vrstaPaketa);
    return this;
  }

  @Override
  public PaketBuilder setVisina(float visina) {
    paket.setVisina(visina);
    return this;
  }

  @Override
  public PaketBuilder setSirina(float sirina) {
    paket.setSirina(sirina);
    return this;
  }

  @Override
  public PaketBuilder setDuzina(float duzina) {
    paket.setDuzina(duzina);
    return this;
  }

  @Override
  public PaketBuilder setTezina(float tezina) {
    paket.setTezina(tezina);
    return this;
  }

  @Override
  public PaketBuilder setUslugaDostave(String uslugaDostave) {
    paket.setUslugaDostave(uslugaDostave);
    return this;
  }

  @Override
  public PaketBuilder setCijena() {
    float cijena = izracunCijene();
    paket.setCijena(cijena);
    return this;
  }

  @Override
  public PaketBuilder setPouzece(float pouzece) {
    paket.setPouzece(pouzece);
    return this;
  }

}
