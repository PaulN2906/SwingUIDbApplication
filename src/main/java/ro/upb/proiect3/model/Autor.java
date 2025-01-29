package ro.upb.proiect3.model;

public class Autor {
    private int autorID;
    private String numeAutor;
    private String prenumeAutor;
    private String taraOrigine;

    public Autor() {
    }

    public Autor(int autorID, String numeAutor, String prenumeAutor, String taraOrigine) {
        this.autorID = autorID;
        this.numeAutor = numeAutor;
        this.prenumeAutor = prenumeAutor;
        this.taraOrigine = taraOrigine;
    }

    public int getAutorID() {
        return autorID;
    }

    public void setAutorID(int autorID) {
        this.autorID = autorID;
    }

    public String getNumeAutor() {
        return numeAutor;
    }

    public void setNumeAutor(String numeAutor) {
        this.numeAutor = numeAutor;
    }

    public String getPrenumeAutor() {
        return prenumeAutor;
    }

    public void setPrenumeAutor(String prenumeAutor) {
        this.prenumeAutor = prenumeAutor;
    }

    public String getTaraOrigine() {
        return taraOrigine;
    }

    public void setTaraOrigine(String taraOrigine) {
        this.taraOrigine = taraOrigine;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "autorID=" + autorID +
                ", numeAutor='" + numeAutor + '\'' +
                ", prenumeAutor='" + prenumeAutor + '\'' +
                ", taraOrigine='" + taraOrigine + '\'' +
                '}';
    }
}
