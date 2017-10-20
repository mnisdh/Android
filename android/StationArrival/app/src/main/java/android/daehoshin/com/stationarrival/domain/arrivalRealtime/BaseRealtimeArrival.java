package android.daehoshin.com.stationarrival.domain.arrivalRealtime;

import java.util.List;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class BaseRealtimeArrival {
    private ErrorMessage errorMessage;

    private List<RealtimeArrivalList> realtimeArrivalList;

    public ErrorMessage getErrorMessage ()
    {
        return errorMessage;
    }

    public void setErrorMessage (ErrorMessage errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public List<RealtimeArrivalList> getRealtimeArrivalList ()
    {
        return realtimeArrivalList;
    }

    public void setRealtimeArrivalList (List<RealtimeArrivalList> realtimeArrivalList)
    {
        this.realtimeArrivalList = realtimeArrivalList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [errorMessage = "+errorMessage+", realtimeArrivalList = "+realtimeArrivalList+"]";
    }
}
