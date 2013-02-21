package UI;

import BL.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import sun.awt.HorizBagLayout;

/**
 *
 * @author Martin, Anthony
 */
public class MainView extends JFrame {

    private JPanel main;
    private MainEngine me;
    private javax.swing.JButton btnSearch, btnClose, btnClear;
    private javax.swing.JLabel lblSearch, lblResults, lblCount;
    private javax.swing.JTextField txtQuery;
    private javax.swing.JScrollPane scpList;
    private javax.swing.JList lstResults;
    private javax.swing.JRadioButton rbtnOp1, rbtnOp2, rbtnOp3, rbtnOp4;
    private javax.swing.ButtonGroup rbtnGroup;
    private javax.swing.JCheckBox cbtnOp1, cbtnOp2;
    private javax.swing.JComboBox cbbxLimits;
    private Dimension searchBarSize, buttonSize, listSize;
    private FileChooser fc;
    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnSearch)) {
                startSearch();
            }
            if (e.getSource().equals(btnClear)) {
                // Clear query + results
                clearQuery();
                clearResults();
            }
            if (e.getSource().equals(btnClose)) {
                System.exit(0);
            }
        }
    };
    KeyAdapter queryListener = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            if (getInstantSearch() && txtQuery.getText().length() >= 3) {
                startSearch();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
                startSearch();
                e.consume();
            }
            if (key == KeyEvent.VK_BACK_SPACE) {
                clearResults();
            }
        }
    };
    MouseAdapter clickListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    };

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
        this.setSize(500, 400);
        this.setResizable(false);
        this.setTitle("Word Search Tool");
        main = getBorderLayout();
        this.add(main);
    }

    private void init() {
        ///////  TEXT FIELDS  ///////
        txtQuery = new javax.swing.JTextField();
        searchBarSize = new Dimension(260, 25);
        txtQuery.setPreferredSize(searchBarSize);
        txtQuery.addKeyListener(queryListener);
        ////////////////////////////

        /////////  BUTTONS  /////////
        buttonSize = new Dimension(75, 25);
        btnSearch = new javax.swing.JButton("Search");
        btnClear = new javax.swing.JButton("Clear");
        btnClose = new javax.swing.JButton("Close");
        btnSearch.setPreferredSize(buttonSize);
        btnClear.setPreferredSize(buttonSize);
        btnClose.setPreferredSize(buttonSize);
        btnSearch.addActionListener(buttonListener);
        btnClear.addActionListener(buttonListener);
        btnClose.addActionListener(buttonListener);
        /////////////////////////////

        ////// LIST COMPONENTS //////
        listSize = new Dimension(255, 295);
        scpList = new javax.swing.JScrollPane();
        lstResults = new javax.swing.JList();
        scpList.setViewportView(lstResults);
        scpList.setPreferredSize(listSize);
        /////////////////////////////

        /////// RADIO BUTTONS ///////
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
        /////////////////////////////

        //////// CHECK BOXES ////////
        cbtnOp1 = new javax.swing.JCheckBox("Case sensitive");
        cbtnOp2 = new javax.swing.JCheckBox("Instant search");
        /////////////////////////////

        /////// COMBO BOXES ////////
        cbbxLimits = new javax.swing.JComboBox();
        cbbxLimits.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"None", "50", "100", "250", "500"}));
        ////////////////////////////

        //////////  LABELS  //////////
        lblSearch = new javax.swing.JLabel("Search:");
        lblResults = new javax.swing.JLabel("Results");
        lblCount = new javax.swing.JLabel("Count: " + lstResults.getModel().getSize());
        //////////////////////////////


    }

    private JPanel getBorderLayout() {
        JPanel l = new JPanel();
        l.setLayout(new BorderLayout());

        l.add(getNorthLayout(), BorderLayout.NORTH);
        l.add(getSouthLayout(), BorderLayout.SOUTH);
        l.add(getEastLayout(), BorderLayout.EAST);
        l.add(getWestLayout(), BorderLayout.WEST);
        return l;
    }

    private JPanel getEastLayout() {
        JPanel l = new JPanel();
        l.setLayout(new GridLayout(3, 1));
        l.add(getTypePanel());
        l.add(getStylePanel());
        l.add(getLimitationPanel());
        return l;
    }

    private JPanel getNorthLayout() {
        JPanel l = new JPanel();
        l.setLayout(new FlowLayout(FlowLayout.LEFT));
        l.add(lblSearch);
        l.add(txtQuery);
        l.add(btnSearch);
        l.add(btnClear);
        return l;
    }

    private JPanel getWestLayout() {
        JPanel l = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setAlignOnBaseline(true);
        l.setLayout(layout);
        l.add(lblResults);
        l.add(scpList);
        return l;
    }

    private JPanel getSouthLayout() {
        JPanel l = new JPanel();
        l.setLayout(new BasicOptionPaneUI.ButtonAreaLayout(true, 230));
        l.add(lblCount);
        l.add(btnClose);
        return l;
    }

    private JPanel getTypePanel() {
        JPanel l = new JPanel();
        l.setLayout(new GridLayout(4, 1));
        l.setBorder(BorderFactory.createTitledBorder(null, "Type", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(rbtnOp1);
        l.add(rbtnOp2);
        l.add(rbtnOp3);
        l.add(rbtnOp4);
        return l;
    }

    private JPanel getStylePanel() {
        JPanel l = new JPanel();
        l.setLayout(new GridLayout(4, 1));
        l.setBorder(BorderFactory.createTitledBorder(null, "Style", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbtnOp1);
        l.add(cbtnOp2);
        return l;
    }

    private JPanel getLimitationPanel() {
        JPanel l = new JPanel();
        l.setBorder(BorderFactory.createTitledBorder(null, "Limitations", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbbxLimits);
        return l;
    }

    private void clearQuery() {
        txtQuery.setText("");
    }

    private void clearResults() {
        lstResults.setModel(new javax.swing.DefaultListModel());
        lblCount.setText("Count: " + lstResults.getModel().getSize());

    }

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

    private boolean getCaseSensitive() {
        if (cbtnOp1.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean getInstantSearch() {
        if (cbtnOp2.isSelected()) {
            return true;
        } else {
            return false;
        }
    }

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

    private void startSearch() {
        try {
            me = new MainEngine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainView.this, "Error", "File not found!", JOptionPane.ERROR_MESSAGE);

        }
        ArrayList<String> search;
        search = me.search(txtQuery.getText(), getSearchType(), getCaseSensitive(), getLimitation());
        DefaultListModel model = new DefaultListModel();
        for (String a : search) {
            model.addElement(a);
        }
        lstResults.setModel(model);
        lblCount.setText("Count: " + model.getSize());
    }

    private int intValueOfObject(Object o) {
        return Integer.parseInt((String) o);
    }
}
