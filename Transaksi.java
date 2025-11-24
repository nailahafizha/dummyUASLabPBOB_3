package dummyUASLabPBOB_3;

import java.util.Scanner;

public class Transaksi {
    private int idTransaksi;
    private Pesanan pesanan;
    private Pembayaran metodePembayaran;
    private boolean statusKonfirmasi;

    public Transaksi(int idTransaksi, Pesanan pesanan, Pembayaran metodePembayaran) {
        this.idTransaksi = idTransaksi;
        this.pesanan = pesanan;
        this.metodePembayaran = metodePembayaran;
        this.statusKonfirmasi = false;
    }

    public void konfirmasi(Scanner sc) {
        System.out.println("Memulai proses pembayaran...");
        boolean sukses = metodePembayaran.idPembayaran(pesanan.hitungTotal(), sc);

        if (sukses) {
            this.statusKonfirmasi = true;
            this.pesanan.setStatus("Lunas");
        } else {
            this.statusKonfirmasi = false;
        }
    }

    public boolean isStatusKonfirmasi() { 
        return statusKonfirmasi; 
    }
    public Pesanan getPesanan() { 
        return pesanan; 
    }
    public Pembayaran getMetodePembayaran() { 
        return metodePembayaran; 
    }
    public int getIdTransaksi() { 
        return this.idTransaksi; 
    }
}

