/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package thunder;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
//TEST insertion


        for(int i=0 ; i<1 ; i++){
            indx.indexFiles();
            System.out.println("counter = "+i);

        }




//
//TEST select link where stemmed_word or original_word =some_value
//        List<String> links = new ArrayList<String>();
//        links = indx.getLink("sucessed",1);
//        for(String link :links){
//            System.out.println("link : "+link);
//        }


//TEST select total number of words in specfic link
//        System.out.println("numer of total words in Document is "+indx.getTotal("http://link.com"));



//
//TEST select tag where link =some_link and stemmed_word or original_word =some_value
//        List<String> tags = new ArrayList<String>();
//        tags = indx.getTags("http://link.com","sucessed",1);
//        for(String tag :tags){
//            System.out.println("tag : "+tag);
//        }


//TEST select number of repeatance of stem or original word in specific link
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","anim",0));
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","animal",1));
//    System.out.println("numer of counts in Document for the word is "+indx.getCount("http://link.com","animation",1));
//    







//TEST the stemmer and discovered bugs!
//System.out.println(indx.stemmer("animation"));
//System.out.println(indx.stemmer("animal"));
//System.out.println(indx.stemmer("mouse"));
//System.out.println(indx.stemmer("mice"));


    }
    
}
