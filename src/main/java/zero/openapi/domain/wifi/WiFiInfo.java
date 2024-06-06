package zero.openapi.domain.wifi;

import lombok.Getter;

@Getter
public class WiFiInfo {
    private final Integer ID;
    private final String mgrNo;
    private final String wrdOfc;
    private final String mainNm;
    private final String addr1;
    private final String addr2;
    private final String instlFloor;
    private final String instlTy;
    private final String instlMby;
    private final String svcSe;
    private final String cmcWr;
    private final String cnstcYear;
    private final String inoutDoor;
    private final String remars3;
    private final double lat;
    private final double lnt;
    private final String workDttm;
    private final double distance;

    public WiFiInfo(Integer ID,String cnstcYear, String mgrNo, String wrdOfc, String mainNm, String addr1, String addr2, String instlFloor, String instlTy, String instlMby, String svcSe, String cmcWr, String inoutDoor, String remars3, double lat, double lnt, String workDttm, double distance) {
        this.ID = ID;
        this.cnstcYear = cnstcYear;
        this.mgrNo = mgrNo;
        this.wrdOfc = wrdOfc;
        this.mainNm = mainNm;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.instlFloor = instlFloor;
        this.instlTy = instlTy;
        this.instlMby = instlMby;
        this.svcSe = svcSe;
        this.cmcWr = cmcWr;
        this.inoutDoor = inoutDoor;
        this.remars3 = remars3;
        this.lat = lat;
        this.lnt = lnt;
        this.workDttm = workDttm;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "WiFiInfo{" +
                "mgrNo='" + mgrNo + '\'' +
                ", wrdOfc='" + wrdOfc + '\'' +
                ", mainNm='" + mainNm + '\'' +
                ", addr1='" + addr1 + '\'' +
                ", addr2='" + addr2 + '\'' +
                ", instlFloor='" + instlFloor + '\'' +
                ", instlTy='" + instlTy + '\'' +
                ", instlMby='" + instlMby + '\'' +
                ", svcSe='" + svcSe + '\'' +
                ", cmcWr='" + cmcWr + '\'' +
                ", cnstcYear='" + cnstcYear + '\'' +
                ", inoutDoor='" + inoutDoor + '\'' +
                ", remars3='" + remars3 + '\'' +
                ", lat=" + lat +
                ", lnt=" + lnt +
                ", workDttm='" + workDttm + '\'' +
                ", distance=" + distance +
                '}';
    }
}
