package com.fengjunlin.accident.prediction.model.web.map;

public final class PointData {
    /**
     * 经度 扩大的了10^6,转换为int类型，加快计算速度
     */
    private int x;
    /**
     * 维度 扩大了10^6，转换为int类型,加快计算速度
     */
    private int y;

    /**
     * 计算这个数据点和传入数据点，之间的距离
     *
     * * @param p 传入的数据点
     * @return 两个数据点之间的距离
     */
    public double distance(PointData p){
        double d = 0;
        d = (x - p.getX())*(x - p.getX()) + (y - p.getY())*(y - p.getY());

        return Math.sqrt(d);
    }

    public PointData(){
        this(0, 0);
    }

    public PointData(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public PointData setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public PointData setY(int y) {
        this.y = y;
        return this;
    }
}
