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
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicOptionPaneUI;

/**
 *
 * @author Martin, Anthony
 */
public class MainView extends JFrame {

    private JPanel main;
    private MainEngine me;
    private javax.swing.JButton btnStart, btnClose, btnClear;
    private javax.swing.JLabel lblSearch, lblResults, lblCount;
    private javax.swing.JTextField txtQuery;
    private javax.swing.JScrollPane scpList;
    private javax.swing.JList lstResults;
    private javax.swing.JRadioButton rbtnOp1, rbtnOp2, rbtnOp3, rbtnOp4;
    private javax.swing.ButtonGroup rbtnGroup;
    private javax.swing.JCheckBox cbtnOp1;
    private javax.swing.JComboBox cbbxLimits;
    private Dimension searchBarSize, buttonSize, listSize;
    private ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(btnStart)) {
                try {
                    me = new MainEngine();
                    // Start the search query
                    showResults(me.search(txtQuery.getText(), getSearchType()));
                } catch (java.io.IOException ex) {
                    JOptionPane.showMessageDialog(MainView.this, "Error", "File not found!", JOptionPane.ERROR_MESSAGE);
                }

            }
            if (e.getSource().equals(btnClear)) {
                // Clear query + results
                clear();
            }
            if (e.getSource().equals(btnClose)) {
                System.exit(0);
            }
        }
    };
    KeyAdapter queryListener = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            // We could make it search instantly after every keypress.
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // We could make it search if we press ENTER.
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
        ////////////////////////////

        /////////  BUTTONS  /////////
        buttonSize = new Dimension(75, 25);
        btnStart = new javax.swing.JButton("Start");
        btnClear = new javax.swing.JButton("Clear");
        btnClose = new javax.swing.JButton("Close");
        btnStart.setPreferredSize(buttonSize);
        btnClear.setPreferredSize(buttonSize);
        btnClose.setPreferredSize(buttonSize);
        btnStart.addActionListener(buttonListener);
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
        rbtnOp1 = new javax.swing.JRadioButton("Begins with");
        rbtnOp2 = new javax.swing.JRadioButton("Contains");
        rbtnOp3 = new javax.swing.JRadioButton("Ends with");
        rbtnOp4 = new javax.swing.JRadioButton("Exact");
        rbtnGroup.add(rbtnOp1);
        rbtnGroup.add(rbtnOp2);
        rbtnGroup.add(rbtnOp3);
        rbtnGroup.add(rbtnOp4);
        rbtnOp1.setSelected(true);
        /////////////////////////////

        //////// CHECK BOXES ////////
        cbtnOp1 = new javax.swing.JCheckBox("Case sensitive");
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
        l.add(btnStart);
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
        l.setBorder(BorderFactory.createTitledBorder(null, "Style", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbtnOp1);
        return l;
    }

    private JPanel getLimitationPanel() {
        JPanel l = new JPanel();
        l.setBorder(BorderFactory.createTitledBorder(null, "Limitations", TitledBorder.CENTER, TitledBorder.CENTER));
        l.add(cbbxLimits);
        return l;
    }

    private void clear() {
        lstResults.setModel(new javax.swing.DefaultListModel());
        txtQuery.setText("");
        lblCount.setText("Count: " + lstResults.getModel().getSize());
    }

    private IAccept getSearchType() {
        if (rbtnOp1.isSelected()) {
            return new StartsWith();
        }
        if (rbtnOp2.isSelected()) {
            return new Contains();
        }
        if (rbtnOp3.isSelected()) {
            return new EndsWith();
        }
        if (rbtnOp4.isSelected()) {
            return new Exact();
        }
        return null;
    }

    private void showResults(ArrayList<String> search) {
        DefaultListModel model = new DefaultListModel();
        for (String a : search) {
            model.addElement(a);
        }
        lstResults.setModel(model);
        lblCount.setText("Count: " + model.getSize());
    }
}
