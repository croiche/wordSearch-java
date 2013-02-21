/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import BL.MainEngine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author Martin
 */
public class FileChooser extends JFrame{

    private String path;    
    private JFrame frame;
    private MainEngine me;

    public FileChooser(JMenuItem item) {
        item.addActionListener(new OpenClass());
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            me = new MainEngine();
        } catch (IOException ex) {
        }
    }
    
    

    class OpenClass implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();

            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new MyCustomFilter());

            int option = chooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                me.setPath(chooser.getSelectedFile().getPath()); 
            }
        }
    }

    class MyCustomFilter extends javax.swing.filechooser.FileFilter {

        @Override
        public boolean accept(File file) {
            // Allow only directories, or files with ".txt" extension
            return file.isDirectory() || file.getAbsolutePath().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "Text documents (*.txt)";
        }
    }

    public String getPath() {
        return path;
    }
}
