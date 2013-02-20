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

    public MainEngine() throws IOException {
        fm = new FileManager();

    }

    public int totalHitsAvailable() {
        return fm.getFileData().size();
    }

    public ArrayList<String> search(String query, IAccept ia, boolean caseSensitive, int limit) {
        ArrayList<String> tempAL = new ArrayList<>();
        String tempHit = "";
        for (String hit : fm.getFileData()) {
            tempHit = hit;
            if (!caseSensitive) {
                if (ia.accept(query.toLowerCase(), tempHit.toLowerCase())) {
                    if(tempAL.size() < limit){
                    tempAL.add(hit);
                    }
                }
            } else {
                if (ia.accept(query, hit)) {
                    tempAL.add(hit);
                }
            }
        }
        return tempAL;
    }
}
