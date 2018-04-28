package thunder;

public class Query_obj {
    private String query;
    private String []query_words;
    private int ph;

    public Query_obj (String s, String []qu,int p)
    { this.query = s; this.query_words=qu; this.ph=p; }
    public String getQuery() { return query; }
    public int getPh(){ return ph; }
    public String [] getQuery_words(){ return query_words; }
}
