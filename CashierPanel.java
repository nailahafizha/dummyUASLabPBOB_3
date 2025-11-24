package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CashierPanel extends Panel {
    private java.awt.List payList;
    private TextArea detailArea;

    public CashierPanel(AppFrame app){
        setLayout(new BorderLayout());
        setBackground(AppFrame.RED_DARK);

        Label header = new Label("Dashboard Kasir", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(AppFrame.GOLD);
        add(header, BorderLayout.NORTH);

        payList = new java.awt.List();
        detailArea = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        detailArea.setEditable(false);

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(payList);
        center.add(detailArea);
        add(center, BorderLayout.CENTER);

        Choice paymentChoice = new Choice();
        paymentChoice.add("Cash");
        paymentChoice.add("Card");
        paymentChoice.add("QRIS");

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);

        Button btnPay = new Button("Proses Pembayaran");
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        style(btnPay); style(btnRefresh); style(btnLogout);

        bottom.add(label("Metode:"));
        bottom.add(paymentChoice);
        bottom.add(btnPay);
        bottom.add(btnRefresh);
        bottom.add(btnLogout);
        add(bottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> load(app));
        btnLogout.addActionListener(e -> app.showPage("HOME"));
        payList.addItemListener(e -> showDetail(app));

        btnPay.addActionListener(e -> {
            int idx = payList.getSelectedIndex();
            if (idx < 0) return;
            Pesanan p = getList(app).get(idx);

            try {
                Pembayaran metode;
                String m = paymentChoice.getSelectedItem();
                if (m.equals("Cash")) metode = new CashPayment();
                else if (m.equals("Card")) metode = new CardPayment();
                else metode = new QRISPayment();

                int newIdTrx = app.getSystem().generateIdTransaksiBaru();
                Transaksi t = new Transaksi(newIdTrx, p, metode);

                String rawInput = PaymentInputDialog.askInput((Frame) app, m, p.hitungTotal());
                if (rawInput == null) {
                    showReceiptDialog(app, "Pembayaran dibatalkan.");
                    return;
                }

                Scanner sc = new Scanner(rawInput);
                t.konfirmasi(sc);

                if (!t.isStatusKonfirmasi()) {
                    showReceiptDialog(app, "Pembayaran gagal. Uang tidak cukup.");
                    return;
                }

                showReceiptDialog(app, buildStrukText(t));
                load(app);

            } catch (Exception ex){
                detailArea.setText("Pembayaran gagal: " + ex.getMessage());
            }
        });

        load(app);
    }

    // Kasir bisa bayar untuk status "Selesai Dimasak" atau "Menunggu Pembayaran Cash"
    private List<Pesanan> getList(AppFrame app){
        List<Pesanan> res = new ArrayList<>();
        res.addAll(app.getSystem().getDaftarPesananByStatus("Selesai Dimasak"));
        res.addAll(app.getSystem().getDaftarPesananByStatus("Menunggu Pembayaran Cash"));
        return res;
    }

    private void load(AppFrame app){
        payList.removeAll();
        for (Pesanan p : getList(app)){
            payList.add("ID#" + p.getIdPesanan()
                    + " | Meja " + p.getMeja().getNomor()
                    + " | Total Rp " + p.hitungTotal()
                    + " | " + p.getStatus());
        }
        detailArea.setText("Pilih pesanan untuk detail.");
    }

    private void showDetail(AppFrame app){
        int idx = payList.getSelectedIndex();
        if (idx < 0) return;
        Pesanan p = getList(app).get(idx);

        StringBuilder sb = new StringBuilder();
        sb.append("Pesanan ID: ").append(p.getIdPesanan()).append("\n");
        sb.append("Status: ").append(p.getStatus()).append("\n\n");
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append("- ").append(d.getItem().getNama())
              .append(" x").append(d.getJumlah())
              .append(" = Rp").append(d.getSubtotal()).append("\n");
        }
        sb.append("\nTOTAL: Rp ").append(p.hitungTotal());
        detailArea.setText(sb.toString());
    }

    private String buildStrukText(Transaksi transaksi){
        Pesanan p = transaksi.getPesanan();
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("              STRUK PEMBAYARAN\n");
        sb.append("========================================\n");
        sb.append("ID Transaksi: ").append(transaksi.getIdTransaksi()).append("\n");
        sb.append("ID Pesanan  : ").append(p.getIdPesanan()).append("\n");
        sb.append("Meja No.    : ").append(p.getMeja().getNomor()).append("\n");
        sb.append("Metode Bayar: ").append(transaksi.getMetodePembayaran().getNamaMetode()).append("\n");
        sb.append("----------------------------------------\n");

        for (DetailPesanan d : p.getDaftarItem()){
            sb.append(String.format("%-20s x%d \t Rp %d\n",
                    d.getItem().getNama(), d.getJumlah(), d.getSubtotal()));
            if (!d.getCatatan().equals("-")){
                sb.append("  > Catatan: ").append(d.getCatatan()).append("\n");
            }
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("TOTAL BAYAR:\t\t\t Rp %d\n", p.hitungTotal()));
        sb.append("STATUS: LUNAS\n");
        sb.append("========================================\n");
        sb.append("      Terima Kasih Atas Kunjungan Anda\n");
        sb.append("========================================\n");

        return sb.toString();
    }

    private void showReceiptDialog(AppFrame app, String text){
        Dialog dlg = new Dialog((Frame)app, "Struk Pembayaran", true);
        dlg.setSize(460, 520);
        dlg.setLayout(new BorderLayout());

        TextArea ta = new TextArea(text);
        ta.setEditable(false);
        Button close = new Button("Tutup");
        close.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });

        dlg.add(ta, BorderLayout.CENTER);
        dlg.add(close, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    private Label label(String t){
        Label l = new Label(t);
        l.setForeground(AppFrame.WHITE);
        return l;
    }

    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
}
