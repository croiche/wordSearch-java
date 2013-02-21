/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class FileManager {

    private File file;
    private BufferedReader br;
    private ArrayList<String> fileData;
    private String defaultFilePath = "res/brit-a-z.txt";
    
    public FileManager() throws IOException {
        file = new File(defaultFilePath);
        br = new BufferedReader(new FileReader(file));

        addFileData();
    }
    
    public FileManager(String path) throws IOException {
        file = new File(path);
        br = new BufferedReader(new FileReader(file));

        addFileData();
    }

    private void addFileData() throws IOException {
        fileData = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            fileData.add(line);
        }
    }

    public ArrayList<String> getFileData() {
        return fileData;
    }
}
