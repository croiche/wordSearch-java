/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

/**
 *
 * @author Martin
 */
public class Exact implements IAccept{

    @Override
    public boolean accept(String query, String hit) {
        return hit.equals(query);
    }
    
}
