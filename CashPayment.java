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

        // Memeriksa apakah uang yang dibayarkan kurang dari total tagihan
        if (bayar < total) {
            System.out.println("Uang tidak cukup.");
            return false;
        } else {
            // Menghitung dan menampilkan kembalian
            System.out.printf("Kembalian: Rp %.0f\n", (bayar - total));
            return true;
        }
    }

    // Mengembalikan nama metode pembayaran "Cash"
    @Override
    public String getNamaMetode() { return "Cash"; }
}
