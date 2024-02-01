package proxy;

import java.util.List;
import podaci.Osobe;
import pomocnici.Sustav;

public class ProxyOsobe implements IOsobe {
  private boolean admin;
  private Sustav sustav = Sustav.getInstance();;

  public ProxyOsobe(String korisnik) {
    if (korisnikPostoji(korisnik)) {
      if (korisnikAdmin(korisnik)) {
        admin = true;
      }
    } else {
      System.out.println("Korisnik ne postoji!");
    }
  }

  private boolean korisnikPostoji(String korisnik) {
    return sustav.dohvatiOsobu(korisnik) != null;

  }

  private boolean korisnikAdmin(String korisnik) {
    Osobe user = sustav.dohvatiOsobu(korisnik);
    return user.admin();
  }

  @Override
  public List<Osobe> dohvatiOsobe() {
    if (admin) {
      return sustav.dohvatiOsobe();
    } else {
      System.out.println("Samo admin može pristupiti ispisu podataka o osobama!");
    }
    return null;
  }

}
