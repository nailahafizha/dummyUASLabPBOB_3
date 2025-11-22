package dummyUASLabPBOB_3;

import java.util.Scanner;

// Implementasi pembayaran menggunakan QRIS
public class QRISPayment implements Pembayaran {

    // Memproses pembayaran QRIS
    @Override
    public boolean idPembayaran(double total, Scanner sc) {
        System.out.println("Silakan scan QRIS untuk pembayaran Rp " + total);
        System.out.println("... (Menunggu Konfirmasi) ... Pembayaran Berhasil.");
        return true; // Asumsi pembayaran QRIS berhasil setelah discan
    }

    // Mengembalikan nama metode pembayaran "QRIS"
    @Override
    public String getNamaMetode() { return "QRIS"; }
}
