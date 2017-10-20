package android.daehoshin.com.stationarrival.domain.arrival;

/**
 * Created by daeho on 2017. 10. 20..
 */

public class BaseArrival {
    private SearchSTNTimeTableByIDService SearchSTNTimeTableByIDService;

    public SearchSTNTimeTableByIDService getSearchSTNTimeTableByIDService ()
    {
        return SearchSTNTimeTableByIDService;
    }

    public void setSearchSTNTimeTableByIDService (SearchSTNTimeTableByIDService SearchSTNTimeTableByIDService)
    {
        this.SearchSTNTimeTableByIDService = SearchSTNTimeTableByIDService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchSTNTimeTableByIDService = "+SearchSTNTimeTableByIDService+"]";
    }
}
