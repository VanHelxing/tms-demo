package com.vhx.tmsdemo;

import com.vhx.tmsdemo.entity.system.Stock;
import com.vhx.tmsdemo.mapper.StockDao;
import com.vhx.tmsdemo.util.NumberUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
     * 移动加权平均 + 库存偏差
     */
    @Test
    public void YiDongJiaQuanPingJun(){
        double purchaseTotalQty = startQty;    //采购总数量
        double purchaseTotalCost = startCost;  //采购总成本
        double purchaseAvagePrice = NumberUtil.divide(startCost, startQty, 4);  //采购均价
        double lastPurchasePrice = price;   //最后一次采购成本价格
        double lastPrice = price; //最后一次入库计算出的成本价
        double baisQty = 0l; //库存偏差

        Random randomQty = new Random(200); //出/入库随机函数
        Random randomPrice = new Random(10); //价格随机函数
        Random randomType = new Random(2); //入库还是出库, 0-入库, 1-出库

        double qty;
        double price;
        double cost;
        int type;
        Stock preStock = new Stock();

        //模拟365天的数据
        for (int day = 1; day <= 365; day++){
            type = randomType.nextInt(2);
            qty = randomQty.nextInt(200) + 1;
            Stock stock = new Stock();

            //入库
            if (type == 0){
                price = randomPrice.nextInt(10) + 1;
                lastPurchasePrice = price;
                cost = NumberUtil.multiply(price, qty);
                purchaseTotalQty += qty;
                purchaseTotalCost += cost;
                purchaseAvagePrice = NumberUtil.divide(purchaseTotalCost,purchaseTotalQty,4);

                if (day == 1){
                    lastPrice = NumberUtil.divide(NumberUtil.add(startCost, cost), NumberUtil.add(startQty, qty), 4);
                    stock.setQty(startQty);
                    stock.setCost(startCost);
                    stock.setType(type);
                    stock.setChangeQty(qty);
                    stock.setChangeCost(cost);
                    stock.setLastPurchasePrice(lastPurchasePrice);
                    stock.setAvagePrice(purchaseAvagePrice);
                    stock.setAvagePurchasePrice(lastPrice);
                    stock.setBiasQty(baisQty);
                    stock.setResideQty(NumberUtil.add(startQty, qty));
                    stock.setResideCost(NumberUtil.add(startCost, cost));
                    preStock = stock;
                }else{
                    lastPrice = NumberUtil.divide(NumberUtil.add(preStock.getResideCost(), cost), NumberUtil.add(preStock.getResideQty(), qty), 4);
                    stock.setQty(preStock.getResideQty());
                    stock.setCost(preStock.getResideCost());
                    stock.setType(type);
                    stock.setChangeQty(qty);
                    stock.setChangeCost(cost);
                    stock.setLastPurchasePrice(lastPurchasePrice);
                    stock.setAvagePrice(purchaseAvagePrice);
                    stock.setAvagePurchasePrice(lastPrice);
                    stock.setBiasQty(baisQty);
                    stock.setResideQty(NumberUtil.add(preStock.getResideQty(), qty));
                    stock.setResideCost(NumberUtil.add(preStock.getResideCost(), cost));
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
                    stock.setChangeCost(NumberUtil.multiply(qty, lastPrice));
                    stock.setLastPurchasePrice(lastPurchasePrice);
                    stock.setAvagePurchasePrice(lastPrice);
                    stock.setAvagePrice(purchaseAvagePrice);
                    if (NumberUtil.subtract(startQty, qty) < 0){
                        baisQty += NumberUtil.subtract(startQty, qty);
                        stock.setResideQty(0);
                        stock.setResideCost(0);
                        stock.setBiasQty(baisQty);
                    }else{
                        stock.setResideQty(NumberUtil.subtract(startQty, qty));
                        stock.setResideCost(NumberUtil.subtract(startCost, NumberUtil.multiply(qty, lastPrice)));
                        stock.setBiasQty(baisQty);
                    }
                    preStock = stock;
                }else{
                    stock.setQty(preStock.getResideQty());
                    stock.setCost(preStock.getResideCost());
                    stock.setType(type);
                    stock.setChangeQty(qty);
                    stock.setChangeCost(NumberUtil.multiply(qty, lastPrice));
                    stock.setLastPurchasePrice(lastPurchasePrice);
                    stock.setAvagePurchasePrice(lastPrice);
                    stock.setAvagePrice(purchaseAvagePrice);
                    if (NumberUtil.subtract(preStock.getResideQty(), qty) < 0){
                        baisQty += NumberUtil.subtract(preStock.getResideQty(), qty);
                        stock.setResideQty(0);
                        stock.setResideCost(0);
                        stock.setBiasQty(baisQty);
                    }else{
                        stock.setResideQty(NumberUtil.subtract(preStock.getResideQty(), qty));
                        stock.setBiasQty(baisQty);
                        stock.setResideCost(NumberUtil.subtract(preStock.getResideCost(), NumberUtil.multiply(qty, lastPrice)));
                    }
                    preStock = stock;

                }

            }
            stockDao.insert(stock);
        }
    }




//    /**
//     * 涨幅在10元的范围，随机进行采购入库和售卖，每天售卖或者入库数量在200范围内
//     */
//    @Test
//    public void insertTest(){
//        double purchaseTotalQty = startQty;    //采购总数量
//        double purchaseTotalCost = startCost;  //采购总成本
//        double purchaseAvagePrice = NumberUtil.divide(startCost, startQty, 4);  //采购均价
//        double lastPrice = price;
//        double outQty = 0l; //总出数量
//        double baisQty = 0l; //偏差数量
//
//        Random randomQty = new Random(200); //出/入库随机函数
//        Random randomPrice = new Random(10); //价格随机函数
//        Random randomType = new Random(2); //入库还是出库, 0-入库, 1-出库
//
//        double qty;
//        double cost;
//        double price;
//        int type;
//        Stock preStock = new Stock();
//
//        //查看365天的数据
//        for (int day = 1; day <= 365; day++){
//            type = randomType.nextInt(2);
//            qty = randomQty.nextInt(200) + 1;
//            Stock stock = new Stock();
//
//            //入库
//          if(type == 0){
//              price = randomPrice.nextInt(10) + 1;
//              purchaseTotalQty += qty;
//              purchaseTotalCost += NumberUtil.multiply(qty, price);
//              lastPrice = price;
//              purchaseAvagePrice = NumberUtil.divide(purchaseTotalCost,purchaseTotalQty,4);
//
//              if (day == 1){
//                  stock.setQty(startQty);
//                  stock.setCost(startCost);
//                  stock.setType(type);
//                  stock.setChangeQty(qty);
//                  stock.setChangeCost(NumberUtil.multiply(qty, price));
//                  stock.setLastPurchasePrice(lastPrice);
//                  stock.setAvagePurchasePrice(purchaseAvagePrice);
//                  stock.setResideQty(NumberUtil.add(startQty, qty));
//                  stock.setResideCost(NumberUtil.add(startCost, NumberUtil.multiply(qty, price)));
//                  if (NumberUtil.subtract(purchaseTotalQty,outQty) < 0){
//                      stock.setBiasQty(NumberUtil.subtract(purchaseTotalQty,outQty) );
//                  }else{
//                      stock.setBiasQty(0);
//                  }
//                  preStock = stock;
//              }else{
//                  stock.setQty(preStock.getResideQty());
//                  stock.setCost(preStock.getResideCost());
//                  stock.setType(type);
//                  stock.setChangeQty(qty);
//                  stock.setChangeCost(NumberUtil.multiply(qty,price));
//                  stock.setLastPurchasePrice(lastPrice);
//                  stock.setAvagePurchasePrice(purchaseAvagePrice);
//                  stock.setResideQty(NumberUtil.add(preStock.getResideQty(), qty));
//                  stock.setResideCost(NumberUtil.add(preStock.getResideCost(), NumberUtil.multiply(qty, price)));
//                  if (NumberUtil.subtract(purchaseTotalQty,outQty) < 0){
//                      stock.setBiasQty(NumberUtil.subtract(purchaseTotalQty,outQty) );
//                  }else{
//                      stock.setBiasQty(0);
//                  }
//                  preStock = stock;
//              }
//          }
//          //出库
//          else{
//              outQty += qty;
//
//              if (day == 1){
//                stock.setQty(startQty);
//                stock.setCost(startCost);
//                stock.setType(type);
//                stock.setChangeQty(qty);
//                stock.setChangeCost(NumberUtil.multiply(qty, purchaseAvagePrice));
//                stock.setLastPurchasePrice(lastPrice);
//                stock.setAvagePurchasePrice(purchaseAvagePrice);
//                stock.setResideQty(NumberUtil.subtract(startQty, qty));
//                stock.setResideCost(NumberUtil.subtract(startQty, NumberUtil.multiply(qty, purchaseAvagePrice)));
//
//                  if (NumberUtil.subtract(purchaseTotalQty,outQty) < 0){
//                      stock.setBiasQty(NumberUtil.subtract(purchaseTotalQty,outQty) );
//                  }else{
//                      stock.setBiasQty(0);
//                  }
//
//                preStock = stock;
//              }else{
//                  stock.setQty(preStock.getResideQty());
//                  stock.setCost(preStock.getResideCost());
//                  stock.setType(type);
//                  stock.setChangeQty(qty);
//                  stock.setChangeCost(NumberUtil.multiply(qty, purchaseAvagePrice));
//                  stock.setLastPurchasePrice(lastPrice);
//                  stock.setAvagePurchasePrice(purchaseAvagePrice);
//                  stock.setResideQty(NumberUtil.subtract(preStock.getResideQty(), qty));
//                  stock.setResideCost(NumberUtil.subtract(preStock.getResideCost(), NumberUtil.multiply(qty, purchaseAvagePrice)));
//                  if (NumberUtil.subtract(purchaseTotalQty,outQty) < 0){
//                      stock.setBiasQty(NumberUtil.subtract(purchaseTotalQty,outQty) );
//                  }else{
//                      stock.setBiasQty(0);
//                  }
//                  preStock = stock;
//              }
//          }
//
//
//          stockDao.insert(stock);
//        }
//    }


}
