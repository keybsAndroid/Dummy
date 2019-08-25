package com.qanawat.modules.api.network;


import com.qanawat.database.catalog.XmlCatalogResponse;
import com.qanawat.modules.api.model.MerchantBalanceResponse;
import com.qanawat.modules.api.model.pin.Acknowledge;
import com.qanawat.modules.api.model.pin.DownloadPins;
import com.qanawat.modules.api.model.response.confirm.ConfirmTopup;
import com.qanawat.modules.api.model.response.confirm.ConfirmTransaction;
import com.qanawat.modules.api.model.response.confirm.LastTopup;
import com.qanawat.modules.api.model.response.giftcard.GiftCardProducts;
import com.qanawat.modules.api.model.response.report.DetReport;
import com.qanawat.modules.api.model.response.report.SumReport;
import com.qanawat.modules.api.model.response.transaction.LastTransactions;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Created by Prashant on 5/14/2018.
 */
public interface WebService {

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.MERCHANT_BALANCE)
    Call<MerchantBalanceResponse> merchantBalance(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.DOWNLOAD_PIN)
    Call<DownloadPins> downloadPin(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.DOWNLOAD_TOPUP_CATALOG)
    Call<XmlCatalogResponse> downloadTopupCatalog(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.DOWNLOAD_GIFT_CARD_CATALOG)
    Call<XmlCatalogResponse> downloadGiftCardsCatalog(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.ACKNOWLEDGE_TRANSACTION)
    Call<Acknowledge> acknowledgeTransaction(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.SUMMARY_REPORT)
    Call<Object> getSummaryReport(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.DETAIL_SUMMARY_REPORT)
    Call<Object> getDetailSummaryReport(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.SALES_REPORT)
    Call<SumReport> getSumReport(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.TRANSACTION_REPORT)
    Call<DetReport> getDetReport(@QueryMap Map<String, String> params);


    @Headers({"Accept: application/xml"})
    @GET(EndPoints.LAST_TRANSACTIONS)
    Call<LastTransactions> getLastTransactions(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.GET_PRODUCTS)
    Call<GiftCardProducts> getProducts(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.CONFIRMATION_TRANSACTION)
    Call<ConfirmTransaction> getConfirmTransaction(@QueryMap Map<String, String> params);


    @Headers({"Accept: application/xml"})
    @GET(EndPoints.SALESMAN_TOPUP)
    Call<ConfirmTopup> salesmanTopup(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.SALESMAN_LAST_TOPUP)
    Call<LastTopup> salesmanLastTopup(@QueryMap Map<String, String> params);

    @Headers({"Accept: application/xml"})
    @GET(EndPoints.SALESMAN_REVERSE_TOPUP)
    Call<LastTopup> salesmanReverseTopup(@QueryMap Map<String, String> params);

}
