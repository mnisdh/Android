package android.daehoshin.com.remotebbs.domain;

import java.util.List;

/**
 * Created by daeho on 2017. 10. 26..
 */

public class Result
{
    private String code;

    private String msg;

    private List<Bbs> bbs;

    public boolean isOK(){
        return "200".equals(code);
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    public List<Bbs> getBbs ()
    {
        return bbs;
    }

    public void setBbs (List<Bbs> bbs)
    {
        this.bbs = bbs;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", msg = "+msg+", bbs = "+bbs+"]";
    }
}