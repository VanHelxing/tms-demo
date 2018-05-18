package com.vhx.tmsdemo.mapper;

import com.vhx.tmsdemo.entity.system.Stock;

public interface StockDao {

    public void insert(Stock stock);

    /**
     * 查找最后一条数据
     * @return
     */
    public Stock findLast();
}
