package UI;

import BL.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Martin, Anthony
 */
public class MainView extends JFrame {

    private JPanel main;
    private MainEngine me;
    private javax.swing.JButton btnSearch, btnClear;
    private javax.swing.JLabel lblCount;
    private javax.swing.JTextField txtQuery;
    private javax.swing.JScrollPane scpList;
    private javax.swing.JList lstResults;
    private javax.swing.JRadioButton rbtnOp1, rbtnOp2, rbtnOp3, rbtnOp4;
    private javax.swing.ButtonGroup rbtnGroup;
    private javax.swing.JCheckBox cbtnOp1, cbtnOp2;
    private javax.swing.JComboBox cbbxLimits;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem exitMenuItem, openMenuItem;
    private Dimension searchBarSize, buttonSize, listSize, statusBarSize;
    /**
     * ActionListener for the Search and Clear buttons. It also contains the
     * action for the Exit menu option from the MenuBar.
     */
    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnSearch)) {
                startSearch();
            }
            if (e.getSource().equals(btnClear)) {
                clearQuery();
                clearResults();
                btnClear.setEnabled(false);
                btnSearch.setEnabled(false);
            }
            if (e.getSource().equals(exitMenuItem)) {
                System.exit(0);
            }
        }
    };
    /**
     * KeyListener for the search/query bar.
     */
    KeyAdapter queryListener = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            if (getInstantSearch() && txtQuery.getText().length() >= 3 && btnSearch.isEnabled()) {
                startSearch();
            }
            if (txtQuery.getText().length() > 0) {
                btnSearch.setEnabled(true);
                btnClear.setEnabled(true);
            } else {
                btnSearch.setEnabled(false);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER && btnSearch.isEnabled()) {
                startSearch();
                e.consume();
            }
            if (key == KeyEvent.VK_BACK_SPACE && btnClear.isEnabled()) {
                clearResults();
            }
        }
    };
    /**
     * MouseListener for the click-and-clear feature on the search/query bar.
     */
    MouseAdapter clickListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == txtQuery) {
                txtQuery.setText("");
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource().equals(txtQuery)) {
                txtQuery.setFocusable(true);
            }
        }
    };

    /**
     * Constructor for the class. Creates the main panel, initializes the
     * components and sets the layout and theme of the frame.
     */
    public MainView() {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        init();
        main = getBorderLayout();
        this.add(main);
        this.setJMenuBar(menuBar);
    }

    /**
     * Initializes the components and the business logic. Warning: It's pretty
     * long. But on the bright side, it's organized at least. :-)
     */
    private void init() {
        // If there's a problem with the loaded file, a windows will pop up and warn the user about it.
        try {
            me = new MainEngine();
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        ///////// DIMENSIONS /////////
        searchBarSize = new Dimension(260, 25);
        buttonSize = new Dimension(75, 25);
        listSize = new Dimension(260, 315);
        statusBarSize = new Dimension(this.getWidth(), 16);
        //////////////////////////////

        ////////  TEXT FIELDS  ///////
        txtQuery = new javax.swing.JTextField();
        txtQuery.setText("Search here...");
        txtQuery.setFocusable(false);
        txtQuery.setToolTipText("Enter your search query here");
        txtQuery.setPreferredSize(searchBarSize);
        txtQuery.addKeyListener(queryListener);
        txtQuery.addMouseListener(clickListener);
        //////////////////////////////

        //////////  BUTTONS  /////////
        btnSearch = new javax.swing.JButton("Search");
        btnClear = new javax.swing.JButton("Clear");
        btnSearch.setPreferredSize(buttonSize);
        btnClear.setPreferredSize(buttonSize);
        btnSearch.addActionListener(buttonListener);
        btnSearch.setEnabled(false);
        btnClear.addActionListener(buttonListener);
        btnClear.setEnabled(false);
        //////////////////////////////

        /////// LIST COMPONENTS //////
        scpList = new javax.swing.JScrollPane();
        lstResults = new javax.swing.JList();
        scpList.setViewportView(lstResults);
        scpList.setPreferredSize(listSize);
        //////////////////////////////

        //////// RADIO BUTTONS ///////
        rbtnGroup = new javax.swing.ButtonGroup();
        rbtnOp1 = new javax.swing.JRadioButton("Contains");
        rbtnOp2 = new javax.swing.JRadioButton("Begins With");
        rbtnOp3 = new javax.swing.JRadioButton("Ends With");
        rbtnOp4 = new javax.swing.JRadioButton("Exact");
        rbtnGroup.add(rbtnOp1);
        rbtnGroup.add(rbtnOp2);
        rbtnGroup.add(rbtnOp3);
        rbtnGroup.add(rbtnOp4);
        rbtnOp1.setSelected(true);
        //////////////////////////////

        ///////// CHECK BOXES ////////
        cbtnOp1 = new javax.swing.JCheckBox("Case sensitive");
        cbtnOp2 = new javax.swing.JCheckBox("Instant search");
        cbtnOp2.setSelected(true);
        //////////////////////////////

        //////// COMBO BOXES /////////
        cbbxLimits = new javax.swing.JComboBox();
        cbbxLimits.setModel(new javax.swing.DefaultComboBoxModel(
                new String[]{"None", "50", "100", "250", "500"}));
        //////////////////////////////

        //////////  LABELS  //////////
        lblCount = new javax.swing.JLabel("Count: " + lstResults.getModel().getSize());
        //////////////////////////////

        ///////////  MENU  ///////////
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        openMenuItem = new javax.swing.JMenuItem("Open");
        exitMenuItem = new javax.swing.JMenuItem("Exit");
        openMenuItem.setMnemonic(KeyEvent.VK_O);
        exitMenuItem.setMnemonic(KeyEvent.VK_C);
        openMenuItem.setToolTipText("Open file");
        exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener(buttonListener);
        fileMenu.add(openMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        FileChooser fc = new FileChooser(this, openMenuItem); // Creates the 'Open' window frame and the ActionListener.
    }

    /**
     * Creates the main JPanel, using the BorderLayout
     */
    private JPanel getBorderLayout() {
        JPanel l = new JPanel();
        l.setLayout(new BorderLayout());
        l.setBackground(Color.DARK_GRAY);

        l.add(getNorthLayout(), BorderLayout.NORTH);
        l.add(getSouthLayout(), BorderLayout.SOUTH);
        l.add(getEastLayout(), BorderLayout.EAST);
        l.add(getWestLayout(), BorderLayout.WEST);
        return l;
    }

    //////////////////////////////
    /**
     * Creates the panel that contains three another panel, each of them has
     * different settings on it. A GridLayout is used here.
     */
    private JPanel getEastLayout() {
        JPanel l = new JPanel();
        l.setBackground(Color.DARK_GRAY);
        l.setLayout(new GridLayout(3, 1));
        l.add(getTypePanel());
        l.add(getStylePanel());
        l.add(getLimitationPanel());
        return l;
    }

    /**
     * Creates the panel that contains the search bar and the two buttons. It
     * uses a simple FlowLayout.
     */
    private JPanel getNorthLayout() {
        JPanel l = new JPanel();
        l.setBackground(Color.DARK_GRAY);
        l.setLayout(new FlowLayout(FlowLayout.LEFT));
        l.add(txtQuery);
        l.add(btnSearch);
        l.add(btnClear);
        return l;
    }

    /**
     * Creates a panel that contains the ListBox with the results. It uses a
     * FlowLayout.
     */
    private JPanel getWestLayout() {
        JPanel l = new JPanel();
        l.setBackground(Color.DARK_GRAY);
        FlowLayout layout = new FlowLayout();
        layout.setAlignOnBaseline(true);
        l.setLayout(layout);
        l.add(scpList);
        return l;
    }

    /**
     * Creates a panel which is our status bar in the bottom. It contains the
     * counter and uses BevelBorder and BoxLayout.
     */
    private JPanel getSouthLayout() {
        JPanel l = new JPanel();
        l.setBorder(new BevelBorder(BevelBorder.LOWERED));
        l.setPreferredSize(statusBarSize);
        l.setLayout(new BoxLayout(l, BoxLayout.X_AXIS));
        l.add(lblCount);
        return l;
    }

    //////////////////////////////
    /**
     * This panel contains the type settings and uses a GridLayout.
     */
    private JPanel getTypePanel() {
        JPanel l = new JPanel();
        l.setBackground(Color.DARK_GRAY);
        l.setLayout(new GridLayout(4, 1));
        l.setBorder(BorderFactory.createTitledBorder(null, "Type", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(rbtnOp1);
        l.add(rbtnOp2);
        l.add(rbtnOp3);
        l.add(rbtnOp4);
        return l;
    }

    /**
     * This panel contains the style options and uses a GridLayout.
     */
    private JPanel getStylePanel() {
        JPanel l = new JPanel();

        l.setBackground(Color.DARK_GRAY);
        l.setLayout(new GridLayout(4, 1));
        l.setBorder(BorderFactory.createTitledBorder(null, "Style", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbtnOp1);
        l.add(cbtnOp2);
        return l;
    }

    /**
     * This panel contains the style options and uses the default layout.
     */
    private JPanel getLimitationPanel() {
        JPanel l = new JPanel();
        l.setBackground(Color.DARK_GRAY);
        l.setBorder(BorderFactory.createTitledBorder(null, "Limitations", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbbxLimits);
        return l;
    }

    //////////////////////////////
    /**
     * This method simply clears the search bar.
     */
    private void clearQuery() {
        txtQuery.setText("");
    }

    /**
     * This method simply clears the list of results and resets the counter.
     */
    private void clearResults() {
        lstResults.setModel(new javax.swing.DefaultListModel());
        lblCount.setText("Count: " + lstResults.getModel().getSize());

    }

    //////////////////////////////
    /**
     * This method checks which radio button is selected in the type options
     * pane and returns the appropriate search type class.
     *
     * @return Search Type Class
     */
    private IAccept getSearchType() {
        if (rbtnOp1.isSelected()) {
            return new Contains();
        }
        if (rbtnOp2.isSelected()) {
            return new StartsWith();
        }
        if (rbtnOp3.isSelected()) {
            return new EndsWith();
        }
        if (rbtnOp4.isSelected()) {
            return new Exact();
        }
        return null;
    }

    /**
     * This method returns whether the case sensitive CheckBox is selected or
     * not.
     *
     * @return true in case the CheckBox is selected, false otherwise.
     */
    private boolean getCaseSensitive() {
        if (cbtnOp1.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method returns whether the instant search CheckBox is selected or
     * not.
     *
     * @return true in case the CheckBox is selected, false otherwise.
     */
    private boolean getInstantSearch() {
        if (cbtnOp2.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks what is the selected limitation within the ComboBox.
     * Also uses the totalHitsAvailable() from the BL and the intValueOfObject()
     * methods.
     *
     * @return the value associated with that specific selection. In case the
     * default 'None' is selected, it returns the number of all items in our
     * file.
     */
    private int getLimitation() {
        if (cbbxLimits.getSelectedIndex() == 0) {
            return me.totalHitsAvailable();
        }
        if (cbbxLimits.getSelectedIndex() == 1) {
            return intValueOfObject(cbbxLimits.getSelectedItem());
        }
        if (cbbxLimits.getSelectedIndex() == 2) {
            return intValueOfObject(cbbxLimits.getSelectedItem());
        }
        if (cbbxLimits.getSelectedIndex() == 3) {
            return intValueOfObject(cbbxLimits.getSelectedItem());
        }
        if (cbbxLimits.getSelectedIndex() == 4) {
            return intValueOfObject(cbbxLimits.getSelectedItem());
        }
        return 0;
    }

    //////////////////////////////
    /**
     * This method does the search, saves the results in a ArrayList and puts
     * those results inside the list model.
     */
    private void startSearch() {

        ArrayList<String> search;
        search = me.search(txtQuery.getText(), getSearchType(), getCaseSensitive(), getLimitation());
        DefaultListModel model = new DefaultListModel();
        for (String a : search) {
            model.addElement(a);
        }
        lstResults.setModel(model);
        lblCount.setText("Count: " + model.getSize());
    }

    //////////////////////////////
    /**
     * This method takes in an object like a ComboBox object and parses it's
     * value into a Integer.
     *
     * @param o
     * @return
     */
    private int intValueOfObject(Object o) {
        return Integer.parseInt((String) o);
    }

    /**
     * This method updates the path of our file by creating a new MainEngine
     * (BL) with a special constructor that takes in a path.
     *
     * @param path The path of our new file.
     * @throws IOException It deals with a file in the system.
     */
    public void updateSearchFile(String path) throws IOException {
        me = new MainEngine(path);
    }
}
