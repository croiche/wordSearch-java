/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Martin
 */
public class MainView extends JFrame {

    private JLabel main;

    public MainView() {
        init();

        this.setSize(500, 600);
        this.setTitle("Word Searcher");


        main = getBorderLayout();
        this.add(main);


    }

    private void init() {
    }

    private JLabel getBorderLayout() {
        JLabel l = new JLabel();
        return l;
    }
}
