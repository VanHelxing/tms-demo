package com.vhx.tmsdemo;

import com.vhx.tmsdemo.entity.system.Stock;
import com.vhx.tmsdemo.mapper.StockDao;
import com.vhx.tmsdemo.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockTest {

    //起始库存数量为100
    private static final double startQty = 100;
    //起始库存成本为200元
    private static final double startCost = 1000;
    //采购单价为10元
    private static final double price = 10;

    @Resource
    private StockDao stockDao;

    /**
     * 涨幅在5元的范围，随机进行采购入库和售卖，每天售卖或者入库数量在200范围内
     */
    @Test
    public void insertTest(){
        double purchaseTotalQty = startQty;    //采购总数量
        double purchaseTotalCost = startCost;  //采购总成本
        double purchaseAvagePrice = NumberUtil.divide(startCost, startQty, 4);  //采购均价
        double lastPrice = price;

        Random randomQty = new Random(200); //出/入库随机函数
        Random randomPrice = new Random(10); //价格随机函数
        Random randomType = new Random(2); //入库还是出库, 0-入库, 1-出库

        double qty;
        double cost;
        double price;
        int type;
        Stock preStock = new Stock();

        //查看100天的数据
        for (int day = 1; day <= 1000; day++){
            type = randomType.nextInt(2);
            qty = randomQty.nextInt(200) + 1;
            Stock stock = new Stock();

            //入库
          if(type == 0){
              price = randomPrice.nextInt(10) + 1;
              purchaseTotalQty += qty;
              purchaseTotalCost += NumberUtil.multiply(qty, price);
              lastPrice = price;
              purchaseAvagePrice = NumberUtil.divide(purchaseTotalCost,purchaseTotalQty,4);

              if (day == 1){
                  stock.setQty(startQty);
                  stock.setCost(startCost);
                  stock.setType(type);
                  stock.setChangeQty(qty);
                  stock.setChangeCost(NumberUtil.multiply(qty, price));
                  stock.setLastPurchasePrice(lastPrice);
                  stock.setAvagePurchasePrice(purchaseAvagePrice);
                  stock.setResideQty(NumberUtil.add(startQty, qty));
                  stock.setResideCost(NumberUtil.add(startCost, NumberUtil.multiply(qty, price)));
                  preStock = stock;
              }else{
                  stock.setQty(preStock.getResideQty());
                  stock.setCost(preStock.getResideCost());
                  stock.setType(type);
                  stock.setChangeQty(qty);
                  stock.setChangeCost(NumberUtil.multiply(qty,price));
                  stock.setLastPurchasePrice(lastPrice);
                  stock.setAvagePurchasePrice(purchaseAvagePrice);
                  stock.setResideQty(NumberUtil.add(preStock.getResideQty(), qty));
                  stock.setResideCost(NumberUtil.add(preStock.getResideCost(), NumberUtil.multiply(qty, price)));
                  preStock = stock;
              }
          }
          //出库
          else{
              if (day == 1){
                stock.setQty(startQty);
                stock.setCost(startCost);
                stock.setType(type);
                stock.setChangeQty(qty);
                stock.setChangeCost(NumberUtil.multiply(qty, purchaseAvagePrice));
                stock.setLastPurchasePrice(lastPrice);
                stock.setAvagePurchasePrice(purchaseAvagePrice);
                stock.setResideQty(NumberUtil.subtract(startQty, qty));
                stock.setResideCost(NumberUtil.subtract(startQty, NumberUtil.multiply(qty, purchaseAvagePrice)));
                preStock = stock;
              }else{
                  stock.setQty(preStock.getResideQty());
                  stock.setCost(preStock.getResideCost());
                  stock.setType(type);
                  stock.setChangeQty(qty);
                  stock.setChangeCost(NumberUtil.multiply(qty, purchaseAvagePrice));
                  stock.setLastPurchasePrice(lastPrice);
                  stock.setAvagePurchasePrice(purchaseAvagePrice);
                  stock.setResideQty(NumberUtil.subtract(preStock.getResideQty(), qty));
                  stock.setResideCost(NumberUtil.subtract(preStock.getResideCost(), NumberUtil.multiply(qty, purchaseAvagePrice)));
                  preStock = stock;
              }
          }


          stockDao.insert(stock);
        }
    }


}
