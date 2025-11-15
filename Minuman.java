package dummyUASLabPBOB_3;

public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    public Minuman(String nama, int harga, String ukuran, String suhu) {
        super(nama, harga);
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    // Implementasi method abstract
    @Override
    public String getInfo() {
        return String.format("%-20s (%s, %s) - Rp %d", getNama(), ukuran, suhu, getHarga());
    }
}