/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Martin
 */
public class MainView extends JFrame {

    private JPanel main;

    public MainView() {
        init();

        this.setSize(500, 600);
        this.setTitle("Word Searcher");


        main = getBorderLayout();
        this.add(main);
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


    }

    private void init() {
    }

    private JPanel getBorderLayout() {
        JPanel l = new JPanel();
        l.setLayout(new BorderLayout());
        
        l.add(new QueryPanel(), BorderLayout.NORTH);
        l.add(new JButton("Close"), BorderLayout.SOUTH);
        l.add(getEastLayout(), BorderLayout.EAST);
        l.add(new ResultsPanel(), BorderLayout.WEST);
        
        
        return l;
    }

    private JPanel getEastLayout() {
        JPanel l = new JPanel();
        l.setLayout(new GridLayout(3, 1));
        l.add(new TypePanel());
        l.add(new StylePanel());
        l.add(new LimitPanel());
        return l;
    }
}
