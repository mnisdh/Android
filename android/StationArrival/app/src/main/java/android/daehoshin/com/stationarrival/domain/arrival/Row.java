package android.daehoshin.com.stationarrival.domain.arrival;

/**
 * Created by daeho on 2017. 10. 20..
 */

public class Row {
    private String TRAIN_NO;

    private String SUBWAYSNAME;

    private String STATION_NM;

    private String STATION_CD;

    private String SUBWAYENAME;

    private String WEEK_TAG;

    private String INOUT_TAG;

    private String LINE_NUM;

    private String LEFTTIME;

    private String ARRIVETIME;

    private String BRANCH_LINE;

    private String DESTSTATION;

    private String FL_FLAG;

    private String FR_CODE;

    private String EXPRESS_YN;

    private String ORIGINSTATION;

    private String DESTSTATION2;

    public String getTRAIN_NO ()
    {
        return TRAIN_NO;
    }

    public void setTRAIN_NO (String TRAIN_NO)
    {
        this.TRAIN_NO = TRAIN_NO;
    }

    public String getSUBWAYSNAME ()
    {
        return SUBWAYSNAME;
    }

    public void setSUBWAYSNAME (String SUBWAYSNAME)
    {
        this.SUBWAYSNAME = SUBWAYSNAME;
    }

    public String getSTATION_NM ()
    {
        return STATION_NM;
    }

    public void setSTATION_NM (String STATION_NM)
    {
        this.STATION_NM = STATION_NM;
    }

    public String getSTATION_CD ()
    {
        return STATION_CD;
    }

    public void setSTATION_CD (String STATION_CD)
    {
        this.STATION_CD = STATION_CD;
    }

    public String getSUBWAYENAME ()
    {
        return SUBWAYENAME;
    }

    public void setSUBWAYENAME (String SUBWAYENAME)
    {
        this.SUBWAYENAME = SUBWAYENAME;
    }

    public String getWEEK_TAG ()
    {
        return WEEK_TAG;
    }

    public void setWEEK_TAG (String WEEK_TAG)
    {
        this.WEEK_TAG = WEEK_TAG;
    }

    public String getINOUT_TAG ()
    {
        return INOUT_TAG;
    }

    public void setINOUT_TAG (String INOUT_TAG)
    {
        this.INOUT_TAG = INOUT_TAG;
    }

    public String getLINE_NUM ()
    {
        return LINE_NUM;
    }

    public void setLINE_NUM (String LINE_NUM)
    {
        this.LINE_NUM = LINE_NUM;
    }

    public String getLEFTTIME ()
    {
        return LEFTTIME;
    }

    public void setLEFTTIME (String LEFTTIME)
    {
        this.LEFTTIME = LEFTTIME;
    }

    public String getARRIVETIME ()
    {
        return ARRIVETIME;
    }

    public void setARRIVETIME (String ARRIVETIME)
    {
        this.ARRIVETIME = ARRIVETIME;
    }

    public String getBRANCH_LINE ()
    {
        return BRANCH_LINE;
    }

    public void setBRANCH_LINE (String BRANCH_LINE)
    {
        this.BRANCH_LINE = BRANCH_LINE;
    }

    public String getDESTSTATION ()
    {
        return DESTSTATION;
    }

    public void setDESTSTATION (String DESTSTATION)
    {
        this.DESTSTATION = DESTSTATION;
    }

    public String getFL_FLAG ()
    {
        return FL_FLAG;
    }

    public void setFL_FLAG (String FL_FLAG)
    {
        this.FL_FLAG = FL_FLAG;
    }

    public String getFR_CODE ()
    {
        return FR_CODE;
    }

    public void setFR_CODE (String FR_CODE)
    {
        this.FR_CODE = FR_CODE;
    }

    public String getEXPRESS_YN ()
    {
        return EXPRESS_YN;
    }

    public void setEXPRESS_YN (String EXPRESS_YN)
    {
        this.EXPRESS_YN = EXPRESS_YN;
    }

    public String getORIGINSTATION ()
    {
        return ORIGINSTATION;
    }

    public void setORIGINSTATION (String ORIGINSTATION)
    {
        this.ORIGINSTATION = ORIGINSTATION;
    }

    public String getDESTSTATION2 ()
    {
        return DESTSTATION2;
    }

    public void setDESTSTATION2 (String DESTSTATION2)
    {
        this.DESTSTATION2 = DESTSTATION2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TRAIN_NO = "+TRAIN_NO+", SUBWAYSNAME = "+SUBWAYSNAME+", STATION_NM = "+STATION_NM+", STATION_CD = "+STATION_CD+", SUBWAYENAME = "+SUBWAYENAME+", WEEK_TAG = "+WEEK_TAG+", INOUT_TAG = "+INOUT_TAG+", LINE_NUM = "+LINE_NUM+", LEFTTIME = "+LEFTTIME+", ARRIVETIME = "+ARRIVETIME+", BRANCH_LINE = "+BRANCH_LINE+", DESTSTATION = "+DESTSTATION+", FL_FLAG = "+FL_FLAG+", FR_CODE = "+FR_CODE+", EXPRESS_YN = "+EXPRESS_YN+", ORIGINSTATION = "+ORIGINSTATION+", DESTSTATION2 = "+DESTSTATION2+"]";
    }
}
