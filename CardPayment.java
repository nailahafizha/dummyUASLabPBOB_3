package dummyUASLabPBOB_3;

import java.util.Scanner;

// Implementasi pembayaran menggunakan Kartu Kredit/Debit
public class CardPayment implements Pembayaran {

    @Override
    public boolean idPembayaran(double total, Scanner sc) {
        System.out.println("Memproses Kartu Kredit/Debit sebesar Rp " + total);
        return true;
    }

    @Override
    public String getNamaMetode() { 
        return "Card"; 
    }
}
