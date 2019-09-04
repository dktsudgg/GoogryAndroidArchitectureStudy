package com.architecture.study.data.repository

import com.architecture.study.data.source.CoinRemoteDataSourceListener
import com.architecture.study.network.RetrofitInstanceCallBack
import com.architecture.study.network.model.MarketResponse
import com.architecture.study.network.model.TickerResponse

interface CoinRepository {
    fun setRetrofitInstance(callback: RetrofitInstanceCallBack)

    fun getMarketList(listener: CoinRemoteDataSourceListener<MarketResponse>)

    fun getTickerList(marketNames: String, listener: CoinRemoteDataSourceListener<TickerResponse>)
}