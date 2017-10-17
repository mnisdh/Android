package android.daehoshin.com.bicycle.domain;

/**
 * Created by daeho on 2017. 10. 17..
 */

public class BaseBike {
    private GeoInfoBikeConvenientFacilitiesWGS GeoInfoBikeConvenientFacilitiesWGS;

    public GeoInfoBikeConvenientFacilitiesWGS getGeoInfoBikeConvenientFacilitiesWGS ()
    {
        return GeoInfoBikeConvenientFacilitiesWGS;
    }

    public void setGeoInfoBikeConvenientFacilitiesWGS (GeoInfoBikeConvenientFacilitiesWGS GeoInfoBikeConvenientFacilitiesWGS)
    {
        this.GeoInfoBikeConvenientFacilitiesWGS = GeoInfoBikeConvenientFacilitiesWGS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [GeoInfoBikeConvenientFacilitiesWGS = "+GeoInfoBikeConvenientFacilitiesWGS+"]";
    }
}
