package builderPaketi;

public interface PaketBuilder {

  Paket build();

  PaketBuilder setOznaka(String oznaka);

  PaketBuilder setVrijemePrijema(String vrijemePrijema);

  PaketBuilder setPosiljatelj(String posiljatelj);

  PaketBuilder setPrimatelj(String primatelj);

  PaketBuilder setVrstaPaketa(String vrstaPaketa);

  PaketBuilder setVisina(float visina);

  PaketBuilder setSirina(float sirina);

  PaketBuilder setDuzina(float duzina);

  PaketBuilder setTezina(float tezina);

  PaketBuilder setUslugaDostave(String uslugaDostave);

  PaketBuilder setCijena();

  PaketBuilder setPouzece(float pouzece);

}
