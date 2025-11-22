package dummyUASLabPBOB_3;

public class DetailPesanan {
    private MenuItem item;
    private int jumlah;
    private String catatan;

    // Konstruktor DetailPesanan. Menginisialisasi item, jumlah, dan catatan
    public DetailPesanan(MenuItem item, int jumlah, String catatan) {
        this.item = item;
        this.jumlah = jumlah;
        // Catatan diatur ke "-" jika kosong/null
        this.catatan = (catatan == null || catatan.isEmpty()) ? "-" : catatan;
    }

    public MenuItem getItem() { return item; }
    public int getJumlah() { return jumlah; }
    public String getCatatan() { return catatan; }

    // Menghitung subtotal untuk detail pesanan ini
    public int getSubtotal() {
        return item.getHarga() * jumlah;
    }
}
