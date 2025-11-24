package dummyUASLabPBOB_3;

import java.awt.*;

public class CustomerLoginRegisterPanel extends Panel {
    
    private final AppFrame app;
    private Label lblStatus;

    public CustomerLoginRegisterPanel(AppFrame app) {
        this.app = app;
        setLayout(new BorderLayout());
        setBackground(AppFrame.RED_DARK);

        //header
        Label header = new Label("Selamat Datang Pelanggan", Label.CENTER);
        header.setFont(new Font("Serif", Font.BOLD, 32));
        header.setForeground(AppFrame.GOLD);
        header.setPreferredSize(new Dimension(1000, 80));
        add(header, BorderLayout.NORTH);

        Panel center = new Panel(new GridLayout(1, 2, 20, 0)); // 1 Baris, 2 Kolom, Jarak 20px
        center.setBackground(AppFrame.RED_DARK);
        
        Panel wrapper = new Panel(new BorderLayout());
        wrapper.setBackground(AppFrame.RED_DARK);
        Panel paddingBox = new Panel(new GridLayout(1, 2, 40, 0)) {
            public Insets getInsets() {
                return new Insets(20, 40, 20, 40);
            }
            
        };

        paddingBox.setBackground(AppFrame.RED_DARK);

        paddingBox.add(buildLoginBox());
        paddingBox.add(buildRegisterBox());

        wrapper.add(paddingBox, BorderLayout.CENTER);
        add(wrapper, BorderLayout.CENTER);

        Panel footer = new Panel(new BorderLayout());
        footer.setBackground(AppFrame.RED_DARK);
        footer.setPreferredSize(new Dimension(1000, 60));
        
        lblStatus = new Label("Silakan Login atau Daftar akun baru", Label.CENTER);
        lblStatus.setForeground(Color.white);
        lblStatus.setFont(new Font("SansSerif", Font.ITALIC, 14));
        
        Button btnBack = new Button("Kembali ke Beranda");
        styleButton(btnBack, false); 
        btnBack.addActionListener(e -> app.showPage("HOME"));
        
        Panel btnBox = new Panel(new FlowLayout());
        btnBox.setBackground(AppFrame.RED_DARK);
        btnBox.add(btnBack);

        footer.add(lblStatus, BorderLayout.CENTER);
        footer.add(btnBox, BorderLayout.SOUTH);
        
        add(footer, BorderLayout.SOUTH);
    }

    // box login
    private Panel buildLoginBox() {
        Panel p = new Panel(new GridBagLayout());
        p.setBackground(Color.white); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        Label title = new Label("LOGIN", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(AppFrame.RED_DARK);
        gbc.gridwidth = 2;
        p.add(title, gbc);

        gbc.gridy++;
        Label sub = new Label("Sudah punya akun? Masuk di sini.", Label.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        p.add(sub, gbc);

        gbc.gridy++; gbc.gridwidth = 1;
        p.add(new Label("ID Customer:"), gbc);
        
        gbc.gridx = 1;
        TextField tfId = new TextField(15);
        p.add(tfId, gbc);

        gbc.gridx = 0; gbc.gridy++;
        p.add(new Label("Password:"), gbc);
        
        gbc.gridx = 1;
        TextField tfPass = new TextField(15);
        tfPass.setEchoChar('*');
        p.add(tfPass, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5); 
        Button btnLogin = new Button("MASUK");
        styleButton(btnLogin, true); 
        p.add(btnLogin, gbc);

        //logic login
        btnLogin.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tfId.getText().trim());
                String pass = tfPass.getText().trim();
                
                Akun akun = app.getSystem().login(id, pass);
                
                if (akun instanceof Customer) {
                    app.setCurrentCustomer((Customer) akun);
                    lblStatus.setText("Login Berhasil!");
                    tfId.setText(""); tfPass.setText(""); // Reset form
                    app.showPage("CUSTOMER");
                } else {
                    lblStatus.setText("Gagal: ID atau Password salah.");
                }
            } catch (NumberFormatException ex) {
                lblStatus.setText("Error: ID harus berupa angka!");
            } catch (Exception ex) {
                lblStatus.setText("Error: " + ex.getMessage());
            }
        });

        return p;
    }

    // box register
    private Panel buildRegisterBox() {
        Panel p = new Panel(new GridBagLayout());
        p.setBackground(new Color(255, 250, 240)); // Warna agak krem dikit beda
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        Label title = new Label("DAFTAR BARU", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(AppFrame.RED_DARK);
        gbc.gridwidth = 2;
        p.add(title, gbc);


        gbc.gridy++;
        Label sub = new Label("Belum punya akun? Buat sekarang.", Label.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        p.add(sub, gbc);

        //input nama
        gbc.gridy++; gbc.gridwidth = 1;
        p.add(new Label("Nama Kamu:"), gbc);
        
        gbc.gridx = 1;
        TextField tfNama = new TextField(15);
        p.add(tfNama, gbc);

        // Input Pass
        gbc.gridx = 0; gbc.gridy++;
        p.add(new Label("Buat Password:"), gbc);
        
        gbc.gridx = 1;
        TextField tfPass = new TextField(15);
        tfPass.setEchoChar('*'); //sensor password
        p.add(tfPass, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        Button btnReg = new Button("DAFTAR SEKARANG");
        styleButton(btnReg, true);
        p.add(btnReg, gbc);

        //logic register
        btnReg.addActionListener(e -> {
            String nama = tfNama.getText().trim();
            String pass = tfPass.getText().trim();

            if (nama.isEmpty() || pass.isEmpty()) {
                lblStatus.setText("Nama dan Password tidak boleh kosong!");
                return;
            }

            try {
                Customer c = app.getSystem().registerCustomer(nama, pass);
                
                showSuccessDialog("Pendaftaran Berhasil!", 
                        "Halo " + c.getNama() + "!\n\n" +
                        "ID Login kamu adalah: " + c.getId() + "\n" +
                        "Password: " + c.getPassword() + "\n\n" +
                        "Harap diingat untuk login selanjutnya.");
                
                tfNama.setText(""); tfPass.setText(""); //reset form
                lblStatus.setText("Akun berhasil dibuat. Silakan login di kotak kiri.");
                
            } catch (Exception ex) {
                lblStatus.setText("Gagal daftar: " + ex.getMessage());
            }
        });

        return p;
    }

    // Helper: Styling Button
    private void styleButton(Button b, boolean primary) {
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (primary) {
            b.setBackground(AppFrame.GOLD);
            b.setForeground(Color.black);
        } else {
            b.setBackground(Color.white);
            b.setForeground(AppFrame.RED_DARK);
        }
    }

    // Helper: Dialog Sukses 
    private void showSuccessDialog(String title, String msg) {
        Dialog dlg = new Dialog((Frame)app, title, true);
        dlg.setSize(350, 250);
        dlg.setLayout(new BorderLayout());
        
        TextArea ta = new TextArea(msg);
        ta.setEditable(false);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        Button ok = new Button("Saya Mengerti");
        styleButton(ok, true);
        ok.addActionListener(e -> { dlg.setVisible(false); dlg.dispose(); });
        
        dlg.add(ta, BorderLayout.CENTER);
        dlg.add(ok, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(app);
        dlg.setVisible(true);
    }
}