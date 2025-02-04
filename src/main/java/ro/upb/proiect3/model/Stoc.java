package ro.upb.proiect3.model;

public class Stoc {
    private int stocID;
    private int carteID;
    private int bibliotecaID;
    private int nrExemplare;

    public Stoc() {
    }

    public Stoc(int stocID, int carteID, int bibliotecaID, int nrExemplare) {
        this.stocID = stocID;
        this.carteID = carteID;
        this.bibliotecaID = bibliotecaID;
        this.nrExemplare = nrExemplare;
    }

    public int getStocID() {
        return stocID;
    }

    public void setStocID(int stocID) {
        this.stocID = stocID;
    }

    public int getCarteID() {
        return carteID;
    }

    public void setCarteID(int carteID) {
        this.carteID = carteID;
    }

    public int getBibliotecaID() {
        return bibliotecaID;
    }

    public void setBibliotecaID(int bibliotecaID) {
        this.bibliotecaID = bibliotecaID;
    }

    public int getNrExemplare() {
        return nrExemplare;
    }

    public void setNrExemplare(int nrExemplare) {
        this.nrExemplare = nrExemplare;
    }

    @Override
    public String toString() {
        return "Stoc{" +
                "stocID=" + stocID +
                ", carteID=" + carteID +
                ", bibliotecaID=" + bibliotecaID +
                ", nrExemplare=" + nrExemplare +
                '}';
    }
}
