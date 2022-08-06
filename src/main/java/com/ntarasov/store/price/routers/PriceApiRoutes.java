package com.ntarasov.store.price.routers;

import com.ntarasov.store.base.routes.BaseApiRouters;

public class PriceApiRoutes {
    public static final String ROOT = BaseApiRouters.STORE + "/price";
    public static final String BY_ID = ROOT + "/{id}";

}
