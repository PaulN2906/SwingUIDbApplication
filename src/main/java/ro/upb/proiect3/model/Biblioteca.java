package ro.upb.proiect3.model;

public class Biblioteca {
    private int bibliotecaID;
    private String denumire;
    private String adresa;

    public Biblioteca() {
    }

    public Biblioteca(int bibliotecaID, String denumire, String adresa) {
        this.bibliotecaID = bibliotecaID;
        this.denumire = denumire;
        this.adresa = adresa;
    }

    public int getBibliotecaID() {
        return bibliotecaID;
    }

    public void setBibliotecaID(int bibliotecaID) {
        this.bibliotecaID = bibliotecaID;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Biblioteca{" +
                "bibliotecaID=" + bibliotecaID +
                ", denumire='" + denumire + '\'' +
                ", adresa='" + adresa + '\'' +
                '}';
    }
}
