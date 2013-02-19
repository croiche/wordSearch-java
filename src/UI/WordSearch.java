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
        JFrame myFrame = new MainView();
        //myFrame.setTitle("Word search tool");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);

    }
}
