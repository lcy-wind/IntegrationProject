package com.teamwork.integrationproject.common.utils;

/**
 * 位置计算工具类
 *
 * @author pengh
 * @date 2020/10/12 15:09:13
 */
public class LBSUtils {

    //赤道半径(单位m)
    private static final double EARTH_RADIUS = 6378137;

    //转化为弧度(rad)
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离
     *
     * @param lat1 纬度
     * @param lng1 经度
     * @param lat2 纬度
     * @param lng2 经度
     * @return 距离：单位为米
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double Lat1 = rad(lat1);
        double Lat2 = rad(lat2);
        double a = Lat1 - Lat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
