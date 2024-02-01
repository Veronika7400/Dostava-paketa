package builderPaketi;

import podaci.VrstaPaketa;
import pomocnici.Sustav;

public class PaketTipskiBuilder implements PaketBuilder {
  private Paket paket;

  public PaketTipskiBuilder() {
    paket = new Paket();
  }

  private float odrediCijenu() {
    VrstaPaketa vrsta = null;

    for (VrstaPaketa vp : Sustav.vrstePaketa) {
      if (vp.oznaka().equals(paket.getVrstaPaketa())) {
        vrsta = vp;
      }
    }
    switch (paket.getUslugaDostave()) {
      case ("H"):
        return vrsta.cijenaH();
      case ("P"):
        return vrsta.cijenaP();
      default:
        return vrsta.cijena();
    }
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
    float cijena = odrediCijenu();
    paket.setCijena(cijena);
    return this;
  }

  @Override
  public PaketBuilder setPouzece(float pouzece) {
    paket.setPouzece(pouzece);
    return this;
  }
}
