package dummyUASLabPBOB_3;

import java.util.Scanner;

// Implementasi pembayaran menggunakan QRIS
public class QRISPayment implements Pembayaran {

    @Override
    public boolean idPembayaran(double total, Scanner sc) {
        System.out.println("Silakan scan QRIS untuk pembayaran Rp " + total);
        System.out.println("... (Menunggu Konfirmasi) ... Pembayaran Berhasil.");
        return true;
    }

    @Override
    public String getNamaMetode() { return "QRIS"; }
}
