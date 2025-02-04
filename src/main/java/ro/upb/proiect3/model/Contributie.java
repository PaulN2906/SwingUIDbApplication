package ro.upb.proiect3.model;

public class Contributie {
    private int contributieID;
    private int autorID;
    private int carteID;
    private String rolContributie;

    public Contributie() {
    }

    public Contributie(int contributieID, int autorID, int carteID, String rolContributie) {
        this.contributieID = contributieID;
        this.autorID = autorID;
        this.carteID = carteID;
        this.rolContributie = rolContributie;
    }

    public int getContributieID() {
        return contributieID;
    }

    public void setContributieID(int contributieID) {
        this.contributieID = contributieID;
    }

    public int getAutorID() {
        return autorID;
    }

    public void setAutorID(int autorID) {
        this.autorID = autorID;
    }

    public int getCarteID() {
        return carteID;
    }

    public void setCarteID(int carteID) {
        this.carteID = carteID;
    }

    public String getRolContributie() {
        return rolContributie;
    }

    public void setRolContributie(String rolContributie) {
        this.rolContributie = rolContributie;
    }

    @Override
    public String toString() {
        return "Contributie{" +
                "contributieID=" + contributieID +
                ", autorID=" + autorID +
                ", carteID=" + carteID +
                ", rolContributie='" + rolContributie + '\'' +
                '}';
    }
}
