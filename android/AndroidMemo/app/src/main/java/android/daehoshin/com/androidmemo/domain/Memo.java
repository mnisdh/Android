package android.daehoshin.com.androidmemo.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class Memo implements Serializable {
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
    public String getFormatedDatetime(){
        return getFormatedDatetime("yyyy-MM-dd hh:mm:ss");
    }
    public String getFormatedDatetime(String format){
        Date date = new Date(datetime);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    public void setDatetime(long datetime){this.datetime = datetime;}

    public Memo(int id){
        this.id = id;
    }

}
