/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import javax.swing.JFrame;

/**
 *
 * @author Martin
 */
public class WordSearch {

    public static void main(String[] args) {
        JFrame frame = new MainView();
        frame.setTitle("Word Search Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(430, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
