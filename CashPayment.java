package dummyUASLabPBOB_3;

import java.util.Scanner;

// Implementasi pembayaran menggunakan Cash (Uang Tunai)
public class CashPayment implements Pembayaran {

    // Memproses pembayaran tunai
    @Override
    public boolean idPembayaran(double total, Scanner sc) {
        System.out.printf("Total tagihan: Rp %.0f\n", total);
        System.out.print("Masukkan jumlah uang tunai: Rp ");
        double bayar = sc.nextDouble();

        if (bayar < total) {
            System.out.println("Uang tidak cukup.");
            return false;
        } else {
            System.out.printf("Kembalian: Rp %.0f\n", (bayar - total));
            return true;
        }
    }

    @Override
    public String getNamaMetode() { return "Cash"; }
}
