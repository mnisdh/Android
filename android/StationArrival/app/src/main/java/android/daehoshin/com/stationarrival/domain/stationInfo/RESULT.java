package android.daehoshin.com.stationarrival.domain.stationInfo;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class RESULT {
    private String MESSAGE;

    private String CODE;

    public String getMESSAGE ()
    {
        return MESSAGE;
    }

    public void setMESSAGE (String MESSAGE)
    {
        this.MESSAGE = MESSAGE;
    }

    public String getCODE ()
    {
        return CODE;
    }

    public void setCODE (String CODE)
    {
        this.CODE = CODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MESSAGE = "+MESSAGE+", CODE = "+CODE+"]";
    }
}
