package dummyUASLabPBOB_3;

import java.awt.*;
import java.awt.event.*;

public class HomePanel extends Panel {

    private ScrollPane scrollPane;
    private Panel page;
    private Panel heroSection, aboutSection, featuredSection, contactSection;
    
    private static final Color CREAM = new Color(255, 250, 240);

    public HomePanel(AppFrame app) {
        setLayout(new BorderLayout());
        setBackground(CREAM);

        Panel navBar = buildNavBar(app);
        add(navBar, BorderLayout.NORTH);

        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        page = new Panel();
        page.setLayout(new GridBagLayout());
        page.setBackground(CREAM);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        heroSection = buildHero(app);
        gbc.gridy = 0;
        page.add(heroSection, gbc);

        aboutSection = buildAbout();
        gbc.gridy = 1;
        page.add(aboutSection, gbc);

        featuredSection = buildFeaturedMenu();
        gbc.gridy = 2;
        page.add(featuredSection, gbc);

        contactSection = buildContact();
        gbc.gridy = 3;
        page.add(contactSection, gbc);

        Panel footer = buildFooter();
        gbc.gridy = 4;
        page.add(footer, gbc);

        scrollPane.add(page);
        add(scrollPane, BorderLayout.CENTER);

        validate();
    }

    // navbar
    private Panel buildNavBar(AppFrame app) {
        Panel navBar = new Panel(new BorderLayout()) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(new Color(0, 0, 0, 30));
                g.fillRect(0, getHeight() - 2, getWidth(), 2);
            }
        };
        navBar.setBackground(Color.white);
        navBar.setPreferredSize(new Dimension(1000, 75));

        Panel left = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        left.setBackground(Color.white);

        Label logo = new Label("\u9F8D");
        logo.setForeground(AppFrame.GOLD);
        logo.setFont(FontLoader.loadNotoSerifSC(32, Font.BOLD));

        Panel brandText = new Panel(new GridLayout(2, 1, 0, 2));
        brandText.setBackground(Color.white);
        
        Label name = new Label("Xing Fu");
        name.setFont(FontLoader.loadNotoSerifSC(22, Font.BOLD));
        name.setForeground(AppFrame.RED_DARK);

        Label tagline = new Label("\u5E78\u798F \u2022 Cita Rasa Autentik China");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 11));
        tagline.setForeground(new Color(120, 120, 120));

        brandText.add(name);
        brandText.add(tagline);

        left.add(logo);
        left.add(brandText);

        Panel center = new Panel(new FlowLayout(FlowLayout.CENTER, 25, 22));
        center.setBackground(Color.white);

        Button beranda = createNavButton("Beranda");
        Button tentang = createNavButton("Tentang");
        Button menu = createNavButton("Menu");
        Button kontak = createNavButton("Kontak");

        beranda.addActionListener(e -> smoothScrollTo(heroSection));
        tentang.addActionListener(e -> smoothScrollTo(aboutSection));
        menu.addActionListener(e -> smoothScrollTo(featuredSection));
        kontak.addActionListener(e -> smoothScrollTo(contactSection));

        center.add(beranda);
        center.add(tentang);
        center.add(menu);
        center.add(kontak);

        Panel right = new Panel(new FlowLayout(FlowLayout.RIGHT, 15, 16));
        right.setBackground(Color.white);

        Button loginPegawai = createActionButton("Login Pekerja", AppFrame.RED_DARK);
        Button daftarCust = createActionButton("Daftar Customer", AppFrame.GOLD);

        loginPegawai.addActionListener(e -> app.showPage("EMP_LOGIN"));
        daftarCust.addActionListener(e -> app.showPage("CUST_LOGIN"));

        right.add(loginPegawai);
        right.add(daftarCust);

        navBar.add(left, BorderLayout.WEST);
        navBar.add(center, BorderLayout.CENTER);
        navBar.add(right, BorderLayout.EAST);

        return navBar;
    }

    private Button createNavButton(String text) {
        Button b = new Button(text);
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setForeground(AppFrame.RED_DARK);
            }
            public void mouseExited(MouseEvent e) {
                b.setForeground(Color.black);
            }
        });
        
        return b;
    }

    private Button createActionButton(String text, Color bgColor) {
        Button b = new Button(text);
        b.setBackground(bgColor);
        b.setForeground(Color.white);
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(bgColor);
            }
        });
        
        return b;
    }

    private Panel buildHero(AppFrame app) {
        Panel hero = new Panel(new BorderLayout());
        hero.setBackground(AppFrame.RED_DARK);
        hero.setPreferredSize(new Dimension(1000, 550));

        PatternCanvas bg = new PatternCanvas();

        Panel overlay = new Panel(new GridBagLayout());
        overlay.setBackground(new Color(0, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.gridx = 0;

        Label big = new Label("Selamat Datang di Istana Kuliner China", Label.CENTER);
        big.setFont(FontLoader.loadNotoSerifSC(48, Font.BOLD));
        big.setForeground(Color.white);

        Label sub = new Label(
            "Nikmati hidangan tradisional China dengan sentuhan modern dan bahan pilihan terbaik",
            Label.CENTER
        );
        sub.setFont(FontLoader.loadNotoSerifSC(19, Font.PLAIN));
        sub.setForeground(AppFrame.GOLD);

        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        Button lihatMenu = createHeroButton("Lihat Menu Kami", AppFrame.GOLD);
        lihatMenu.addActionListener(e -> smoothScrollTo(featuredSection));

        Button tentangKami = createHeroButton("Tentang Kami", Color.white);
        tentangKami.setForeground(AppFrame.RED_DARK);
        tentangKami.addActionListener(e -> smoothScrollTo(aboutSection));

        buttonPanel.add(lihatMenu);
        buttonPanel.add(tentangKami);

        gbc.gridy = 0;
        overlay.add(big, gbc);
        gbc.gridy = 1;
        overlay.add(sub, gbc);
        gbc.gridy = 2;
        overlay.add(buttonPanel, gbc);

        bg.add(overlay);
        hero.add(bg, BorderLayout.CENTER);

        return hero;
    }

    private Button createHeroButton(String text, Color bgColor) {
        Button b = new Button(text);
        b.setBackground(bgColor);
        b.setForeground(Color.black);
        b.setFont(new Font("SansSerif", Font.BOLD, 16));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(bgColor);
            }
        });
        
        return b;
    }

    //about
    private Panel buildAbout() {
        Panel about = new Panel(new BorderLayout());
        about.setBackground(CREAM);
        about.setPreferredSize(new Dimension(1000, 500));

        Panel container = new Panel(new GridBagLayout());
        container.setBackground(CREAM);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 40, 40, 40);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.45;
        gbc.weighty = 1;

        Panel leftBox = new Panel(new GridBagLayout()) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                int w = getWidth(), h = getHeight();
                
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(220, 170, 40, 40), 
                                                      w, h, new Color(180, 30, 30, 40));
                g2d.setPaint(gp);
                g2d.fillRoundRect(5, 5, w - 10, h - 10, 20, 20);
                
                g.setColor(AppFrame.GOLD);
                g.drawRoundRect(10, 10, w - 20, h - 20, 18, 18);
                g.drawRoundRect(12, 12, w - 24, h - 24, 16, 16);
                
                g.setFont(FontLoader.loadNotoSerifSC(72, Font.BOLD));
                g.setColor(AppFrame.RED_DARK);
                FontMetrics fm = g.getFontMetrics();
                String ch = "\u5473";
                int chW = fm.stringWidth(ch);
                g.drawString(ch, (w - chW) / 2, h / 2 + 20);
            }
        };
        leftBox.setBackground(new Color(248, 245, 240));
        leftBox.setPreferredSize(new Dimension(350, 350));
        leftBox.setMinimumSize(new Dimension(350, 350));
        leftBox.setMaximumSize(new Dimension(350, 350));

        Panel right = new Panel(new GridBagLayout());
        right.setBackground(CREAM);
        right.setPreferredSize(new Dimension(450, 350));
        right.setMinimumSize(new Dimension(450, 350));

        GridBagConstraints rgbc = new GridBagConstraints();
        rgbc.gridx = 0;
        rgbc.anchor = GridBagConstraints.WEST;
        rgbc.fill = GridBagConstraints.HORIZONTAL;
        rgbc.weightx = 1;

        Label title = new Label("Tentang Kami");
        title.setFont(FontLoader.loadNotoSerifSC(40, Font.BOLD));
        title.setForeground(AppFrame.RED_DARK);

        Label subtitle = new Label("\u2014 Warisan Kuliner Sejak 1988 \u2014");
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subtitle.setForeground(AppFrame.GOLD);

        Label desc1 = new Label("Sejak 1988, kami menghadirkan pengalaman kuliner China autentik");
        Label desc2 = new Label("dengan resep turun-temurun dari generasi ke generasi.");
        Label desc3 = new Label("Setiap hidangan dibuat oleh chef berpengalaman menggunakan");
        Label desc4 = new Label("bahan premium pilihan dan teknik memasak tradisional.");

        for (Label l : new Label[]{desc1, desc2, desc3, desc4}) {
            l.setFont(new Font("SansSerif", Font.PLAIN, 15));
            l.setForeground(new Color(60, 60, 60));
        }

        Panel badges = new Panel(new GridLayout(1, 3, 15, 0));
        badges.setBackground(CREAM);
        badges.setPreferredSize(new Dimension(450, 100));
        badges.add(createBadge("\u2756", "Autentik"));
        badges.add(createBadge("\u2605", "Chef Ahli"));
        badges.add(createBadge("\u2726", "Premium"));

        rgbc.gridy = 0;
        rgbc.insets = new Insets(0, 0, 10, 0);
        right.add(title, rgbc);
        
        rgbc.gridy = 1;
        rgbc.insets = new Insets(0, 0, 20, 0);
        right.add(subtitle, rgbc);
        
        rgbc.gridy = 2;
        rgbc.insets = new Insets(0, 0, 5, 0);
        right.add(desc1, rgbc);
        
        rgbc.gridy = 3;
        right.add(desc2, rgbc);
        
        rgbc.gridy = 4;
        right.add(desc3, rgbc);
        
        rgbc.gridy = 5;
        rgbc.insets = new Insets(0, 0, 25, 0);
        right.add(desc4, rgbc);
        
        rgbc.gridy = 6;
        rgbc.insets = new Insets(0, 0, 0, 0);
        right.add(badges, rgbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(leftBox, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.55;
        container.add(right, gbc);

        about.add(container, BorderLayout.CENTER);

        return about;
    }

    private Panel createBadge(String icon, String text) {
        Panel p = new Panel(new GridBagLayout());
        p.setBackground(AppFrame.RED_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        Label i = new Label(icon, Label.CENTER);
        i.setFont(new Font("SansSerif", Font.BOLD, 28));
        i.setForeground(Color.white);

        Label t = new Label(text, Label.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 14));
        t.setForeground(Color.white);

        gbc.gridy = 0;
        p.add(i, gbc);
        gbc.gridy = 1;
        p.add(t, gbc);

        return p;
    }

    // ================= FEATURED MENU =================
    private Panel buildFeaturedMenu() {
        Panel featured = new Panel(new BorderLayout());
        featured.setBackground(AppFrame.RED_DARK);
        featured.setPreferredSize(new Dimension(1000, 580));

        Panel header = new Panel(new GridBagLayout());
        header.setBackground(AppFrame.RED_DARK);

        GridBagConstraints hgbc = new GridBagConstraints();
        hgbc.gridx = 0;
        hgbc.insets = new Insets(30, 20, 10, 20);

        Label title = new Label("Menu Unggulan Kami", Label.CENTER);
        title.setFont(FontLoader.loadNotoSerifSC(42, Font.BOLD));
        title.setForeground(Color.white);

        Label subtitle = new Label("\u2014 Hidangan Terpopuler dengan Cita Rasa Istimewa \u2014", Label.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 15));
        subtitle.setForeground(AppFrame.GOLD);

        hgbc.gridy = 0;
        header.add(title, hgbc);
        hgbc.gridy = 1;
        header.add(subtitle, hgbc);

        Panel cards = new Panel(new FlowLayout(FlowLayout.CENTER, 28, 50));
        cards.setBackground(AppFrame.RED_DARK);

        cards.add(new MenuCard("Mapo Tofu", "Mapo Tofu",
                "Tofu lembut dengan saus pedas khas Sichuan", "Rp 35.000"));
        cards.add(new MenuCard("Hot Pot", "Hot Pot Premium",
                "Hot Pot dengan kuah kaldu spesial dan bahan berkualitas", "Rp 45.000"));
        cards.add(new MenuCard("DUCK", "Bebek Peking Klasik",
                "Bebek panggang renyah dengan saus plum khas Beijing", "Rp 185.000"));

        featured.add(header, BorderLayout.NORTH);
        featured.add(cards, BorderLayout.CENTER);

        return featured;
    }

    // contact
    private Panel buildContact() {
        Panel contact = new Panel(new BorderLayout());
        contact.setBackground(CREAM);
        contact.setPreferredSize(new Dimension(1000, 450));

        Panel content = new Panel(new GridBagLayout());
        content.setBackground(CREAM);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(30, 20, 20, 20);

        Label title = new Label("Kunjungi Kami", Label.CENTER);
        title.setFont(FontLoader.loadNotoSerifSC(40, Font.BOLD));
        title.setForeground(AppFrame.RED_DARK);

        Label subtitle = new Label("\u2014 Kami Menanti Kedatangan Anda \u2014", Label.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subtitle.setForeground(AppFrame.GOLD);

        Panel boxes = new Panel(new GridLayout(1, 3, 30, 0));
        boxes.setBackground(CREAM);
        boxes.setPreferredSize(new Dimension(850, 180));
        
        boxes.add(createInfoBox("\u25CF", "Alamat", "Jl. Kuliner No. 888\nBanda Aceh"));
        boxes.add(createInfoBox("\u260E", "Telepon", "(021) 8888-8888\nWhatsApp Ready"));
        boxes.add(createInfoBox("\u25D4", "Jam Buka", "Setiap Hari\n10:00 - 22:00"));

        gbc.gridy = 0;
        content.add(title, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 20, 30, 20);
        content.add(subtitle, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 30, 20);
        content.add(boxes, gbc);

        contact.add(content, BorderLayout.CENTER);

        return contact;
    }

    private Panel createInfoBox(String icon, String title, String text) {
        Panel p = new Panel(new GridBagLayout()) {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(new Color(0, 0, 0, 20));
                g.fillRoundRect(5, 5, getWidth() - 5, getHeight() - 5, 15, 15);
            }
        };
        p.setBackground(AppFrame.RED_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 15, 15, 15);

        Label i = new Label(icon, Label.CENTER);
        i.setFont(new Font("SansSerif", Font.BOLD, 36));
        i.setForeground(Color.white);

        Label t = new Label(title, Label.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 17));
        t.setForeground(AppFrame.GOLD);

        String[] lines = text.split("\n");
        Panel textPanel = new Panel(new GridLayout(lines.length, 1, 0, 5));
        textPanel.setBackground(AppFrame.RED_DARK);
        
        for (String line : lines) {
            Label d = new Label(line, Label.CENTER);
            d.setFont(new Font("SansSerif", Font.PLAIN, 14));
            d.setForeground(Color.white);
            textPanel.add(d);
        }

        gbc.gridy = 0;
        p.add(i, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 15, 10, 15);
        p.add(t, gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 15, 15, 15);
        p.add(textPanel, gbc);

        return p;
    }

    //footer
    private Panel buildFooter() {
        Panel footer = new Panel(new GridBagLayout());
        footer.setBackground(new Color(40, 40, 40));
        footer.setPreferredSize(new Dimension(1000, 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        Label copy = new Label("\u00A9 2024 Xing Fu Restaurant. All Rights Reserved.", Label.CENTER);
        copy.setFont(new Font("SansSerif", Font.PLAIN, 12));
        copy.setForeground(Color.lightGray);

        Label motto = new Label("\u2726 Membawa Cita Rasa Autentik China ke Meja Anda \u2726", Label.CENTER);
        motto.setFont(new Font("SansSerif", Font.ITALIC, 11));
        motto.setForeground(AppFrame.GOLD);

        gbc.gridy = 0;
        footer.add(copy, gbc);
        gbc.gridy = 1;
        footer.add(motto, gbc);

        return footer;
    }

    // scroll
    private void smoothScrollTo(Component comp) {
        if (comp == null) return;
        Point p = comp.getLocation();
        scrollPane.setScrollPosition(0, p.y);
    }
}