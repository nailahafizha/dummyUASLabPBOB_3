package dummyUASLabPBOB_3;

public class Meja {
    private int nomor;
    private String status;

    // Konstruktor Meja
    public Meja(int nomor) {
        this.nomor = nomor;
        this.status = "Terisi"; // Otomatis terisi saat dibuat
    }

    // Mengembalikan nomor meja
    public int getNomor() { return nomor; }
    // Mengembalikan status meja
    public String getStatus() { return status; }
    // Mengubah status meja
    public void setStatus(String status) { this.status = status; }
}
