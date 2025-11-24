package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChefPanel extends Panel {
    
    private java.awt.List listMasuk;
    private java.awt.List listProses;
    
    private List<Pesanan> dataMasuk;
    private List<Pesanan> dataProses;
    
    private TextArea detailArea;
    private Pesanan selectedPesanan = null;
    private Button btnAction; 

    public ChefPanel(AppFrame app){
        setLayout(new BorderLayout(10, 10));
        setBackground(AppFrame.RED_DARK);

        Label header = new Label("Dapur (Kitchen Display)", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(AppFrame.GOLD);
        add(header, BorderLayout.NORTH);

        Panel centerPanel = new Panel(new GridLayout(2, 1, 0, 10));
        centerPanel.setBackground(AppFrame.RED_DARK);

        Panel pnlMasuk = new Panel(new BorderLayout());
        pnlMasuk.setBackground(AppFrame.RED_DARK);
        Label lblMasuk = new Label(" ANTRIAN BARU (Siap Dimasak):");
        lblMasuk.setForeground(Color.white);
        lblMasuk.setFont(new Font("SansSerif", Font.BOLD, 14));
        listMasuk = new java.awt.List();
        pnlMasuk.add(lblMasuk, BorderLayout.NORTH);
        pnlMasuk.add(listMasuk, BorderLayout.CENTER);

        Panel pnlProses = new Panel(new BorderLayout());
        pnlProses.setBackground(AppFrame.RED_DARK);
        Label lblProses = new Label(" SEDANG DIMASAK:");
        lblProses.setForeground(AppFrame.GOLD);
        lblProses.setFont(new Font("SansSerif", Font.BOLD, 14));
        listProses = new java.awt.List();
        pnlProses.add(lblProses, BorderLayout.NORTH);
        pnlProses.add(listProses, BorderLayout.CENTER);

        centerPanel.add(pnlMasuk);
        centerPanel.add(pnlProses);
        add(centerPanel, BorderLayout.CENTER);

        Panel rightPanel = new Panel(new BorderLayout());
        rightPanel.setBackground(AppFrame.RED_DARK);
        rightPanel.setPreferredSize(new Dimension(300, 0));
        
        detailArea = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        rightPanel.add(new Label(" Detail Resep:", Label.CENTER), BorderLayout.NORTH);
        rightPanel.add(detailArea, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);

        btnAction = new Button("Pilih Pesanan Dulu");
        btnAction.setEnabled(false); 
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        
        style(btnAction); style(btnRefresh); style(btnLogout);
        btnAction.setBackground(AppFrame.GOLD); 
        btnAction.setForeground(Color.black);

        bottom.add(btnAction);
        bottom.add(btnRefresh);
        bottom.add(btnLogout);
        add(bottom, BorderLayout.SOUTH);
        
        btnRefresh.addActionListener(e -> load(app));
        btnLogout.addActionListener(e -> app.showPage("HOME"));

        // Klik List Masuk
        listMasuk.addItemListener(e -> {
            int idx = listMasuk.getSelectedIndex();
            if (idx >= 0) {
                listProses.deselect(listProses.getSelectedIndex());
                selectedPesanan = dataMasuk.get(idx);
                showDetail(selectedPesanan);
                btnAction.setLabel("Mulai Masak");
                btnAction.setEnabled(true);
            }
        });

        listProses.addItemListener(e -> {
            int idx = listProses.getSelectedIndex();
            if (idx >= 0) {
                listMasuk.deselect(listMasuk.getSelectedIndex());
                selectedPesanan = dataProses.get(idx);
                showDetail(selectedPesanan);
                btnAction.setLabel("Selesai (Siap disaji)");
                btnAction.setEnabled(true);
            }
        });

        // Logika Tombol Aksi (Dinamis)
        btnAction.addActionListener(e -> {
            if (selectedPesanan == null) return;
            
            String status = selectedPesanan.getStatus();
            
            if (status.equals("Dipesan")) {
                selectedPesanan.setStatus("Sedang Dimasak");
            } else if (status.equals("Sedang Dimasak")) {
                selectedPesanan.setStatus("Selesai Dimasak");
            }
            
            load(app);
            detailArea.setText("Status diperbarui.");
            btnAction.setLabel("Pilih Pesanan Dulu");
            btnAction.setEnabled(false);
            selectedPesanan = null;
        });

        load(app);
    }

    private void load(AppFrame app){
        listMasuk.removeAll();
        listProses.removeAll();
        
        dataMasuk = new ArrayList<>();
        dataProses = new ArrayList<>();
        
        List<Pesanan> all = app.getSystem().getDaftarPesanan();
        
        for (Pesanan p : all){
            if (p.getStatus().equals("Dipesan")) {
                dataMasuk.add(p);
                listMasuk.add("ID#" + p.getIdPesanan() + " | Meja " + p.getMeja().getNomor());
            } 
            else if (p.getStatus().equals("Sedang Dimasak")) {
                dataProses.add(p);
                listProses.add("ID#" + p.getIdPesanan() + " | Meja " + p.getMeja().getNomor());
            }
        }
    }

    private void showDetail(Pesanan p){
        StringBuilder sb = new StringBuilder();
        sb.append("ID Pesanan: ").append(p.getIdPesanan()).append("\n");
        sb.append("Meja      : ").append(p.getMeja().getNomor()).append("\n");
        sb.append("Status    : ").append(p.getStatus().toUpperCase()).append("\n");
        sb.append("--------------------------\n");
        sb.append("DAFTAR ITEM:\n");
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append("- ").append(d.getItem().getNama())
              .append(" (x").append(d.getJumlah()).append(")\n");
            
            if (!d.getCatatan().equals("-")) {
                sb.append("  Note: ").append(d.getCatatan()).append("\n");
            }
            sb.append("\n");
        }
        detailArea.setText(sb.toString());
    }

    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}