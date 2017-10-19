package android.daehoshin.com.stationarrival.domain.station;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class BaseStationList {
    private SearchInfoBySubwayNameService SearchInfoBySubwayNameService;

    public SearchInfoBySubwayNameService getSearchInfoBySubwayNameService ()
    {
        return SearchInfoBySubwayNameService;
    }

    public void setSearchInfoBySubwayNameService (SearchInfoBySubwayNameService SearchInfoBySubwayNameService)
    {
        this.SearchInfoBySubwayNameService = SearchInfoBySubwayNameService;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchInfoBySubwayNameService = "+SearchInfoBySubwayNameService+"]";
    }
}
