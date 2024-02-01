package builderPaketi;

public class PaketBuildDirector {

  private PaketBuilder builder;

  public PaketBuildDirector(PaketBuilder builder) {
    this.builder = builder;
  }

  public Paket buildPaketX(String oznaka, String vrijemePrijema, String posiljatelj,
      String primatelj, String vrsta, float visina, float sirina, float duzina, float tezina,
      String uslugaDostave, float pouzece) {
    return builder.setOznaka(oznaka).setVrijemePrijema(vrijemePrijema).setPosiljatelj(posiljatelj)
        .setPrimatelj(primatelj).setVrstaPaketa(vrsta).setVisina(visina).setSirina(sirina)
        .setDuzina(duzina).setTezina(tezina).setUslugaDostave(uslugaDostave).setPouzece(pouzece)
        .setCijena().build();
  }

  public Paket buildPaketTipski(String oznaka, String vrijemePrijema, String posiljatelj,
      String primatelj, String vrsta, float tezina, String uslugaDostave, float pouzece) {
    return builder.setOznaka(oznaka).setVrijemePrijema(vrijemePrijema).setPosiljatelj(posiljatelj)
        .setPrimatelj(primatelj).setVrstaPaketa(vrsta).setTezina(tezina)
        .setUslugaDostave(uslugaDostave).setPouzece(pouzece).setCijena().build();
  }
}
