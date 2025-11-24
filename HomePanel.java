package dummyUASLabPBOB_3;

import java.awt.*;

public class HomePanel extends Panel {

    private ScrollPane scrollPane;
    private Panel page;

    private Panel heroSection, aboutSection, featuredSection, contactSection;

    public HomePanel(AppFrame app) {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        Panel navBar = buildNavBar(app);
        add(navBar, BorderLayout.NORTH);

        scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        page = new Panel();
        page.setLayout(new GridBagLayout());
        page.setBackground(Color.white);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0; gbc.weightx=1; gbc.fill=GridBagConstraints.HORIZONTAL;

        heroSection = buildHero(app);
        gbc.gridy=0; page.add(heroSection, gbc);

        aboutSection = buildAbout();
        gbc.gridy=1; page.add(aboutSection, gbc);

        featuredSection = buildFeaturedMenu();
        gbc.gridy=2; page.add(featuredSection, gbc);

        contactSection = buildContact();
        gbc.gridy=3; page.add(contactSection, gbc);

        scrollPane.add(page);
        add(scrollPane, BorderLayout.CENTER);

        validate();
    }

    // ================= NAVBAR =================
    private Panel buildNavBar(AppFrame app){
        Panel navBar = new Panel(new BorderLayout());
        navBar.setBackground(Color.white);
        navBar.setPreferredSize(new Dimension(1000, 70));

        Panel left = new Panel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        left.setBackground(Color.white);

        // logo pakai unicode escape (aman encoding)
        Label logo = new Label("\u9F8D"); 
        logo.setForeground(AppFrame.GOLD);
        logo.setFont(FontLoader.loadNotoSerifSC(28, Font.BOLD));

        Panel brandText = new Panel(new GridLayout(2,1));
        brandText.setBackground(Color.white);
        Label name = new Label("Xing Fu");
        name.setFont(FontLoader.loadNotoSerifSC(20, Font.BOLD));
        name.setForeground(Color.black);

        Label tagline = new Label("Cita Rasa Autentik China");
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tagline.setForeground(Color.gray);

        brandText.add(name);
        brandText.add(tagline);

        left.add(logo);
        left.add(brandText);

        Panel center = new Panel(new FlowLayout(FlowLayout.CENTER, 18, 18));
        center.setBackground(Color.white);

        Button beranda = navBtn("Beranda");
        Button tentang = navBtn("Tentang");
        Button menu = navBtn("Menu");
        Button kontak = navBtn("Kontak");

        beranda.addActionListener(e -> scrollTo(heroSection));
        tentang.addActionListener(e -> scrollTo(aboutSection));
        menu.addActionListener(e -> scrollTo(featuredSection));
        kontak.addActionListener(e -> scrollTo(contactSection));

        center.add(beranda);
        center.add(tentang);
        center.add(menu);
        center.add(kontak);

        Panel right = new Panel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        right.setBackground(Color.white);

        Button loginPegawai = actionBtn("Login Pekerja");
        Button daftarCust = actionBtn("Daftar Customer");

        loginPegawai.addActionListener(e -> app.showPage("EMP_LOGIN"));
        daftarCust.addActionListener(e -> app.showPage("CUST_LOGIN"));

        right.add(loginPegawai);
        right.add(daftarCust);

        navBar.add(left, BorderLayout.WEST);
        navBar.add(center, BorderLayout.CENTER);
        navBar.add(right, BorderLayout.EAST);

        return navBar;
    }

    private Button navBtn(String text){
        Button b = new Button(text);
        b.setBackground(Color.white);
        b.setForeground(Color.black);
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private Button actionBtn(String text){
        Button b = new Button(text);
        b.setBackground(AppFrame.GOLD);
        b.setForeground(Color.black);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ================= HERO =================
    private Panel buildHero(AppFrame app){
    Panel hero = new Panel(new BorderLayout());
    hero.setBackground(AppFrame.RED_DARK);
    hero.setPreferredSize(new Dimension(1000, 520));

    PatternCanvas bg = new PatternCanvas(); // sekarang Panel container

    Panel texts = new Panel(new GridBagLayout());
    texts.setBackground(new Color(0,0,0,0));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10,10,10,10);
    gbc.gridx=0; gbc.gridy=0;

    Label big = new Label("Selamat Datang di Istana Kuliner China", Label.CENTER);
    big.setFont(FontLoader.loadNotoSerifSC(44, Font.BOLD));
    big.setForeground(Color.white);

    Label sub = new Label(
        "Nikmati hidangan tradisional China dengan sentuhan modern dan bahan pilihan terbaik",
        Label.CENTER
    );
    sub.setFont(FontLoader.loadNotoSerifSC(18, Font.PLAIN));
    sub.setForeground(AppFrame.GOLD);

    Button lihatMenu = new Button("Lihat Menu Kami");
    lihatMenu.setBackground(AppFrame.GOLD);
    lihatMenu.setFont(new Font("SansSerif", Font.BOLD, 16));
    lihatMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
    lihatMenu.addActionListener(e -> scrollTo(featuredSection));

    gbc.gridy=0; texts.add(big, gbc);
    gbc.gridy=1; texts.add(sub, gbc);
    gbc.gridy=2; texts.add(lihatMenu, gbc);

    bg.add(texts);              // sekarang valid karena bg Panel
    hero.add(bg, BorderLayout.CENTER);

    return hero;
}


    // ================= ABOUT =================
    private Panel buildAbout(){
        Panel about = new Panel(new GridBagLayout());
        about.setBackground(Color.white);
        about.setPreferredSize(new Dimension(1000, 420));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx=1; gbc.weighty=1;

        Panel leftBox = new Panel(new GridBagLayout()){
            @Override public void paint(Graphics g){
                super.paint(g);
                int w=getWidth(), h=getHeight();
                g.setColor(new Color(218,165,32,120));
                g.drawRoundRect(10,10,w-20,h-20,16,16);
                g.setFont(FontLoader.loadNotoSerifSC(64, Font.BOLD));
                g.setColor(AppFrame.RED_DARK);
                g.drawString("\u5473", w/2-24, h/2+18); // 味
            }
        };
        leftBox.setBackground(new Color(245,245,245));
        leftBox.setPreferredSize(new Dimension(380,280));

        Panel right = new Panel(new GridLayout(5,1,5,5));
        right.setBackground(Color.white);

        Label title = new Label("Tentang Kami");
        title.setFont(FontLoader.loadNotoSerifSC(36, Font.BOLD));

        Label desc1 = new Label("Sejak 1988, kami menghadirkan pengalaman kuliner China autentik");
        Label desc2 = new Label("dengan resep turun-temurun dari generasi ke generasi.");
        Label desc3 = new Label("Setiap hidangan dibuat oleh chef berpengalaman menggunakan bahan premium.");

        for (Label l : new Label[]{desc1,desc2,desc3}){
            l.setFont(new Font("SansSerif", Font.PLAIN, 15));
            l.setForeground(Color.darkGray);
        }

        Panel badges = new Panel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        badges.setBackground(Color.white);
        badges.add(badge("CHOP", "Autentik"));
        badges.add(badge("CHEF", "Chef Ahli"));
        badges.add(badge("STAR", "Premium"));

        right.add(title);
        right.add(desc1);
        right.add(desc2);
        right.add(desc3);
        right.add(badges);

        gbc.gridx=0; gbc.gridy=0;
        about.add(leftBox, gbc);
        gbc.gridx=1;
        about.add(right, gbc);

        return about;
    }

    private Panel badge(String icon, String text){
        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER,8,8));
        p.setBackground(AppFrame.RED_DARK);
        p.setPreferredSize(new Dimension(140,90));

        Label i = new Label(icon);
        i.setFont(new Font("SansSerif", Font.BOLD, 16));
        i.setForeground(Color.white);

        Label t = new Label(text);
        t.setFont(new Font("SansSerif", Font.BOLD, 14));
        t.setForeground(Color.white);

        p.add(i); p.add(t);
        return p;
    }

    // ================= FEATURED MENU =================
    private Panel buildFeaturedMenu(){
        Panel featured = new Panel(new BorderLayout());
        featured.setBackground(AppFrame.RED_DARK);
        featured.setPreferredSize(new Dimension(1000, 520));

        Label title = new Label("Menu Unggulan Kami", Label.CENTER);
        title.setFont(FontLoader.loadNotoSerifSC(38, Font.BOLD));
        title.setForeground(Color.white);

        Panel cards = new Panel(new FlowLayout(FlowLayout.CENTER, 24, 40));
        cards.setBackground(AppFrame.RED_DARK);

        cards.add(new MenuCard("Mapo Tofu","Mapo Tofu",
                "Tofu pilihan dengan resep turun temurun","Rp 35.000"));
        cards.add(new MenuCard("Hot Pot","Hot Pot",
                "Hot Pot dengan kualitas premium","Rp 45.000"));
        cards.add(new MenuCard("DUCK","Bebek Peking Klasik",
                "Bebek panggang renyah dengan saus plum khas","Rp 185.000"));

        featured.add(title, BorderLayout.NORTH);
        featured.add(cards, BorderLayout.CENTER);

        return featured;
    }

    // ================= CONTACT =================
    private Panel buildContact(){
        Panel contact = new Panel(new GridBagLayout());
        contact.setBackground(Color.white);
        contact.setPreferredSize(new Dimension(1000, 380));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(18,18,18,18);

        Label title = new Label("Kunjungi Kami", Label.CENTER);
        title.setFont(FontLoader.loadNotoSerifSC(36, Font.BOLD));

        Panel boxes = new Panel(new FlowLayout(FlowLayout.CENTER, 18, 20));
        boxes.setBackground(Color.white);
        boxes.add(infoBox("MAP","Alamat","Jl. Kuliner No. 888, Banda Aceh"));
        boxes.add(infoBox("PHONE","Telepon","(021) 8888-8888"));
        boxes.add(infoBox("CLOCK","Jam Buka","Setiap Hari: 10:00 - 22:00"));

        gbc.gridx=0; gbc.gridy=0;
        contact.add(title, gbc);
        gbc.gridy=1;
        contact.add(boxes, gbc);

        return contact;
    }

    private Panel infoBox(String icon, String title, String text){
        Panel p = new Panel(new GridLayout(3,1));
        p.setBackground(AppFrame.RED_DARK);
        p.setPreferredSize(new Dimension(220,140));

        Label i = new Label(icon, Label.CENTER);
        i.setFont(new Font("SansSerif", Font.BOLD, 14));
        i.setForeground(Color.white);

        Label t = new Label(title, Label.CENTER);
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.setForeground(Color.white);

        Label d = new Label(text, Label.CENTER);
        d.setFont(new Font("SansSerif", Font.PLAIN, 13));
        d.setForeground(Color.white);

        p.add(i); p.add(t); p.add(d);
        return p;
    }

    // ================= SCROLL HELPER =================
    private void scrollTo(Component comp){
        if (comp == null) return;
        Point p = comp.getLocation();
        scrollPane.setScrollPosition(0, p.y);
    }
}
