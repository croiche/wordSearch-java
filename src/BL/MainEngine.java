/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAL.FileManager;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author anthony
 */
public class MainEngine {

    private FileManager fm;

    public MainEngine(String path) throws IOException {
        fm = new FileManager(path);
    }

    public MainEngine() throws IOException {
        fm = new FileManager();
    }

    /**
     * Returns the number of the elements inside our file.
     *
     * @return int The total number of elements.
     */
    public int totalHitsAvailable() {
        return fm.getFileData().size();
    }

    /**
     * We loop through all the Strings from our file and save it into *another*
     * file, because we have to manipulate one of them in case we are not
     * conducting a case sensitive search. In the case when we are doing a case
     * sensitive search, we put all Strings into lower case Strings and compare
     * them using the other Search type classes that we created.
     *
     * @param query A string that we are searching for.
     * @param ia The type of search we are conducting.
     * @param caseSensitive Should the search be case sensitive or not?!
     * @param limit The number of results we want to get back.
     * @return tempAl An ArrayList<Strong> with the results.
     */
    public ArrayList<String> search(String query, IAccept ia, boolean caseSensitive, int limit) {
        ArrayList<String> tempAL = new ArrayList<>();
        String tempHit;
        for (String hit : fm.getFileData()) {
            tempHit = hit;
            if (!caseSensitive) {
                if (ia.accept(query.toLowerCase(), tempHit.toLowerCase())) {
                    if (tempAL.size() < limit) {
                        tempAL.add(hit);
                    }
                }
            } else {
                if (ia.accept(query, hit)) {
                    if (tempAL.size() < limit) {
                        tempAL.add(hit);
                    }
                }
            }
        }
        return tempAL;
    }
}
