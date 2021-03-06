/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author Martin
 */
public class FileChooser extends JFrame {

    private String path;
    private JFrame frame;
    private MainView mv;
    
    

    public FileChooser(MainView mv, JMenuItem item) {
        this.mv = mv;
        item.addActionListener(new OpenClass());
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    class OpenClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();

            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new MyCustomFilter());

            int option = chooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                path = chooser.getSelectedFile().getPath();
                try {
                    mv.updateSearchFile(path);
                } catch (IOException ex) {
                    Logger.getLogger(FileChooser.class.getName()).log(Level.SEVERE, null, ex);
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
    }

}
