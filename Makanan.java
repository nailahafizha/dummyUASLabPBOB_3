package dummyUASLabPBOB_3;

public class Makanan extends MenuItem {
    private int tingkatPedas;
    private String kategori;

    // Konstruktor. Menginisialisasi nama, harga, tingkatPedas, dan kategori
    public Makanan(String nama, int harga, int tingkatPedas, String kategori) {
        super(nama, harga); // Panggil konstruktor superclass
        this.tingkatPedas = tingkatPedas;
        this.kategori = kategori;
    }

    // Implementasi getInfo(), Mengembalikan deskripsi lengkap Makanan
    @Override
    public String getInfo() {
       
        return String.format("%-20s (Kategori: %s, Pedas: %d) - Rp %d", 
            getNama(), 
            this.kategori, 
            this.tingkatPedas, 
            getHarga());
    }
}