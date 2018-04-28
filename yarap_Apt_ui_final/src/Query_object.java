public class Query_object {
    private String []query_words;
    private int ph;
    public Query_object (String []qu,int p)
    {
        query_words=qu;
        ph=p;
    }
    public int getPh(){
        return ph;
    }
    public String [] getQuery_words(){
        return query_words;
    }
}
