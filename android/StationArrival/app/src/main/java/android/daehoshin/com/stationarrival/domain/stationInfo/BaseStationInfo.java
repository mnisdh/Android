package android.daehoshin.com.stationarrival.domain.stationInfo;

/**
 * Created by daeho on 2017. 10. 19..
 */

public class BaseStationInfo {
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
