package dummyUASLabPBOB_3;

public class Makanan extends MenuItem {
    private int tingkatPedas;
    private String kategori;

    public Makanan(String nama, int harga, int tingkatPedas, String kategori) {
        super(nama, harga);
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    // Implementasi method abstract
    @Override
    public String getInfo() {
        // [PERBAIKI DI SINI] Tambahkan 'kategori' ke dalam string
        return String.format("%-20s (Kategori: %s, Pedas: %d) - Rp %d", 
            getNama(), 
            this.kategori, // <-- Tambahkan ini
            this.tingkatPedas, 
            getHarga());
    }
}