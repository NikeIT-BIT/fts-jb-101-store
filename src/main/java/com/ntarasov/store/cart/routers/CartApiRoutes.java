package com.ntarasov.store.cart.routers;

import com.ntarasov.store.base.routes.BaseApiRouters;

public class CartApiRoutes {
    public static final String ROOT = BaseApiRouters.STORE + "/cart";
    public static final String BY_ID = ROOT + "/{id}";

}
