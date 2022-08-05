package com.ntarasov.store.product.routers;

import com.ntarasov.store.base.routes.BaseApiRouters;

public class ProductApiRoutes {
    public static final String ROOT = BaseApiRouters.STORE + "/product";
    public static final String BY_ID = ROOT + "/{id}";

}
