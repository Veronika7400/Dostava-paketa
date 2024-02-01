package adapter;

import java.util.Properties;

public class Provjera1Adapter implements ProvjeraParametara {
  Provjera1 provjera1;

  public Provjera1Adapter() {
    this.provjera1 = new Provjera1();
  }

  @Override
  public boolean provjeriParametre(Properties parametri) {
    String parametriString = pretvoriUString(parametri);
    if (provjera1.provjeriParametre1(parametriString)) {
      return true;
    }
    return false;
  }

  private String pretvoriUString(Properties parametri) {
    return parametri.toString().replaceAll("[{}]", "");
  }


}
