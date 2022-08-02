package com.ntarasov.store.base.mapping;

public abstract class BaseMapping<From, To> {
    public abstract To convert(From from);
    public abstract From unMapping(To to);
}
