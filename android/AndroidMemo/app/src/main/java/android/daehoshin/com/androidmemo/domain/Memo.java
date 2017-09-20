package android.daehoshin.com.androidmemo.domain;

import android.daehoshin.com.androidmemo.util.FileUtil;

import java.io.File;
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
    public Memo(File file){
        try {
            String s = FileUtil.read(file);
            String[] rows = s.split("[\\r\\n]+");

            for(String row : rows){
                String[] cells = row.split(":^:");
                if(cells.length == 2){
                    switch (cells[0]){
                        case "no": this.id = Integer.parseInt(cells[1]); break;
                        case "title": this.title = cells[1]; break;
                        case "author": this.author = cells[1]; break;
                        case "datetime": this.datetime = Long.parseLong(cells[1]); break;
                        case "content": this.content += cells[1]; break;
                    }
                }
                else if(cells.length == 1 && !this.content.equals("")) this.content += cells[0];
            }

        }catch (Exception ex){

        }

    }

    public void Save(String fileName){

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("no:^:").append(id).append("\n");
        sb.append("title:^:").append(title).append("\n");
        sb.append("author:^:").append(author).append("\n");
        sb.append("datetime:^:").append(datetime).append("\n");
        sb.append("content:^:").append(content).append("\n");

        return sb.toString();
    }

    public byte[] toBytes(){
        return toString().getBytes();
    }

}
