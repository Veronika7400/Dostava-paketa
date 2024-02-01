package visitor;

import java.util.ArrayList;
import java.util.List;

public class Voznja extends VisitorKlijenti {

  private List<SegmentVoznje> voznje = new ArrayList<>();
  private float zauzeceProstora;
  private float zauzeceTezine;
  private List<String> paketiUVozilu = new ArrayList<>();
  // private float prijedeniKm;
  private Integer idPodrucja;

  public Voznja() {
    voznje = new ArrayList<>();
  }

  public List<SegmentVoznje> getVoznje() {
    return voznje;
  }

  public void setVoznju(SegmentVoznje segment) {
    this.voznje.add(segment);
  }

  public float getZauzeceProstora() {
    return zauzeceProstora;
  }

  public void setZauzeceProstora(float zauzeceProstora) {
    this.zauzeceProstora = zauzeceProstora;
  }

  public float getZauzeceTezine() {
    return zauzeceTezine;
  }

  public void setZauzeceTezine(float zauzeceTezine) {
    this.zauzeceTezine = zauzeceTezine;
  }

  public List<String> getPaketiUVozilu() {
    return paketiUVozilu;
  }

  public void setPaketUVozilu(String paket) {
    paketiUVozilu.add(paket);
  }

  public void removePaketUVozilu(String paket) {
    paketiUVozilu.remove(paket);
  }


  public Integer getIdPodrucja() {
    return idPodrucja;
  }

  public void setIdPodrucja(Integer idPodrucja) {
    this.idPodrucja = idPodrucja;
  }

  public SegmentVoznje dohavtiZadnjiSegment() {
    if (!voznje.isEmpty()) {
      int indeksZadnjeVoznje = voznje.size() - 1;
      return voznje.get(indeksZadnjeVoznje);
    } else {
      return null;
    }

  }

  public SegmentVoznje dohavtiPrviSegment() {
    if (!voznje.isEmpty()) {
      for (SegmentVoznje v : voznje) {
        return v;
      }
    }
    return null;
  }


  @Override
  public void accept(Visitor visitor) {
    for (SegmentVoznje s : voznje) {
      s.accept(visitor);
    }
    visitor.visit(this);
  }

  public boolean paketUVozilu(String oznaka) {
    if (paketiUVozilu.size() != 0) {
      for (String o : paketiUVozilu) {
        if (o.equals(oznaka))
          return true;
      }
    }
    return false;
  }

  public void update(List<String> poredani) {
    paketiUVozilu = new ArrayList<>();
    paketiUVozilu.addAll(poredani);
  }
}
