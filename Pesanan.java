package dummyUASLabPBOB_3;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status;
    private List<DetailPesanan> daftarItem;
    private Meja meja;
    private Customer customer; 

    // Konstruktor Pesanan
    public Pesanan(int idPesanan, Meja meja, Customer customer) { 
        this.idPesanan = idPesanan;
        this.meja = meja;
        this.customer = customer; 
        this.status = "Dipesan"; // Status awal pesanan
        this.daftarItem = new ArrayList<>(); // Inisialisasi daftar item kosong
    }

    // Menambahkan DetailPesanan ke daftar item
    public void tambahItem(DetailPesanan item) {
        this.daftarItem.add(item);
    }

    // Menghitung total harga seluruh item dalam pesanan
    public int hitungTotal() {
        int total = 0;
        for (DetailPesanan item : daftarItem) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Menampilkan detail pesanan
    public void tampilkanDetail() {
        System.out.println("---------------------------------");
        System.out.println("ID Pesanan: " + idPesanan + " | Meja: " + meja.getNomor() + " | Status: " + status);
        for(DetailPesanan item : daftarItem) {
            System.out.printf("  - %s (x%d) \t Rp %d\n", item.getItem().getNama(), item.getJumlah(), item.getSubtotal());
        }
        System.out.println("  TOTAL: Rp " + hitungTotal());
        System.out.println("---------------------------------");
    }

    // Getters dan Setters
    public int getIdPesanan() { return idPesanan; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDaftarItem() { return daftarItem; }
    public Meja getMeja() { return meja; }

    // Mengubah status pesanan
    public void setStatus(String status) { this.status = status; }

    // Mengembalikan objek Customer yang membuat pesanan
    public Customer getCustomer() {
        return this.customer;
    }
}
