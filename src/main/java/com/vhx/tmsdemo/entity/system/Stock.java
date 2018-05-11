package com.vhx.tmsdemo.entity.system;

public class Stock {
    /**  天数 */
    private int day;
    /** 当前库存数量 */
    private double qty;
    /** 当前库存成本*/
    private double cost;
    /**
     * <ul>
     *     <li>0 - 入库</li>
     *     <li>1 - 出库</li>
     * </ul>
     */
    private int type;
    /** 入/出数量 */
    private double changeQty;
    /** 入/出成本 */
    private double changeCost;
    /** 最后一次采购价 */
    private double lastPurchasePrice;
    /** 移动加权平均 */
    private double avagePrice;
    /** 采购均价 */
    private double avagePurchasePrice;
    /** 剩余库存数量 */
    private double resideQty;
    /** 剩余库存成本*/
    private double resideCost;
    /** 库存偏差数量 */
    private double biasQty;

    public Stock() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(double changeQty) {
        this.changeQty = changeQty;
    }

    public double getChangeCost() {
        return changeCost;
    }

    public void setChangeCost(double changeCost) {
        this.changeCost = changeCost;
    }

    public double getLastPurchasePrice() {
        return lastPurchasePrice;
    }

    public void setLastPurchasePrice(double lastPurchasePrice) {
        this.lastPurchasePrice = lastPurchasePrice;
    }

    public double getAvagePurchasePrice() {
        return avagePurchasePrice;
    }

    public void setAvagePurchasePrice(double avagePurchasePrice) {
        this.avagePurchasePrice = avagePurchasePrice;
    }

    public double getResideQty() {
        return resideQty;
    }

    public void setResideQty(double resideQty) {
        this.resideQty = resideQty;
    }

    public double getResideCost() {
        return resideCost;
    }

    public void setResideCost(double resideCost) {
        this.resideCost = resideCost;
    }

    public double getBiasQty() {
        return biasQty;
    }

    public void setBiasQty(double biasQty) {
        this.biasQty = biasQty;
    }

    public double getAvagePrice() {
        return avagePrice;
    }

    public void setAvagePrice(double avagePrice) {
        this.avagePrice = avagePrice;
    }
}
