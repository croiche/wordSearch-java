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
