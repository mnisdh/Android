package android.daehoshin.com.androidmemo.domain;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class Memo {
    private int id;
    public int getId(){return id;}

    private String title;
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    private String author;
    public String getAuthor(){return author;}
    public void setAuthor(String author){this.author = author;}

    private String content;
    public String getContent(){return content;}
    public void setContent(String content){this.content = content;}

    private long datetime;
    public long getDatetime(){return datetime;}
    public void setDatetime(long datetime){this.datetime = datetime;}

    public Memo(int id){
        this.id = id;
    }

}
