/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

    ;

    public FileManager() throws FileNotFoundException, IOException {
        file = new File("");
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

    public void setPath(String newPath) {
        file = new File(newPath);

    }
}
