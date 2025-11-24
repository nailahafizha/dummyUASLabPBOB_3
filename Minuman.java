package dummyUASLabPBOB_3;

public class Minuman extends MenuItem {
    private String ukuran;
    private String suhu;

    // Konstruktor. Menginisialisasi nama, harga, ukuran, dan suhu
    public Minuman(String nama, int harga, String ukuran, String suhu) {
        super(nama, harga); // Panggil konstruktor superclass
        this.ukuran = ukuran;
        this.suhu = suhu;
    }

    // Implementasi getInfo(). Mengembalikan deskripsi lengkap Minuman
    @Override
    public String getInfo() {
        return String.format("%-20s (%s, %s) - Rp %d", getNama(), ukuran, suhu, getHarga());
    }
}