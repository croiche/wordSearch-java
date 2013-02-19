/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAL.FileManager;
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author anthony
 */
public class MainEngine {

    private FileManager fm;

    public MainEngine() throws FileNotFoundException, IOException {
        fm = new FileManager();
        
    }

    public ArrayList<String> search(String query, IAccept ia) {
        ArrayList<String> tempAL = new ArrayList<>();
        
        for (String a : fm.getFileData()) {

            if (ia.accept(query, a)){
            tempAL.add(a);
            }
        }
        return tempAL;
    }

   
}
