package com.vhx.tmsdemo;


import com.vhx.tmsdemo.entity.system.Stock;
import com.vhx.tmsdemo.mapper.StockDao;
import com.vhx.tmsdemo.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 郭总提出的库存成本计算方式:
 *
 *  1. 当库存数量大于0时, 按照移动加权计算
 *  2. 当库存数量小于0时, 出库成本 = 库存成本, 成本单价 = 库存成本 / 出库数量
 *
 * @author : yangjunqing / yangjunqing@zhimadi.cn
 * @version : 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GuoZongSolution {

    @Resource
    private StockDao stockDao;

    //起始库存数量
    private static final double START_QTY = 50l;
    //起始库存成本
    private static final double START_COST = 1000l;
    //起始采购单价/移动加权平均
    private static final double PURCHASE_PRICE = 20l;


    @Test
    public void run(){

        double purchasePrice = PURCHASE_PRICE;  //采购成本单价
        double avgPrice = PURCHASE_PRICE;       //移动加权平均单价

        Stock preStock = new Stock();
        preStock.setResideCost(START_COST);
        preStock.setResideQty(START_QTY);

        int operatFlag = 0;                     //0 - 涨价, 1 - 降价
        int type;                               //0 - 入库，1 - 出库
        boolean isCheck = false;                //是否进行盘点
        double baisQty = 0l; //库存偏差
        double qty;
        double cost;
        double priceOffset;

        Random randomQty = new Random(200);
        Random randomPrice = new Random(5);
        Random randomType = new Random(2);
        Random randomCheck = new Random(2);

        //模拟开始
        for (int day = 1; day <= 365; day++){
            type = randomType.nextInt(2);
            qty = randomQty.nextInt(200) + 1;
            Stock stock = new Stock();

            //入库
            if (type == 0){
                priceOffset = randomPrice.nextInt(5) + 1;

                //采购单价超过30元就开始减价
                if (purchasePrice > 30){
                    operatFlag = 1;
                }
                //采购单价低于10元就开始加价
                if (purchasePrice < 10){
                    operatFlag = 0;
                }

                //本次的采购价格
                if (operatFlag == 0){
                    purchasePrice += priceOffset;
                }else{
                    purchasePrice -= priceOffset;
                }

                //本次采购的成本
                cost = NumberUtil.multiply(purchasePrice, qty);

                //库存数量大于0则采用移动加权
                if (NumberUtil.add(preStock.getResideQty(), qty) > 0) {
                    avgPrice = NumberUtil.divide(NumberUtil.add(cost, preStock.getResideCost()), NumberUtil.add(qty, preStock.getResideQty()), 4);
                }else {
                    avgPrice = 0l;
                }
                stock.setQty(preStock.getResideQty());
                stock.setCost(preStock.getResideCost());
                stock.setType(type);
                stock.setChangeQty(qty);
                stock.setChangeCost(cost);
                stock.setLastPurchasePrice(purchasePrice);
                stock.setAvagePrice(avgPrice);
                stock.setResideQty(NumberUtil.add(qty, preStock.getResideQty()));
                stock.setResideCost(NumberUtil.add(cost, preStock.getResideCost()));
                stock.setBiasQty(baisQty);
                preStock = stock;
            }
            //出库
            else{
                if (NumberUtil.subtract(preStock.getResideQty(), qty) > 0){
                    cost = NumberUtil.multiply(qty, avgPrice);
                    stock.setResideCost(NumberUtil.subtract(preStock.getResideCost(), cost));
                } else{
                    cost = preStock.getResideCost();
                    baisQty = baisQty + NumberUtil.subtract(preStock.getResideQty(), qty);
                    avgPrice = 0l;
                    stock.setResideCost(0);
                }

                stock.setQty(preStock.getResideQty());
                stock.setCost(preStock.getResideCost());
                stock.setType(type);
                stock.setChangeQty(qty);
                stock.setChangeCost(cost);
                stock.setLastPurchasePrice(purchasePrice);
                stock.setAvagePrice(avgPrice);
                stock.setResideQty(NumberUtil.subtract(preStock.getResideQty(), qty));
                stock.setBiasQty(baisQty);
                preStock = stock;
            }

            stockDao.insert(stock);


            //每7天盘点一次
            if(day % 7 == 0){
                isCheck = true;
            }else{
                isCheck = false;
            }

            //是否需要盘点
            if(isCheck){
                Stock lastStock = stockDao.findLast();
                if (lastStock == null){
                    throw new RuntimeException("未查询到最后一条记录！");
                }
                // 存在库片偏差则需盘点
                if (baisQty != 0){
//                    type = randomCheck.nextInt(2) + 2; //2-盘点, 3-采购调数量
                    Stock checkStock = new Stock();
                    //盘点调整, 数量为库存偏差数量,成本为0
                    cost = 0;
                    baisQty = 0;
                    checkStock.setQty(lastStock.getResideQty());
                    checkStock.setCost(lastStock.getResideCost());
                    checkStock.setType(2);
                    checkStock.setChangeQty(-lastStock.getBiasQty());
                    checkStock.setChangeCost(cost);
                    checkStock.setLastPurchasePrice(purchasePrice);
                    checkStock.setAvagePrice(avgPrice);
                    checkStock.setResideQty(0);
                    checkStock.setResideCost(lastStock.getResideCost());
                    stock.setBiasQty(baisQty);
                    stockDao.insert(checkStock);
                    preStock = checkStock;

                }
            }


        }
    }


}
