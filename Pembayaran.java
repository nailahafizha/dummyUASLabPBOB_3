package dummyUASLabPBOB_3;

import java.util.Scanner;

// Interface Pembayaran. Kontrak untuk semua metode pembayaran
public interface Pembayaran {

    // Memproses transaksi dan mengembalikan status keberhasilan (true/false)
    boolean idPembayaran(double total, Scanner sc);

    // Mengembalikan nama metode pembayaran
    String getNamaMetode();
}
