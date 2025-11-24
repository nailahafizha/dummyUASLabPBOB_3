package dummyUASLabPBOB_3;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerPanel extends Panel {

    private final AppFrame app;

    // Colors
    private final Color RED_DARK = AppFrame.RED_DARK;      // #8B0000
    private final Color GOLD = AppFrame.GOLD;             // #DAA520
    private final Color BG_LIGHT = new Color(248, 245, 236);
    private final Color CARD_BG = Color.white;
    private final Color RED_ACCENT = new Color(200, 0, 0);

    private Panel menuListPanel;
    private ScrollPane menuScroll;

    private java.awt.List cartList;
    private List<DetailPesanan> cart = new ArrayList<>();

    private TextField tfMeja;
    private TextArea taCatatan;
    private Choice choiceMetode;

    private Label lblItemCount;
    private Label lblSubtotal;
    private Label lblTax;
    private Label lblService;
    private Label lblTotal;

    public CustomerPanel(AppFrame app) {
        this.app = app;

        setLayout(new BorderLayout());
        setBackground(BG_LIGHT);

        add(buildHeader(), BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);

        loadMenu();
        updateSummary();
    }

    // header
    private Panel buildHeader() {
        Panel header = new Panel(new BorderLayout());
        header.setBackground(RED_ACCENT);
        header.setPreferredSize(new Dimension(1000, 90));

        Panel titleBox = new Panel(new GridLayout(2,1));
        titleBox.setBackground(RED_ACCENT);

        Label title = new Label("Xing Fu Restaurant", Label.CENTER);
        title.setForeground(GOLD);
        title.setFont(FontLoader.loadNotoSerifSC(28, Font.BOLD));

        Label sub = new Label("Sistem Pemesanan Online", Label.CENTER);
        sub.setForeground(Color.white);
        sub.setFont(new Font("SansSerif", Font.ITALIC, 14));

        titleBox.add(title);
        titleBox.add(sub);

        Panel right = new Panel(new FlowLayout(FlowLayout.RIGHT, 14, 28));
        right.setBackground(RED_ACCENT);
        
        // tombol pesanan saya
        Button btnMyOrder = new Button("Pesanan Saya"); 
        styleGoldButton(btnMyOrder);
        btnMyOrder.addActionListener(e -> showMyOrderDialog());
        
        Button btnLogout = new Button("Logout");
        styleGoldButton(btnLogout);
        btnLogout.addActionListener(e -> {
            cart.clear();
            if (cartList != null) cartList.removeAll();
            app.showPage("HOME");
        });
        
        right.add(btnMyOrder);
        right.add(btnLogout);

        header.add(titleBox, BorderLayout.CENTER);
        header.add(right, BorderLayout.EAST);
        return header;
    }

    // content
    private Panel buildContent() {
        Panel content = new Panel(new GridLayout(1,3,12,12));
        content.setBackground(BG_LIGHT);
        content.setPreferredSize(new Dimension(1000, 600));

        content.add(buildMenuColumn());
        content.add(buildCheckoutColumn());
        content.add(buildSummaryColumn());

        return content;
    }

    // menu kolom
    private Panel buildMenuColumn() {
        Panel leftWrap = new Panel(new BorderLayout());
        leftWrap.setBackground(BG_LIGHT);

        Panel leftCard = new Panel(new BorderLayout());
        leftCard.setBackground(CARD_BG);

        Label title = sectionTitle("Daftar Menu");
        leftCard.add(title, BorderLayout.NORTH);

        menuListPanel = new Panel(new GridBagLayout());
        menuListPanel.setBackground(CARD_BG);

        menuScroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        menuScroll.add(menuListPanel);

        leftCard.add(menuScroll, BorderLayout.CENTER);

        leftWrap.add(leftCard, BorderLayout.CENTER);
        return leftWrap;
    }

    private void loadMenu() {
        menuListPanel.removeAll();

        List<MenuItem> menus = app.getSystem().getDaftarMenu();

        List<MenuItem> appetizers = new ArrayList<>();
        List<MenuItem> mains = new ArrayList<>();
        List<MenuItem> drinks = new ArrayList<>();

        for (MenuItem m : menus) {
            if (m instanceof Minuman) {
                drinks.add(m);
            } else {
                String info = m.getInfo();
                String cat = parseKategori(info).toLowerCase();
                if (cat.contains("appetizer") || cat.contains("pembuka") || cat.contains("snack")) {
                    appetizers.add(m);
                } else {
                    mains.add(m);
                }
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.weightx=1; gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 10, 6, 10);

        int y = 0;

        if (!appetizers.isEmpty()) {
            gbc.gridy = y++;
            menuListPanel.add(categoryLabel("APPETIZER"), gbc);
            for (MenuItem m : appetizers) {
                gbc.gridy = y++;
                menuListPanel.add(new MenuCardPanel(m), gbc);
            }
        }

        if (!mains.isEmpty()) {
            gbc.gridy = y++;
            menuListPanel.add(categoryLabel("MAIN MENU"), gbc);
            for (MenuItem m : mains) {
                gbc.gridy = y++;
                menuListPanel.add(new MenuCardPanel(m), gbc);
            }
        }

        if (!drinks.isEmpty()) {
            gbc.gridy = y++;
            menuListPanel.add(categoryLabel("DRINKS"), gbc);
            for (MenuItem m : drinks) {
                gbc.gridy = y++;
                menuListPanel.add(new MenuCardPanel(m), gbc);
            }
        }

        gbc.gridy = y++;
        gbc.weighty = 1;
        menuListPanel.add(new Label(""), gbc);

        menuListPanel.validate();
        menuListPanel.repaint();
    }

    // komponen card
    private class MenuCardPanel extends Panel {
        MenuCardPanel(MenuItem item) {
            setLayout(new BorderLayout());
            setBackground(CARD_BG);
            setPreferredSize(new Dimension(280, 100));

            Panel textBox = new Panel(new GridLayout(3,1));
            textBox.setBackground(CARD_BG);

            Label name = new Label(item.getNama());
            name.setFont(new Font("Serif", Font.BOLD, 15));
            name.setForeground(RED_DARK);

            String desc = lookupDeskripsi(item);

            Label descLbl = new Label(desc);
            descLbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
            descLbl.setForeground(Color.darkGray);

            Label price = new Label(formatRupiah(item.getHarga()));
            price.setFont(new Font("SansSerif", Font.BOLD, 12));
            price.setForeground(RED_DARK);

            textBox.add(name);
            textBox.add(descLbl);
            textBox.add(price);

            Button addBtn = new Button("+ Tambah");
            addBtn.setBackground(RED_ACCENT);
            addBtn.setForeground(Color.white);
            addBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
            addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            addBtn.addActionListener(e -> addItemToCart(item));

            Panel btnBox = new Panel(new FlowLayout(FlowLayout.RIGHT, 8, 28));
            btnBox.setBackground(CARD_BG);
            btnBox.add(addBtn);

            add(textBox, BorderLayout.CENTER);
            add(btnBox, BorderLayout.EAST);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(GOLD);
            g.drawRoundRect(0,0,getWidth()-1,getHeight()-1,8,8);
        }
    }

    // deskripsi
    private String lookupDeskripsi(MenuItem item){
        String n = item.getNama().toLowerCase();

        if (n.contains("mapo tofu")) return "Tahu sutra lembut dengan saus Sichuan gurih pedas mala.";
        if (n.contains("hot pot")) return "Rebusan kuah hangat ala China dengan aneka isian pilihan.";
        if (n.contains("kwetiau siram seafood")) return "Kwetiau lebar siram saus gurih dengan seafood segar.";
        if (n.contains("crystal prawn dumplings") || n.contains("hakao")) return "Dumpling bening kenyal berisi udang segar premium.";
        if (n.contains("green tea")) return "Teh hijau menyegarkan dengan aroma ringan, disajikan dingin.";
        if (n.contains("black tea")) return "Teh hitam beraroma kuat dan pekat, disajikan dingin.";

        return shortDescFromInfo(item.getInfo());
    }

    private String shortDescFromInfo(String info){
        if (info == null) return "Menu spesial";
        int open = info.indexOf('(');
        int close = info.indexOf(')');
        if (open != -1 && close != -1 && close > open){
            String inside = info.substring(open + 1, close).trim();
            if (!inside.isEmpty()) return inside;
        }
        return "Menu spesial";
    }

    private void addItemToCart(MenuItem item) {
        int jumlah = askJumlah();
        if (jumlah <= 0) return;

        String catatanItem = askCatatan(item.getNama());

        DetailPesanan d = new DetailPesanan(item, jumlah, catatanItem);
        cart.add(d);

        cartList.add(item.getNama() + " x" + jumlah +
                (catatanItem == null || catatanItem.trim().isEmpty() ? "" : " [" + catatanItem + "]"));

        updateSummary();
    }

    private int askJumlah() {
        Dialog dlg = new Dialog((Frame)app, "Jumlah", true);
        dlg.setSize(280, 150);
        dlg.setLayout(new BorderLayout());

        Panel mid = new Panel(new FlowLayout());
        Label l = new Label("Masukkan jumlah:");
        TextField tf = new TextField("1", 5);
        mid.add(l); mid.add(tf);

        Panel bot = new Panel(new FlowLayout());
        Button ok = new Button("OK");
        Button cancel = new Button("Batal");
        styleGoldButton(ok);
        cancel.setBackground(Color.lightGray);

        final int[] result = new int[]{0};

        ok.addActionListener(e -> {
            try {
                int val = Integer.parseInt(tf.getText().trim());
                if (val > 0) result[0] = val;
            } catch (Exception ex) {}
            dlg.setVisible(false);
            dlg.dispose();
        });

        cancel.addActionListener(e -> {
            dlg.setVisible(false);
            dlg.dispose();
        });

        bot.add(ok); bot.add(cancel);

        dlg.add(mid, BorderLayout.CENTER);
        dlg.add(bot, BorderLayout.SOUTH);

        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);

        return result[0];
    }

    private String askCatatan(String namaMenu) {
        Dialog dlg = new Dialog((Frame)app, "Catatan Item", true);
        dlg.setSize(350, 220);
        dlg.setLayout(new BorderLayout());

        Label top = new Label("Catatan untuk: " + namaMenu);
        top.setFont(new Font("SansSerif", Font.BOLD, 12));

        TextArea ta = new TextArea("", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);

        Panel bot = new Panel(new FlowLayout());
        Button ok = new Button("OK");
        Button skip = new Button("Lewati");
        styleGoldButton(ok);
        skip.setBackground(Color.lightGray);

        final String[] result = new String[]{""};

        ok.addActionListener(e -> {
            result[0] = ta.getText().trim();
            dlg.setVisible(false);
            dlg.dispose();
        });

        skip.addActionListener(e -> {
            dlg.setVisible(false);
            dlg.dispose();
        });

        bot.add(ok); bot.add(skip);

        dlg.add(top, BorderLayout.NORTH);
        dlg.add(ta, BorderLayout.CENTER);
        dlg.add(bot, BorderLayout.SOUTH);

        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);

        return result[0];
    }

    // checkout kolom
    private Panel buildCheckoutColumn() {
        Panel midWrap = new Panel(new BorderLayout());
        midWrap.setBackground(BG_LIGHT);

        Panel midCard = new Panel(new BorderLayout());
        midCard.setBackground(CARD_BG);

        Label title = sectionTitle("Checkout");
        midCard.add(title, BorderLayout.NORTH);

        Panel body = new Panel(new BorderLayout());
        body.setBackground(CARD_BG);

        cartList = new java.awt.List();
        body.add(cartList, BorderLayout.CENTER);

        Panel cartActions = new Panel(new FlowLayout(FlowLayout.RIGHT));
        cartActions.setBackground(CARD_BG);
        Button btnRemove = new Button("Hapus Terpilih");
        btnRemove.setBackground(Color.lightGray);
        btnRemove.addActionListener(e -> removeSelectedCart());
        cartActions.add(btnRemove);
        body.add(cartActions, BorderLayout.SOUTH);

        midCard.add(body, BorderLayout.CENTER);

        Panel form = new Panel(new GridLayout(4,1,6,6));
        form.setBackground(CARD_BG);

        Panel mejaBox = new Panel(new BorderLayout());
        mejaBox.setBackground(CARD_BG);
        Label mejaLbl = fieldLabel("Nomor Meja:");
        tfMeja = new TextField("1");
        mejaBox.add(mejaLbl, BorderLayout.NORTH);
        mejaBox.add(tfMeja, BorderLayout.CENTER);

        Panel catBox = new Panel(new BorderLayout());
        catBox.setBackground(CARD_BG);
        Label catLbl = fieldLabel("Catatan Pesanan:");
        taCatatan = new TextArea("", 3, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
        catBox.add(catLbl, BorderLayout.NORTH);
        catBox.add(taCatatan, BorderLayout.CENTER);
        Panel payBox = new Panel(new BorderLayout()); payBox.setBackground(CARD_BG);
        payBox.add(fieldLabel("Metode Pembayaran:"), BorderLayout.NORTH);
        choiceMetode = new Choice();
        choiceMetode.add("Cash");
        choiceMetode.add("Card");
        choiceMetode.add("QRIS");
        payBox.add(choiceMetode, BorderLayout.CENTER);
        form.add(new Label(""));
        form.add(mejaBox);
        form.add(catBox);

        midCard.add(form, BorderLayout.SOUTH);

        midWrap.add(midCard, BorderLayout.CENTER);
        return midWrap;
    }

    private void removeSelectedCart() {
        int idx = cartList.getSelectedIndex();
        if (idx < 0) return;

        cartList.remove(idx);
        cart.remove(idx);

        updateSummary();
    }

    // summary kolom
    private Panel buildSummaryColumn() {
        Panel rightWrap = new Panel(new BorderLayout());
        rightWrap.setBackground(BG_LIGHT);

        Panel rightCard = new Panel(new BorderLayout());
        rightCard.setBackground(CARD_BG);

        Label title = sectionTitle("Ringkasan Pesanan");
        rightCard.add(title, BorderLayout.NORTH);

        Panel body = new Panel(new GridLayout(8,2,6,6));
        body.setBackground(CARD_BG);

        lblItemCount = sumValueLabel();
        lblSubtotal = sumValueLabel();
        lblTax = sumValueLabel();
        lblService = sumValueLabel();
        lblTotal = new Label("Rp 0", Label.RIGHT);
        lblTotal.setFont(new Font("Serif", Font.BOLD, 18));
        lblTotal.setForeground(RED_DARK);

        body.add(sumLabel("Jumlah Item:")); body.add(lblItemCount);
        body.add(line()); body.add(line());

        body.add(sumLabel("Subtotal:")); body.add(lblSubtotal);
        body.add(sumLabel("Pajak (10%):")); body.add(lblTax);
        body.add(sumLabel("Biaya Layanan (5%):")); body.add(lblService);

        body.add(lineRed()); body.add(lineRed());

        body.add(sumLabel("Total Bayar:")); body.add(lblTotal);

        rightCard.add(body, BorderLayout.CENTER);

        Panel btnBox = new Panel(new FlowLayout(FlowLayout.CENTER, 8, 16));
        btnBox.setBackground(CARD_BG);

        Button confirm = new Button("Konfirmasi Pesanan");
        confirm.setBackground(RED_ACCENT);
        confirm.setForeground(Color.white);
        confirm.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirm.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirm.setPreferredSize(new Dimension(240, 45));
        confirm.addActionListener(e -> submitOrder());

        btnBox.add(confirm);
        rightCard.add(btnBox, BorderLayout.SOUTH);

        rightWrap.add(rightCard, BorderLayout.CENTER);
        return rightWrap;
    }

    private void submitOrder() {
        Customer c = app.getCurrentCustomer();
        if (c == null) {
            showMessage("Error", "Customer belum login.");
            return;
        }

        if (cart.isEmpty()) {
            showMessage("Info", "Keranjang masih kosong.");
            return;
        }

        int nomorMeja;
        try {
            nomorMeja = Integer.parseInt(tfMeja.getText().trim());
            if (nomorMeja <= 0) throw new Exception();
        } catch (Exception ex) {
            showMessage("Error", "Nomor meja tidak valid.");
            return;
        }

        Meja meja = new Meja(nomorMeja);
        int newId = app.getSystem().getDaftarPesanan().size() + 1;

        Pesanan pesanan = c.buatPesanan(newId, meja);
        for (DetailPesanan d : cart) {
            pesanan.tambahItem(d);
        }

        app.getSystem().tambahPesanan(pesanan);

        cart.clear();
        cartList.removeAll();
        taCatatan.setText("");

        updateSummary();

        showMessage("Sukses",
                "Pesanan berhasil dibuat.\nID Pesanan: " + pesanan.getIdPesanan() +
                "\nStatus: " + pesanan.getStatus() +
                "\nSilakan tunggu pesanan dimasak.");
    }

    // summary logic
    private void updateSummary() {
        int itemCount = 0;
        int subtotal = 0;

        for (DetailPesanan d : cart) {
            itemCount += d.getJumlah();
            subtotal += d.getSubtotal();
        }

        int tax = (int) Math.round(subtotal * 0.10);
        int service = (int) Math.round(subtotal * 0.05);
        int total = subtotal + tax + service;

        lblItemCount.setText(String.valueOf(itemCount));
        lblSubtotal.setText(formatRupiah(subtotal));
        lblTax.setText(formatRupiah(tax));
        lblService.setText(formatRupiah(service));
        lblTotal.setText(formatRupiah(total));
    }

    // lihat pesanan
    private void showMyOrderDialog() {
        Customer c = app.getCurrentCustomer();
        if (c == null) return;

        // Cari pesanan aktif milik customer ini
        Pesanan myOrder = null;
        for (Pesanan p : app.getSystem().getDaftarPesanan()) {
            if (p.getCustomer().getId() == c.getId() && 
               (p.getStatus().equals("Dipesan") || p.getStatus().equals("Selesai Dimasak") || p.getStatus().equals("Menunggu Pembayaran Cash"))) {
                myOrder = p;
                break; // Asumsi 1 pesanan aktif per customer
            }
        }

        Dialog dlg = new Dialog((Frame)app, "Pesanan Saya", true);
        dlg.setSize(400, 450);
        dlg.setLayout(new BorderLayout());
        
        if (myOrder == null) {
            dlg.add(new Label("Anda belum memiliki pesanan aktif.", Label.CENTER), BorderLayout.CENTER);
        } else {
            TextArea detail = new TextArea();
            detail.setEditable(false);
            detail.setFont(new Font("Monospaced", Font.PLAIN, 12));
            
            StringBuilder sb = new StringBuilder();
            sb.append("ID Pesanan: ").append(myOrder.getIdPesanan()).append("\n");
            sb.append("Status    : ").append(myOrder.getStatus()).append("\n");
            sb.append("------------------------------\n");
            for(DetailPesanan dp : myOrder.getDaftarItem()) {
                sb.append(String.format("%-20s x%d  %s\n", 
                    dp.getItem().getNama(), dp.getJumlah(), formatRupiah(dp.getSubtotal())));
            }
            sb.append("------------------------------\n");
            sb.append("Subtotal: ").append(formatRupiah(myOrder.hitungSubtotal())).append("\n");
            sb.append("Pajak & Service (15%): ").append(formatRupiah(myOrder.getPajak() + myOrder.getService())).append("\n");
            sb.append("GRAND TOTAL: ").append(formatRupiah(myOrder.hitungTotal())).append("\n");
            
            detail.setText(sb.toString());
            dlg.add(detail, BorderLayout.CENTER);
            
            // Tombol Aksi Berdasarkan Status
            if (myOrder.getStatus().equals("Selesai Dimasak")) {
                Button btnPay = new Button("Bayar Sekarang");
                styleGoldButton(btnPay);
                
                Pesanan finalP = myOrder;
                btnPay.addActionListener(e -> {
                    dlg.setVisible(false);
                    processCustomerPayment(finalP);
                });
                
                dlg.add(btnPay, BorderLayout.SOUTH);
            } else if (myOrder.getStatus().equals("Menunggu Pembayaran Cash")) {
                Label info = new Label("Silakan bayar tunai di kasir.", Label.CENTER);
                info.setForeground(RED_DARK);
                dlg.add(info, BorderLayout.SOUTH);
            } else {
                Label info = new Label("Pesanan sedang diproses...", Label.CENTER);
                dlg.add(info, BorderLayout.SOUTH);
            }
        }
        
        dlg.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { dlg.dispose(); }
        });
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    private void processCustomerPayment(Pesanan p) {
        Dialog dlg = new Dialog((Frame)app, "Pilih Metode Pembayaran", true);
        dlg.setSize(300, 250);
        dlg.setLayout(new GridLayout(5, 1, 5, 5));
        dlg.setBackground(BG_LIGHT);
        
        Button btnCash = new Button("Cash (Bayar di Kasir)");
        Button btnCard = new Button("Card");
        Button btnQRIS = new Button("QRIS");
        Button btnCancel = new Button("Batal");
        
        styleGoldButton(btnCash);
        styleGoldButton(btnCard);
        styleGoldButton(btnQRIS);
        btnCancel.setBackground(Color.lightGray);
        
        btnCash.addActionListener(e -> {
            p.setStatus("Menunggu Pembayaran Cash");
            showMessage("Info", "Status diperbarui.\nSilakan menuju kasir untuk pembayaran tunai.");
            dlg.dispose();
        });
        
        btnCard.addActionListener(e -> {
            processNonCash(p, new CardPayment());
            dlg.dispose();
        });
        
        btnQRIS.addActionListener(e -> {
            processNonCash(p, new QRISPayment());
            dlg.dispose();
        });
        
        btnCancel.addActionListener(e -> dlg.dispose());
        
        dlg.add(new Label("  Pilih metode pembayaran:", Label.CENTER));
        dlg.add(btnCash);
        dlg.add(btnCard);
        dlg.add(btnQRIS);
        dlg.add(btnCancel);
        
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }
    
    private void processNonCash(Pesanan p, Pembayaran metode) {
        int idTrx = app.getSystem().generateIdTransaksiBaru();
        Transaksi t = new Transaksi(idTrx, p, metode);
        t.konfirmasi(new java.util.Scanner("")); 
        
        if (t.isStatusKonfirmasi()) {
            showMessage("Sukses", "Pembayaran Berhasil!\n" + buildStrukText(t));
        } else {
            showMessage("Gagal", "Pembayaran gagal.");
        }
    }
    
    private String buildStrukText(Transaksi t) {
        return "Total: " + formatRupiah(t.getPesanan().hitungTotal()) + "\nLunas via " + t.getMetodePembayaran().getNamaMetode();
    }

    //helpers
    private Label sectionTitle(String text) {
        Label l = new Label("  " + text);
        l.setFont(new Font("Serif", Font.BOLD, 18));
        l.setForeground(RED_DARK);
        l.setBackground(CARD_BG);
        l.setPreferredSize(new Dimension(100, 45));
        return l;
    }

    private Label categoryLabel(String text) {
        Label l = new Label("  " + text);
        l.setFont(new Font("Serif", Font.BOLD, 14));
        l.setForeground(RED_DARK);
        l.setBackground(CARD_BG);
        return l;
    }

    private Label fieldLabel(String text) {
        Label l = new Label(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(RED_DARK);
        return l;
    }

    private Label sumLabel(String text) {
        Label l = new Label(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(Color.black);
        return l;
    }

    private Label sumValueLabel() {
        Label l = new Label("0", Label.RIGHT);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(RED_DARK);
        return l;
    }

    private Component line() {
        Panel p = new Panel();
        p.setBackground(CARD_BG);
        p.setPreferredSize(new Dimension(1,1));
        return p;
    }

    private Component lineRed() {
        Panel p = new Panel() {
            @Override public void paint(Graphics g) {
                g.setColor(RED_ACCENT);
                g.drawLine(0, 0, getWidth(), 0);
            }
        };
        p.setBackground(CARD_BG);
        p.setPreferredSize(new Dimension(1,6));
        return p;
    }

    private void styleGoldButton(Button b) {
        b.setBackground(GOLD);
        b.setForeground(Color.black);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void showMessage(String title, String msg) {
        Dialog dlg = new Dialog((Frame)app, title, true);
        dlg.setSize(360, 200);
        dlg.setLayout(new BorderLayout());

        TextArea ta = new TextArea(msg);
        ta.setEditable(false);

        Button ok = new Button("OK");
        styleGoldButton(ok);
        ok.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });

        dlg.add(ta, BorderLayout.CENTER);
        dlg.add(ok, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }

    private String formatRupiah(int val) {
        DecimalFormat df = new DecimalFormat("#,###");
        String s = df.format(val).replace(',', '.');
        return "Rp " + s;
    }

    private String parseKategori(String info) {
        if (info == null) return "";
        int open = info.indexOf("Kategori:");
        if (open < 0) return "";
        int comma = info.indexOf(",", open);
        int close = info.indexOf(")", open);
        int end = (comma > 0 ? comma : close);
        if (end < 0) return "";
        return info.substring(open + 9, end).trim();
    }
}