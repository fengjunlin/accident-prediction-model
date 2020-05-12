package com.fengjunlin.accident.prediction.model.web.model.position;

import com.fengjunlin.accident.prediction.model.web.map.PointData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 多叉树索引节点
 * @Author fengjl
 * @Date 2019/6/17 14:36
 * @Version 1.0
 **/
public class CityKey {
    /**
     *  对应的城市编码
     */
    private Integer adcode;
    /**
     * 这个区域对应的围栏的最大最小值
     */
    private List<MaxAndMinLnglat> lnglats = new ArrayList<>(16);

    /**
     * 检查是否在这个索引点的，最大或者最小的范围中
     *
     * @param pt 待检查的数据
     * @return true：在，false：不在
     */
    public boolean checkInMaxAndMin(PointData pt) {
        for (MaxAndMinLnglat maxAndMinLnglat:lnglats) {
            PointData maxPoint = maxAndMinLnglat.getMaxLngAndLat();
            PointData minPoint = maxAndMinLnglat.getMinLngAndLat();
            if(pt.getX() > minPoint.getX() && pt.getY() > minPoint.getY()){
                if(pt.getX() < maxPoint.getX() && pt.getY() < maxPoint.getY()){
                    return true;
                }
            }
        }

        return false;
    }

    public CityKey() {
    }

    public CityKey(Integer adcode) {
        this.adcode = adcode;
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    public List<MaxAndMinLnglat> getLnglats() {
        return lnglats;
    }

    public void setLnglats(List<MaxAndMinLnglat> lnglats) {
        this.lnglats = lnglats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityKey)) return false;

        CityKey cityKey = (CityKey) o;

        return getAdcode().equals(cityKey.getAdcode());
    }

    @Override
    public int hashCode() {
        return getAdcode().hashCode();
    }

    @Override
    public String toString() {
        return "cityKey{" +
                "adcode=" + adcode +
                ", lnglats=" + lnglats +
                '}';
    }

    public static void main(String[] args) {
        CityKey cityKey = new CityKey();
        cityKey.setAdcode(100);
        CityKey cityKey1 = new CityKey();
        cityKey1.setAdcode(100);
        HashMap<CityKey, Integer> cityKeyIntegerHashMap = new HashMap<>();
        cityKeyIntegerHashMap.put(cityKey,null);
        boolean b = cityKeyIntegerHashMap.containsKey(cityKey1);
        System.out.println(b
        );

    }

}
