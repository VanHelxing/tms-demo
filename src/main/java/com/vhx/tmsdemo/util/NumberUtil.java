package com.vhx.tmsdemo.util;

import java.math.BigDecimal;

public class NumberUtil {
	 /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale, int roundingMode) {   
        BigDecimal bd = new BigDecimal(value);   
        bd = bd.setScale(scale, roundingMode);   
        double d = bd.doubleValue();   
        bd = null;   
        return d;   
    }   
    
    /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale) {   
    	return round(value, scale, BigDecimal.ROUND_HALF_UP);   
    }   

     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double add(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.add(bd2).doubleValue(); 
    } 

    /**
     * 求和
     * @param ds
     * @return
     */
    public static double sum(double... ds){
    	BigDecimal total = new BigDecimal("0");
    	for(double d : ds){
    		total = total.add(new BigDecimal(Double.toString(d)));
    	}
    	return total.doubleValue();
    }

    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double subtract(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double multiply(double... ds){ 
    	BigDecimal total = null;
        for(int i = 0; i < ds.length; i++){
        	if(i == 0){
        		total = new BigDecimal(Double.toString(ds[i]));
        	}else{
        		total = total.multiply(new BigDecimal(Double.toString(ds[i])));
        	}
        }
        return total == null ? 0d : total.doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double divide(double d1,double d2,int scale){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    }

    /**
     * double 除法 
     * @param d1
     * @param d2
     * @return
     */
	public static double divide(double d1, double d2) {
		 BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
	        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
	        return bd1.divide(bd2).doubleValue(); 
	} 
	
	/**
	 * 如果d为null则返回defaultValue
	 * @param d
	 * @param dv
	 * @return
	 */
	public static double defaultValue(Double d, double defaultValue){
		if(d == null){
			return defaultValue;
		}
		return d;
	}
}
