package dummyUASLabPBOB_3;

import java.util.ArrayList;
import java.util.List;

public class Pesanan {
    private int idPesanan;
    private String status;
    private List<DetailPesanan> daftarItem;
    private Meja meja;
    private Customer customer;

    // constructor Pesanan
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

    public int hitungTotal() {
        int total = 0;
        for (DetailPesanan item : daftarItem) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void tampilkanDetail() {
        System.out.println("---------------------------------");
        System.out.println("ID Pesanan: " + idPesanan + " | Meja: " + meja.getNomor() + " | Status: " + status);
        for(DetailPesanan item : daftarItem) {
            System.out.printf("  - %s (x%d) \t Rp %d\n", item.getItem().getNama(), item.getJumlah(), item.getSubtotal());
        }
        System.out.println("  TOTAL: Rp " + hitungTotal());
        System.out.println("---------------------------------");
    }

    public int getIdPesanan() { return idPesanan; }
    public String getStatus() { return status; }
    public List<DetailPesanan> getDaftarItem() { return daftarItem; }
    public Meja getMeja() { return meja; }

    public void setStatus(String status) { this.status = status; }

    public Customer getCustomer() { return this.customer; }
}
