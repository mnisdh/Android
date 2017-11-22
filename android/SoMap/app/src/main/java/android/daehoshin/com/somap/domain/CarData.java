package android.daehoshin.com.somap.domain;

/**
 * Created by daeho on 2017. 11. 22..
 */

public class CarData {
    private String id;

    private String driving_fee;

    private String price;

    private String price_name;

    private String car_id;

    private String zone_name;

    private String zone_id;

    private String car_name;

    private String oil_type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDriving_fee ()
    {
        return driving_fee;
    }

    public void setDriving_fee (String driving_fee)
    {
        this.driving_fee = driving_fee;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getPrice_name ()
    {
        return price_name;
    }

    public void setPrice_name (String price_name)
    {
        this.price_name = price_name;
    }

    public String getCar_id ()
    {
        return car_id;
    }

    public void setCar_id (String car_id)
    {
        this.car_id = car_id;
    }

    public String getZone_name ()
    {
        return zone_name;
    }

    public void setZone_name (String zone_name)
    {
        this.zone_name = zone_name;
    }

    public String getZone_id ()
    {
        return zone_id;
    }

    public void setZone_id (String zone_id)
    {
        this.zone_id = zone_id;
    }

    public String getCar_name ()
    {
        return car_name;
    }

    public void setCar_name (String car_name)
    {
        this.car_name = car_name;
    }

    public String getOil_type ()
    {
        return oil_type;
    }

    public void setOil_type (String oil_type)
    {
        this.oil_type = oil_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", driving_fee = "+driving_fee+", price = "+price+", price_name = "+price_name+", car_id = "+car_id+", zone_name = "+zone_name+", zone_id = "+zone_id+", car_name = "+car_name+", oil_type = "+oil_type+"]";
    }
}
