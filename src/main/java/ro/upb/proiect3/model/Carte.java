package ro.upb.proiect3.model;

public class Carte {
    private int carteID;
    private String denumire;
    private int anAparitie;
    private String editura;

    public Carte() {
    }

    public Carte(int carteID, String denumire, int anAparitie, String editura) {
        this.carteID = carteID;
        this.denumire = denumire;
        this.anAparitie = anAparitie;
        this.editura = editura;
    }

    public int getCarteID() {
        return carteID;
    }

    public void setCarteID(int carteID) {
        this.carteID = carteID;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public int getAnAparitie() {
        return anAparitie;
    }

    public void setAnAparitie(int anAparitie) {
        this.anAparitie = anAparitie;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "carteID=" + carteID +
                ", denumire='" + denumire + '\'' +
                ", anAparitie=" + anAparitie +
                ", editura='" + editura + '\'' +
                '}';
    }
}
