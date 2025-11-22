package dummyUASLabPBOB_3;

import java.util.Scanner;

public class Transaksi {
    private int idTransaksi;
    private Pesanan pesanan;
    private Pembayaran metodePembayaran;
    private boolean statusKonfirmasi; 

    // Konstruktor Transaksi
    public Transaksi(int idTransaksi, Pesanan pesanan, Pembayaran metodePembayaran) {
        this.idTransaksi = idTransaksi;
        this.pesanan = pesanan;
        this.metodePembayaran = metodePembayaran;
        this.statusKonfirmasi = false; // Status konfirmasi awal adalah false
    }

    // Memulai proses pembayaran dan mengkonfirmasi status transaksi
    public void konfirmasi(Scanner sc) {
    System.out.println("Memulai proses pembayaran...");
    // Memanggil metode pembayaran spesifik
    boolean sukses = metodePembayaran.idPembayaran(pesanan.hitungTotal(), sc); 

    // Mengatur status Transaksi dan Pesanan berdasarkan hasil pembayaran
    if (sukses) {
        this.statusKonfirmasi = true;
        this.pesanan.setStatus("Lunas"); // Ubah status Pesanan menjadi Lunas
    } else {
        this.statusKonfirmasi = false;
    }
}
    // Getters untuk Struk dan informasi
    // Mengembalikan status konfirmasi transaksi
    public boolean isStatusKonfirmasi() { return statusKonfirmasi; }
    // Mengembalikan objek Pesanan
    public Pesanan getPesanan() { return pesanan; }
    // Mengembalikan objek metode Pembayaran
    public Pembayaran getMetodePembayaran() { return metodePembayaran; }
    // Mengembalikan ID Transaksi
    public int getIdTransaksi() { 
        return this.idTransaksi; 
    }
}
