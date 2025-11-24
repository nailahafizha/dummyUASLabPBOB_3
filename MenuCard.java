package dummyUASLabPBOB_3;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuCard extends Panel {

    private final Color baseBg = new Color(160, 0, 0);   // slightly lighter maroon
    private final Color hoverBg = new Color(178, 34, 34);
    private final Color borderBase = new Color(255, 255, 255, 50);
    private final Color borderHover = AppFrame.GOLD;

    private boolean isHover = false;

    public MenuCard(String icon, String title, String desc, String price) {
        setLayout(new BorderLayout());
        setBackground(baseBg);

        // top icon circle
        Panel top = new Panel(new GridBagLayout());
        top.setBackground(baseBg);
        Label iconLbl = new Label(icon, Label.CENTER);
        iconLbl.setFont(new Font("SansSerif", Font.PLAIN, 48));
        iconLbl.setForeground(Color.white);
        top.add(iconLbl);

        // body
        Panel body = new Panel(new GridLayout(3,1));
        body.setBackground(Color.white);

        Label t = new Label(title);
        t.setFont(FontLoader.loadNotoSerifSC(20, Font.BOLD));
        Label d = new Label(desc);
        d.setFont(new Font("SansSerif", Font.PLAIN, 14));
        d.setForeground(Color.darkGray);
        Label p = new Label(price);
        p.setFont(new Font("SansSerif", Font.BOLD, 18));
        p.setForeground(AppFrame.GOLD);

        body.add(wrapLeft(t));
        body.add(wrapLeft(d));
        body.add(wrapLeft(p));

        add(top, BorderLayout.CENTER);
        add(body, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(260, 300));

        addMouseListener(new MouseAdapter(){
            @Override public void mouseEntered(MouseEvent e){
                isHover = true;
                setBackground(hoverBg);
                top.setBackground(hoverBg);
                repaint();
            }
            @Override public void mouseExited(MouseEvent e){
                isHover = false;
                setBackground(baseBg);
                top.setBackground(baseBg);
                repaint();
            }
        });
    }

    private Panel wrapLeft(Component c){
        Panel p = new Panel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(Color.white);
        p.add(c);
        return p;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // border rounded illusion
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(isHover ? borderHover : borderBase);
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,18,18);
    }
}
