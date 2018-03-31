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
public class Data {
    
    private String stem;
    private String link;
    private int total;
    private String original;
    private String tag;
    private int position;
    
    public Data(String stem, String link, int total, String original, String tag , int position){
        this.stem=stem;
        this.link=link;
        this.total=total;
        this.original=original;
        this.tag=tag;
        this.position=position;    
    }
    
    public String getStem(){
        return this.stem;
    }
    public String getLink(){
        return this.link;
    }
    public int getTotal(){
        return this.total;
    }
    public String getOriginal(){
        return this.original;
    }
    public String getTag(){
        return this.tag;
    }
    public int getPosition(){
        return this.position;
    }
    
    
}
