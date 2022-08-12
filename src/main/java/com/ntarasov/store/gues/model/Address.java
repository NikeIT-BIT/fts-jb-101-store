package com.ntarasov.store.gues.model;

import org.bson.types.ObjectId;

public class Address extends GuesDoc{
    private String home;
    private String flat;
    private String entrance;
    private String floor;
    private ObjectId streetId;
}
