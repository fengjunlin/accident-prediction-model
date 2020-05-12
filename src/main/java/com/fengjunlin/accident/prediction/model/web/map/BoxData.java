package com.fengjunlin.accident.prediction.model.web.map;
import java.util.ArrayList;
import java.util.List;
/**
 * 把每个地域的数据都加载进入地域围栏中
 */
public final class BoxData {
    /**
     * 城市的名字
     */
    private String cityName;
    /**
     * 围栏坐标点
     */
    private List<PointData> cityPointList = new ArrayList<>();
    /**
     * 最大经纬度点
     */
    private PointData maxPoint;
    /**
     * 最小经纬度点
     */
    private PointData minPoint;
    /**
     * 城市编码
     */
    private Integer adcode;

    /**
     *  判定经纬度数据点是否在盒子的最大经纬度和最小经纬度之间
     *
     * @param pt 需要判定的数据点
     * @return 返回判定的结果数据
     */
    public boolean checkInBox(PointData pt){
        if(pt.getX() > minPoint.getX() && pt.getY() > minPoint.getY()){
            if(pt.getX() < maxPoint.getX() && pt.getY() < maxPoint.getY()){
                return inPolygon(pt);
            }
        }
        return false;
    }

    /**
     * 判定该坐标点水平向右的射线和围栏的交点的个数
     *
     * @param pt 需要判定的坐标点
     * @return true：交点个数为奇数个这个点在这个围栏中，false:交点个为偶数个，这个点不在这个围栏中
     */
    private boolean inPolygon(PointData pt){
        try{
            int nCount = 0;

            PointData p0 = cityPointList.get(0);
            for (int i=1;i<cityPointList.size();++i){
                PointData p = cityPointList.get(i);
                if(pointInRect(pt, p0, p)){
                    ++nCount;
                }
                p0 = p;
            }
            if(nCount%2 != 0){
                return true;
            }
            return false;
        }
        catch (Exception ex){
            return false;
        }
    }

    /**
     *  判断这个需要判定的坐标点，和两个点连成的线段是否有交点
     *
     * @param pt 需要判定的坐标点
     * @param p0 线段的头一个坐标点
     * @param p1 线段的最后一个坐标点
     * @return true：有交点，false:没有交点
     */
    private boolean pointInRect(PointData pt, PointData p0, PointData p1){

        int x0 = p0.getX(), x1 = p1.getX();
        int minX = x0 < x1 ? x0 : x1;
        int maxX = x0 > x1 ? x0 : x1;
        int deltax = x1 - x0;
        int y0 = p0.getY(), y1 = p1.getY();
        int maxY = y0 > y1 ? y0 : y1;
        int minY = y0 < y1 ? y0 : y1;
        int deltay = y1 - y0;

        if(pt.getY() > minY && pt.getY() < maxY){
            if(pt.getX() < minX){
                return true;
            }
            else if(pt.getX() > maxX){
                return false;
            }
            else if(deltax == 0){
                return true;
            }
            else{
                double k = (double) deltay/deltax;

                if(k > 0){
                    int y = (int) (minY + (pt.getX() - minX) * k);
                    if(y < pt.getY()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    int y = (int) (maxY + (pt.getX() - minX) * k);
                    if(y > pt.getY()){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        else{
            return false;
        }
    }

    public BoxData() {
    }

    public BoxData(String cityName, List<PointData> cityPointList, PointData maxPoint, PointData minPoint, Integer adcode) {
        this.cityName = cityName;
        this.cityPointList = cityPointList;
        this.maxPoint = maxPoint;
        this.minPoint = minPoint;
        this.adcode = adcode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<PointData> getCityPointList() {
        return cityPointList;
    }

    public void setCityPointList(List<PointData> cityPointList) {
        this.cityPointList = cityPointList;
    }

    public PointData getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(PointData maxPoint) {
        this.maxPoint = maxPoint;
    }

    public PointData getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(PointData minPoint) {
        this.minPoint = minPoint;
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxData boxData = (BoxData) o;

        if (!cityName.equals(boxData.cityName)) return false;
        if (!cityPointList.equals(boxData.cityPointList)) return false;
        if (!maxPoint.equals(boxData.maxPoint)) return false;
        if (!minPoint.equals(boxData.minPoint)) return false;
        return adcode.equals(boxData.adcode);
    }

    @Override
    public int hashCode() {
        int result = cityName.hashCode();
        result = 31 * result + cityPointList.hashCode();
        result = 31 * result + maxPoint.hashCode();
        result = 31 * result + minPoint.hashCode();
        result = 31 * result + adcode.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BoxData{" +
                "cityName='" + cityName + '\'' +
                ", cityPointList=" + cityPointList +
                ", maxPoint=" + maxPoint +
                ", minPoint=" + minPoint +
                ", adcode=" + adcode +
                '}';
    }
}

