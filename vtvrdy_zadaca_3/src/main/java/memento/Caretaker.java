package memento;

import java.util.ArrayList;
import java.util.List;
import chainOfResponibilityKomande.HandlerSPV.Memento;

public class Caretaker {
  private List<Memento> povijestStanja;

  public Caretaker() {
    povijestStanja = new ArrayList<>();
  }

  public void dodajMemento(Memento memento) {
    povijestStanja.add(memento);
  }

  public Memento vratiStanje(String naziv) {
    for (Memento m : povijestStanja) {
      if (m.getNazivVerzije().equals(naziv)) {
        return m;
      }
    }
    return null;
  }

  public boolean postojiNaziv(String naziv) {
    for (Memento m : povijestStanja) {
      if (m.getNazivVerzije().equals(naziv)) {
        return true;
      }
    }
    return false;
  }

}
