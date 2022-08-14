package com.ntarasov.store.order.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.order.api.request.OrderRequest;
import com.ntarasov.store.order.api.request.OrderUpdateRequest;
import com.ntarasov.store.order.api.response.OrderResponse;
import com.ntarasov.store.order.model.OrderDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class OrderMapping {

    public static class RequestMapping extends BaseMapping<OrderRequest, OrderDoc> {

        @Override
        public OrderDoc convert(OrderRequest orderRequest) {
            return OrderDoc.builder()
                    .id(orderRequest.getId())
                    .guesId(orderRequest.getGuesId())
                    .numberOrder(orderRequest.getNumberOrder())
                    .status(orderRequest.getStatus())
                    .build();
        }


        @Override
        public OrderRequest unMapping(OrderDoc orderDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class RequestUpdateMapping extends BaseMapping<OrderUpdateRequest, OrderDoc> {

        @Override
        public OrderDoc convert(OrderUpdateRequest orderRequest) {
            return OrderDoc.builder()
                    .status(orderRequest.getStatus())
                    .build();
        }


        @Override
        public OrderUpdateRequest unMapping(OrderDoc orderDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<OrderDoc, OrderResponse> {

        @Override
        public OrderResponse convert(OrderDoc orderDoc) {
            return OrderResponse.builder()
                    .id(orderDoc.getId().toString())
                    .guesId(orderDoc.getGuesId().toString())
                    .numberOrder(orderDoc.getNumberOrder())
                    .status(orderDoc.getStatus())
                    .build();
        }

        @Override
        public OrderDoc unMapping(OrderResponse orderResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<OrderDoc>, SearchResponse<OrderResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<OrderResponse> convert(SearchResponse<OrderDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<OrderDoc> unMapping(SearchResponse<OrderResponse> orderResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();
    private final RequestUpdateMapping updateRequest = new RequestUpdateMapping();


    public static OrderMapping getInstance() {
        return new OrderMapping();
    }

}
