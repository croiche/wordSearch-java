/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

/**
 *
 * @author Martin
 */
public class Contains implements IAccept {

    @Override
    public boolean accept(String query, String hit) {
        return hit.contains(query);
    }
    
}
