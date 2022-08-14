package com.ntarasov.store.order.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;



public enum Status {
    PROCESSING, PREPARED, TRANSIT, DELIVERED, CANCELLED
}
