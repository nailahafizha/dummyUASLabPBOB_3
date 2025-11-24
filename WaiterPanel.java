package dummyUASLabPBOB_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WaiterPanel extends Panel {
    
    // Dua list terpisah agar History tidak hilang
    private java.awt.List listAktif;
    private java.awt.List listHistory;
    
    // Data pendamping untuk mapping index list ke object Pesanan
    private List<Pesanan> dataAktif;
    private List<Pesanan> dataHistory;
    
    private TextArea detailArea;
    private Pesanan selectedPesanan = null; // Pesanan yang sedang diklik

    public WaiterPanel(AppFrame app) {
        setLayout(new BorderLayout(10, 10)); // Gap antar komponen
        setBackground(AppFrame.RED_DARK);

        // --- 1. HEADER ---
        Label header = new Label("Dashboard Pelayan", Label.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setForeground(AppFrame.GOLD);
        add(header, BorderLayout.NORTH);

        // --- 2. CENTER (Daftar Pesanan Split) ---
        Panel centerPanel = new Panel(new GridLayout(2, 1, 0, 10));
        centerPanel.setBackground(AppFrame.RED_DARK);

        // Panel Atas: Pesanan Aktif
        Panel pnlAktif = new Panel(new BorderLayout());
        pnlAktif.setBackground(AppFrame.RED_DARK);
        Label lblAktif = new Label(" Pesanan Aktif (Berlangsung):");
        lblAktif.setForeground(Color.white);
        lblAktif.setFont(new Font("SansSerif", Font.BOLD, 14));
        listAktif = new java.awt.List();
        pnlAktif.add(lblAktif, BorderLayout.NORTH);
        pnlAktif.add(listAktif, BorderLayout.CENTER);

        // Panel Bawah: History
        Panel pnlHistory = new Panel(new BorderLayout());
        pnlHistory.setBackground(AppFrame.RED_DARK);
        Label lblHistory = new Label(" Riwayat (Selesai/Batal):");
        lblHistory.setForeground(Color.lightGray);
        lblHistory.setFont(new Font("SansSerif", Font.BOLD, 14));
        listHistory = new java.awt.List();
        pnlHistory.add(lblHistory, BorderLayout.NORTH);
        pnlHistory.add(listHistory, BorderLayout.CENTER);

        centerPanel.add(pnlAktif);
        centerPanel.add(pnlHistory);
        
        add(centerPanel, BorderLayout.CENTER);

        // --- 3. RIGHT (Detail Area) ---
        Panel rightPanel = new Panel(new BorderLayout());
        rightPanel.setBackground(AppFrame.RED_DARK);
        rightPanel.setPreferredSize(new Dimension(320, 0)); 
        
        Label lblDetail = new Label("Detail Pesanan:");
        lblDetail.setForeground(AppFrame.GOLD);
        lblDetail.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        detailArea = new TextArea("", 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        rightPanel.add(lblDetail, BorderLayout.NORTH);
        rightPanel.add(detailArea, BorderLayout.CENTER);
        
        add(rightPanel, BorderLayout.EAST);

        // --- 4. BOTTOM (Tombol Aksi) ---
        Panel bottom = new Panel(new FlowLayout());
        bottom.setBackground(AppFrame.RED_DARK);
        
        Button btnNew = new Button("Pesanan Baru");
        Button btnUpdateStatus = new Button("Update Status"); 
        Button btnRefresh = new Button("Refresh");
        Button btnLogout = new Button("Logout");
        
        style(btnNew); style(btnUpdateStatus); style(btnRefresh); style(btnLogout);
        
        bottom.add(btnNew); 
        bottom.add(btnUpdateStatus); 
        bottom.add(btnRefresh); 
        bottom.add(btnLogout);
        
        add(bottom, BorderLayout.SOUTH);

        // --- 5. EVENT LISTENERS ---
        
        btnRefresh.addActionListener(e -> loadPesanan(app));
        btnLogout.addActionListener(e -> app.showPage("HOME"));
        btnNew.addActionListener(e -> openCreateOrderDialog(app));
        
        // Logic Update Status
        btnUpdateStatus.addActionListener(e -> {
            if (selectedPesanan != null) {
                openUpdateStatusDialog(app, selectedPesanan);
            } else {
                detailArea.setText("\n[INFO] Pilih salah satu pesanan\ndari daftar terlebih dahulu\nuntuk mengupdate status.");
            }
        });

        // Logic Klik List Aktif
        listAktif.addItemListener(e -> {
            int idx = listAktif.getSelectedIndex();
            if (idx >= 0 && idx < dataAktif.size()) {
                listHistory.deselect(listHistory.getSelectedIndex()); // Hapus seleksi list bawah
                selectedPesanan = dataAktif.get(idx);
                showPesananDetail(selectedPesanan);
            }
        });

        // Logic Klik List History
        listHistory.addItemListener(e -> {
            int idx = listHistory.getSelectedIndex();
            if (idx >= 0 && idx < dataHistory.size()) {
                listAktif.deselect(listAktif.getSelectedIndex()); // Hapus seleksi list atas
                selectedPesanan = dataHistory.get(idx);
                showPesananDetail(selectedPesanan);
            }
        });

        // Load data awal
        loadPesanan(app);
    }

    // --- LOGIC LOAD DATA (SPLIT LIST) ---
    private void loadPesanan(AppFrame app){
        listAktif.removeAll();
        listHistory.removeAll();
        
        dataAktif = new ArrayList<>();
        dataHistory = new ArrayList<>();
        
        List<Pesanan> allOrders = app.getSystem().getDaftarPesanan();
        
        for (Pesanan p : allOrders){
            String st = p.getStatus();
            String display = "ID#" + p.getIdPesanan() + " | Meja " + p.getMeja().getNomor() + " | " + st;
            
            // Pisahkan berdasarkan status
            if (st.equalsIgnoreCase("Lunas") || st.equalsIgnoreCase("Dibatalkan")) {
                dataHistory.add(p);
                listHistory.add(display);
            } else {
                dataAktif.add(p);
                listAktif.add(display);
            }
        }
        
        detailArea.setText("Klik pesanan untuk melihat detail.");
        selectedPesanan = null;
    }

    // --- LOGIC SHOW DETAIL ---
    private void showPesananDetail(Pesanan p){
        StringBuilder sb = new StringBuilder();
        sb.append("=== DETAIL PESANAN ===\n\n");
        sb.append("ID Pesanan : ").append(p.getIdPesanan()).append("\n");
        if (p.getCustomer() != null) {
            sb.append("Customer   : ").append(p.getCustomer().getNama()).append("\n");
        }
        sb.append("Meja No.   : ").append(p.getMeja().getNomor()).append("\n");
        sb.append("Status     : ").append(p.getStatus()).append("\n");
        sb.append("----------------------\n");
        for (DetailPesanan d : p.getDaftarItem()){
            sb.append("- ").append(d.getItem().getNama())
              .append(" x").append(d.getJumlah())
              .append("\n  (").append(d.getCatatan()).append(")\n");
        }
        sb.append("----------------------\n");
        sb.append("TOTAL: Rp ").append(p.hitungTotal());
        detailArea.setText(sb.toString());
    }
    
    // --- DIALOG UPDATE STATUS ---
    private void openUpdateStatusDialog(AppFrame app, Pesanan p) {
        Dialog dlg = new Dialog((Frame)app, "Update Status ID#" + p.getIdPesanan(), true);
        dlg.setSize(350, 200);
        dlg.setLayout(new FlowLayout());
        dlg.setBackground(AppFrame.RED_DARK);
        
        Label lbl = new Label("Pilih Status Baru:");
        lbl.setForeground(Color.white);
        
        // Pakai Choice agar Pelayan tidak Typo
        Choice statusChoice = new Choice();
        statusChoice.add("Dipesan");
        statusChoice.add("Sedang Dimasak");
        statusChoice.add("Selesai Dimasak");
        statusChoice.add("Menunggu Pembayaran Cash");
        statusChoice.add("Lunas");
        statusChoice.add("Dibatalkan");
        
        // Coba pilih status saat ini di dropdown
        try {
            statusChoice.select(p.getStatus());
        } catch (Exception e) {}
        
        Button btnSave = new Button("Simpan");
        Button btnCancel = new Button("Batal");
        style(btnSave); style(btnCancel);

        btnSave.addActionListener(e -> {
            String newStatus = statusChoice.getSelectedItem();
            p.setStatus(newStatus);
            
            loadPesanan(app); // Refresh list
            
            // Agar detail tetap tampil setelah refresh
            if (dataAktif.contains(p) || dataHistory.contains(p)) {
                showPesananDetail(p);
            }
            
            dlg.setVisible(false);
            dlg.dispose();
        });

        btnCancel.addActionListener(e -> {
            dlg.setVisible(false);
            dlg.dispose();
        });

        dlg.add(lbl);
        dlg.add(statusChoice);
        dlg.add(btnSave);
        dlg.add(btnCancel);
        
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    // --- DIALOG CREATE ORDER (Sama seperti kode lama kamu) ---
    private void openCreateOrderDialog(AppFrame app){
        Dialog dlg = new Dialog((Frame)app, "Buat Pesanan Baru", true);
        dlg.setSize(720, 520);
        dlg.setLayout(new BorderLayout());
        dlg.setBackground(AppFrame.RED_DARK);

        List<MenuItem> menus = app.getSystem().getDaftarMenu();

        java.awt.List menuList = new java.awt.List();
        for (MenuItem m : menus){
            menuList.add(m.getNama() + " - Rp" + m.getHarga());
        }

        java.awt.List draftList = new java.awt.List();
        TextField tfJumlah = new TextField("1", 5);
        TextField tfCatatan = new TextField("", 12);

        Panel top = new Panel(new FlowLayout());
        top.setBackground(AppFrame.RED_DARK);

        TextField tfMeja = new TextField("1", 5);
        TextField tfCustomerId = new TextField("101", 8);

        top.add(label("No Meja:")); top.add(tfMeja);
        top.add(label("ID Customer:")); top.add(tfCustomerId);

        Panel center = new Panel(new GridLayout(1,2,10,10));
        center.setBackground(AppFrame.RED_DARK);
        center.add(menuList);
        center.add(draftList);

        Panel controls = new Panel(new FlowLayout());
        controls.setBackground(AppFrame.RED_DARK);

        Button btnAdd = new Button("Tambah Item");
        Button btnSave = new Button("Simpan Pesanan");
        Button btnCancel = new Button("Batal");
        style(btnAdd); style(btnSave); style(btnCancel);

        controls.add(label("Jumlah:")); controls.add(tfJumlah);
        controls.add(label("Catatan:")); controls.add(tfCatatan);
        controls.add(btnAdd); controls.add(btnSave); controls.add(btnCancel);

        dlg.add(top, BorderLayout.NORTH);
        dlg.add(center, BorderLayout.CENTER);
        dlg.add(controls, BorderLayout.SOUTH);

        List<DetailPesanan> draft = new ArrayList<>();

        btnAdd.addActionListener(e -> {
            int idx = menuList.getSelectedIndex();
            if (idx < 0) return;

            MenuItem m = menus.get(idx);
            int jumlah = Integer.parseInt(tfJumlah.getText().trim());
            String cat = tfCatatan.getText().trim();

            DetailPesanan d = new DetailPesanan(m, jumlah, cat);
            draft.add(d);

            draftList.add(m.getNama() + " x" + jumlah + (cat.isEmpty()? "" : " ["+cat+"]"));
            tfJumlah.setText("1");
            tfCatatan.setText("");
        });

        btnSave.addActionListener(e -> {
            try {
                int nomorMeja = Integer.parseInt(tfMeja.getText().trim());
                int idCustomer = Integer.parseInt(tfCustomerId.getText().trim());

                Meja meja = new Meja(nomorMeja);

                Customer c = app.getSystem().findCustomerById(idCustomer);
                if (c == null) throw new RuntimeException("Customer tidak ditemukan.");

                int newId = app.getSystem().generateIdPesananBaru();
                Pesanan pesanan = c.buatPesanan(newId, meja);

                for (DetailPesanan d : draft) pesanan.tambahItem(d);
                app.getSystem().tambahPesanan(pesanan);

                dlg.setVisible(false); dlg.dispose();
                loadPesanan(app);

            } catch (Exception ex){
                System.out.println("Gagal simpan: " + ex.getMessage());
            }
        });

        btnCancel.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });

        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    // --- HELPERS ---
    private Label label(String t){
        Label l = new Label(t);
        l.setForeground(AppFrame.WHITE);
        return l;
    }
    
    private void style(Button b){
        b.setBackground(AppFrame.RED);
        b.setForeground(AppFrame.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}