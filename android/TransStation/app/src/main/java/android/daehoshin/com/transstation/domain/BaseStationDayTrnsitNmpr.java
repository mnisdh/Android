package android.daehoshin.com.transstation.domain;

/**
 * Created by daeho on 2017. 10. 17..
 */

public class BaseStationDayTrnsitNmpr {
    private StationDayTrnsitNmpr StationDayTrnsitNmpr;

    public StationDayTrnsitNmpr getStationDayTrnsitNmpr ()
    {
        return StationDayTrnsitNmpr;
    }
    public void setStationDayTrnsitNmpr (StationDayTrnsitNmpr StationDayTrnsitNmpr)
    {
        this.StationDayTrnsitNmpr = StationDayTrnsitNmpr;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [StationDayTrnsitNmpr = "+StationDayTrnsitNmpr+"]";
    }
}
