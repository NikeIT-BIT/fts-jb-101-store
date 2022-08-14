package com.ntarasov.store.order.routers;

import com.ntarasov.store.base.routes.BaseApiRouters;

public class OrderApiRoutes {
    public static final String ROOT = BaseApiRouters.STORE + "/order";
    public static final String BY_ID = ROOT + "/{id}";

}
