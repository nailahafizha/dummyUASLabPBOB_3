package dummyUASLabPBOB_3;

import java.awt.*;
import java.awt.event.*;

public class AppFrame extends Frame implements WindowListener {
    private final RestaurantSystem system;

    private CardLayout cardLayout;
    private Panel cardPanel;

    private Pegawai currentPegawai;
    private Customer currentCustomer;

    public static final Color RED_DARK = new Color(139, 0, 0);
    public static final Color RED = new Color(178, 34, 34);
    public static final Color GOLD = new Color(218, 165, 32);
    public static final Color WHITE = Color.white;

    public AppFrame(RestaurantSystem system) {
        super("Xing Fu Restaurant");
        this.system = system;

        setSize(1000, 650);
        setLayout(new BorderLayout());
        setBackground(RED_DARK);
        addWindowListener(this);

        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);

        cardPanel.add(new HomePanel(this), "HOME");
        cardPanel.add(new EmployeeLoginPanel(this), "EMP_LOGIN");
        cardPanel.add(new CustomerLoginRegisterPanel(this), "CUST_LOGIN");
        cardPanel.add(new WaiterPanel(this), "WAITER");
        cardPanel.add(new ChefPanel(this), "CHEF");
        cardPanel.add(new CashierPanel(this), "CASHIER");
        cardPanel.add(new CustomerPanel(this), "CUSTOMER");

        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
        showPage("HOME");
    }

    public RestaurantSystem getSystem() { return system; }
    public void showPage(String key) { cardLayout.show(cardPanel, key); }

    public void setCurrentPegawai(Pegawai p) {
        currentPegawai = p;
        currentCustomer = null;
    }
    public void setCurrentCustomer(Customer c) {
        currentCustomer = c;
        currentPegawai = null;
    }

    public Pegawai getCurrentPegawai() { return currentPegawai; }
    public Customer getCurrentCustomer() { return currentCustomer; }

    public void windowClosing(WindowEvent e) { dispose(); System.exit(0); }
    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
