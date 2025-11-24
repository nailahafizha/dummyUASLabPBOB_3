package dummyUASLabPBOB_3;

public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    // constructor minuman
    public Minuman(String nama, int harga, String ukuran, String suhu) {
        super(nama, harga); // Panggil constructor superclass
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    // Implementasi getInfo(), mengembalikan deskripsi lengkap Minuman
    @Override
    public String getInfo() {
        return String.format("%-20s (%s, %s) - Rp %d", getNama(), ukuran, suhu, getHarga());
    }
}