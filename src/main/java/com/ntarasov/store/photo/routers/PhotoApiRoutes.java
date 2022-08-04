package com.ntarasov.store.photo.routers;

import com.ntarasov.store.base.routes.BaseApiRouters;

public class PhotoApiRoutes {
    public static final String ROOT = BaseApiRouters.STORE + "/photo";
    public static final String BY_ID = ROOT + "/{id}";

    public static final String DOWNLOAD = "/photo/{id}";
}
