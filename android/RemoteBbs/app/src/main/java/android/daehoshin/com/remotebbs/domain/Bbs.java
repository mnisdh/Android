package android.daehoshin.com.remotebbs.domain;

/**
 * Created by daeho on 2017. 10. 26..
 */

public class Bbs
{
    private String content;

    private String title;

    private String _id;

    private String no;

    private String user_id;

    private String date;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getNo ()
    {
        return no;
    }

    public void setNo (String no)
    {
        this.no = no;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", title = "+title+", _id = "+_id+", no = "+no+", user_id = "+user_id+", date = "+date+"]";
    }
}
