package dummyUASLabPBOB_3;

public class Meja {
    private int nomor;
    private String status;

    public Meja(int nomor) {
        this.nomor = nomor;
        this.status = "Terisi";
    }

    public int getNomor() { return nomor; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
