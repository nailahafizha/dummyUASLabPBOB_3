package dummyUASLabPBOB_3;

public class Struk {

    public void Cetak(Transaksi transaksi) {
        if (!transaksi.isStatusKonfirmasi()) {
            System.out.println("STRUK GAGAL DICETAK (Pembayaran belum lunas)");
            return;
        }

        Pesanan p = transaksi.getPesanan();

        System.out.println("\n========================================");
        System.out.println("              STRUK PEMBAYARAN");
        System.out.println("========================================");
        System.out.println("ID Transaksi: " + transaksi.getIdTransaksi());
        System.out.println("ID Pesanan  : " + p.getIdPesanan());
        System.out.println("Meja No.    : " + p.getMeja().getNomor());
        System.out.println("Metode Bayar: " + transaksi.getMetodePembayaran().getNamaMetode());
        System.out.println("----------------------------------------");

        for(DetailPesanan d : p.getDaftarItem()) {
            System.out.printf("%-20s x%d \t Rp %d\n",
                d.getItem().getNama(),
                d.getJumlah(),
                d.getSubtotal());
            if (!d.getCatatan().equals("-")) {
                 System.out.println("  > Catatan: " + d.getCatatan());
            }
        }

        System.out.println("----------------------------------------");
        System.out.printf("TOTAL BAYAR: \t\t\t Rp %d\n", p.hitungTotal());
        System.out.println("STATUS: LUNAS");
        System.out.println("========================================");
        System.out.println("      Terima Kasih Atas Kunjungan Anda");
        System.out.println("========================================\n");
    }
}
