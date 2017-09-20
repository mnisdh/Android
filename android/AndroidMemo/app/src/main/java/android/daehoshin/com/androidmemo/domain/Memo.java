package android.daehoshin.com.androidmemo.domain;

import android.daehoshin.com.androidmemo.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daeho on 2017. 9. 19..
 */

public class Memo implements Serializable {
    private final String SEP = "@#@";

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
    public Memo(File file) throws IOException {
        try {
            String s = FileUtil.read(file);
            String[] rows = s.split("[\\r\\n]+");
            //String[] rows = s.split("\n");

            for(String row : rows){
                String[] cells = row.split(SEP);

                String key = "";
                String value = "";
                if(cells.length == 2){
                    key = cells[0];
                    value = cells[1];
                }
                else{
                    key = "";
                    value = cells[0];
                }

                switch (key){
                    case "no": this.id = Integer.parseInt(value); break;
                    case "title": this.title = value; break;
                    case "author": this.author = value; break;
                    case "datetime": this.datetime = Long.parseLong(value); break;
                    case "content": this.content += value; break;
                    default: this.content += "\n" + value; break;
                }
            }

        }catch (Exception e){
            throw e;
        }

    }

    public void save(String fileName) throws IOException {
        try {
            FileUtil.write(fileName, toBytes());
        } catch (IOException e) {
            throw e;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("no").append(SEP).append(id).append("\n");
        sb.append("title").append(SEP).append(title).append("\n");
        sb.append("author").append(SEP).append(author).append("\n");
        sb.append("datetime").append(SEP).append(datetime).append("\n");
        sb.append("content").append(SEP).append(content).append("\n");

        return sb.toString();
    }

    public byte[] toBytes(){
        return toString().getBytes();
    }

}
