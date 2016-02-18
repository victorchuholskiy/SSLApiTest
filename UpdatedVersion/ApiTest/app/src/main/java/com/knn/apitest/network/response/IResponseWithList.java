package com.knn.apitest.network.response;

import java.util.List;

/**
 * Created by USER on 18.02.2016.
 */
public  interface  IResponseWithList <T> {
    public List<T> getResults();

}
