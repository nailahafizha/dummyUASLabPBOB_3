package dummyUASLabPBOB_3;

import java.util.Scanner;

public interface Pembayaran {
    // Diagram bilang 'idPembayaran', tapi itu nama yg aneh untuk method
    // Kita ganti jadi 'lakukanPembayaran'
    boolean idPembayaran(double total, Scanner sc);
    String getNamaMetode();
}
