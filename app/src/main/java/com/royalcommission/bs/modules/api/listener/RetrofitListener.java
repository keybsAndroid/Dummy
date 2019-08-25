package com.royalcommission.bs.modules.api.listener;

/**
 * Created by Prashant on 8/7/2018.
 */
public interface RetrofitListener {
    void onSuccess(Object object);
    void onError(String error);
}
