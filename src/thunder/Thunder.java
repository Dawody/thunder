/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thunder;

/**
 *
 * @author dawod
 */
public class Thunder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        indexer indx = new indexer();
        indx.indexFiles();
        //indx.getWords();
        //System.out.println(indx.stemmer("animation"));
        //System.out.println(indx.stemmer("animal"));
        //System.out.println(indx.stemmer("mouse"));
        //System.out.println(indx.stemmer("mice"));


    }
    
}
