package dummyUASLabPBOB_3;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status;
    private List<DetailPesanan> daftarItem;
    private Meja meja;
    private Customer customer;
    private String metodePembayaran = "Cash"; // Default

    // Constructor
    public Pesanan(int idPesanan, Meja meja, Customer customer) {
        this.idPesanan = idPesanan;
        this.meja = meja;
        this.customer = customer;
        this.status = "Dipesan";
        this.daftarItem = new ArrayList<>();
    }

    public void tambahItem(DetailPesanan item) {
        this.daftarItem.add(item);
    }

    public int hitungSubtotal() {
        int subtotal = 0;
        for (DetailPesanan item : daftarItem) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    public int getPajak() {
        return (int) Math.round(hitungSubtotal() * 0.10);
    }

    public int getService() {
        return (int) Math.round(hitungSubtotal() * 0.05);
    }

    public int hitungTotal() {
        return hitungSubtotal() + getPajak() + getService();
    }

    public void tampilkanDetail() {
        System.out.println("---------------------------------");
        System.out.println("ID Pesanan: " + idPesanan + " | Meja: " + meja.getNomor() + " | Status: " + status);
        for(DetailPesanan item : daftarItem) {
            System.out.printf("  - %s (x%d) \t Rp %d\n", item.getItem().getNama(), item.getJumlah(), item.getSubtotal());
        }
        System.out.println("---------------------------------");
        System.out.println("  Subtotal: Rp " + hitungSubtotal());
        System.out.println("  Pajak (10%): Rp " + getPajak());
        System.out.println("  Service (5%): Rp " + getService());
        System.out.println("  TOTAL BAYAR: Rp " + hitungTotal());
        System.out.println("---------------------------------");
    }

    public void setMetodePembayaran(String metode) {
        this.metodePembayaran = metode;
    }
    public String getMetodePembayaran() {
        return this.metodePembayaran;
    }
    public int getIdPesanan() { 
        return idPesanan; 
    }
    public String getStatus() {
         return status; 
    }
    public List<DetailPesanan> getDaftarItem() { 
        return daftarItem; 
    }
    public Meja getMeja() { 
        return meja; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

    public Customer getCustomer() { 
        return this.customer; 
    }
}