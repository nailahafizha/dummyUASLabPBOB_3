package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CashierPanel extends Panel {
    private java.awt.List payList;
    private TextArea detailArea;
    private Label lblMetodeInfo;

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
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(payList);
        center.add(detailArea);
        add(center, BorderLayout.CENTER);

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);

        Button btnPay = new Button("Proses Pembayaran");
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        style(btnPay); style(btnRefresh); style(btnLogout);
        
        lblMetodeInfo = new Label("Metode: -");
        lblMetodeInfo.setForeground(Color.white);
        lblMetodeInfo.setFont(new Font("SansSerif", Font.BOLD, 14));

        bottom.add(lblMetodeInfo);
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
                String methodStr = p.getMetodePembayaran();
                Pembayaran metode;
                
                if (methodStr.equalsIgnoreCase("Card")) metode = new CardPayment();
                else if (methodStr.equalsIgnoreCase("QRIS")) metode = new QRISPayment();
                else metode = new CashPayment();

                int newIdTrx = app.getSystem().generateIdTransaksiBaru();
                Transaksi t = new Transaksi(newIdTrx, p, metode);

                String rawInput = PaymentInputDialog.askInput((Frame) app, methodStr, p.hitungTotal());
                
                if (rawInput == null) {
                    showReceiptDialog(app, "Pembayaran dibatalkan.");
                    return;
                }

                double uangTunai = 0;
                try { uangTunai = Double.parseDouble(rawInput.trim()); } catch (Exception ex) {}

                t.konfirmasi(new Scanner(rawInput));

                if (!t.isStatusKonfirmasi()) {
                    showReceiptDialog(app, "Pembayaran gagal. Uang tidak cukup.");
                    return;
                }

                String struk = buildStrukText(t);
                if (metode instanceof CashPayment) {
                    double kembalian = uangTunai - p.hitungTotal();
                    struk += "\nUang Tunai: " + formatRupiah((int)uangTunai);
                    struk += "\nKembalian : " + formatRupiah((int)kembalian);
                }

                showReceiptDialog(app, struk);
                load(app);

            } catch (Exception ex){
                detailArea.setText("Error: " + ex.getMessage());
            }
        });

        load(app);
    }

    private List<Pesanan> getList(AppFrame app){
        List<Pesanan> list = new ArrayList<>();
        list.addAll(app.getSystem().getDaftarPesananByStatus("Selesai Dimasak"));
        list.addAll(app.getSystem().getDaftarPesananByStatus("Menunggu Pembayaran Cash"));
        return list;
    }

    private void load(AppFrame app){
        payList.removeAll();
        for (Pesanan p : getList(app)){
            payList.add("ID#" + p.getIdPesanan() + " | Meja " + p.getMeja().getNomor() + " | " + formatRupiah(p.hitungTotal()) + " | " + p.getStatus());
        }
        detailArea.setText("Pilih pesanan untuk proses.");
        lblMetodeInfo.setText("Metode: -");
    }

    private void showDetail(AppFrame app){
        int idx = payList.getSelectedIndex();
        if (idx < 0) return;
        Pesanan p = getList(app).get(idx);

        lblMetodeInfo.setText("Metode: " + p.getMetodePembayaran().toUpperCase());

        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(p.getIdPesanan()).append("\n");
        if (p.getCustomer() != null) {
            sb.append("Customer: ").append(p.getCustomer().getNama()).append("\n");
        }
        sb.append("Metode Bayar: ").append(p.getMetodePembayaran()).append("\n"); 
        sb.append("----------------\n");
        
        // Tampilkan item
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append(d.getItem().getNama()).append(" x").append(d.getJumlah())
              .append(" = ").append(formatRupiah(d.getSubtotal())).append("\n");
        }
        
        sb.append("----------------\n");
        sb.append("Subtotal: ").append(formatRupiah(p.hitungSubtotal())).append("\n");
        sb.append("Pajak (10%): ").append(formatRupiah(p.getPajak())).append("\n");
        sb.append("Service (5%): ").append(formatRupiah(p.getService())).append("\n");
        sb.append("TOTAL: ").append(formatRupiah(p.hitungTotal()));
        
        detailArea.setText(sb.toString());
    }

    private String buildStrukText(Transaksi t){
        Pesanan p = t.getPesanan();
        StringBuilder sb = new StringBuilder();

        sb.append("========================================\n");
        sb.append("              STRUK PEMBAYARAN\n");
        sb.append("========================================\n");
        sb.append("ID Transaksi: ").append(t.getIdTransaksi()).append("\n");
        sb.append("ID Pesanan  : ").append(p.getIdPesanan()).append("\n");
        sb.append("Meja No.    : ").append(p.getMeja().getNomor()).append("\n");
        sb.append("Metode Bayar: ").append(t.getMetodePembayaran().getNamaMetode()).append("\n");
        sb.append("----------------------------------------\n");

        for (DetailPesanan d : p.getDaftarItem()){
            sb.append(String.format("%-20s x%d \t %s\n",
                    d.getItem().getNama(), d.getJumlah(), formatRupiah(d.getSubtotal())));
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("Subtotal:\t\t %s\n", formatRupiah(p.hitungSubtotal())));
        sb.append(String.format("Pajak (10%%):\t\t %s\n", formatRupiah(p.getPajak())));
        sb.append(String.format("Service (5%%):\t\t %s\n", formatRupiah(p.getService())));
        sb.append("----------------------------------------\n");
        sb.append(String.format("GRAND TOTAL:\t\t %s\n", formatRupiah(p.hitungTotal())));
        sb.append("STATUS: LUNAS\n");
        sb.append("========================================\n");
        sb.append("      Terima Kasih Atas Kunjungan Anda\n");
        sb.append("========================================\n");

        return sb.toString();
    }

    private void showReceiptDialog(AppFrame app, String text){
        Dialog dlg = new Dialog((Frame)app, "Struk Pembayaran", true);
        dlg.setSize(460, 600); 
        dlg.setLayout(new BorderLayout());

        TextArea ta = new TextArea(text);
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        
        Button close = new Button("Tutup");
        style(close);
        close.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });

        dlg.add(ta, BorderLayout.CENTER);
        dlg.add(close, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
    }
    
    private String formatRupiah(int val) {
        return "Rp " + val;
    }
}