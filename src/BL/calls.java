/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Martin
 */
public class calls {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        MainEngine me = new MainEngine();

        ArrayList<String> st = new ArrayList<>();
        Contains con = new Contains();
        StartsWith sw = new StartsWith();
        EndsWith ew = new EndsWith();
        Exact e = new Exact();
        for (int i = 0; i < me.search("ele", con).size(); i++) {
            st.add(me.search("ele", con).get(i));
        }
        for (int i = 0; i < st.size(); i++) {
            System.out.println(st.get(i));
        }
    }
}
