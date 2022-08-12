package com.ntarasov.store.cart.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.cart.api.request.CartRequest;
import com.ntarasov.store.cart.api.request.CartUpdateRequest;
import com.ntarasov.store.cart.api.response.CartResponse;
import com.ntarasov.store.cart.model.CartDoc;
import lombok.Getter;

import java.util.stream.Collectors;
import java.util.Map;

@Getter

public class CartMapping {

    public static class RequestMapping extends BaseMapping<CartRequest, CartDoc> {

        @Override
        public CartDoc convert(CartRequest cartRequest) {
            return CartDoc.builder()
                    .id(cartRequest.getId())
                    .cityId(cartRequest.getCityId())
                    .totalCost(cartRequest.getTotalCost())
                    .products(cartRequest.getProducts())
                    .build();
        }


        @Override
        public CartRequest unMapping(CartDoc cartDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class RequestUpdateMapping extends BaseMapping<CartUpdateRequest, CartDoc> {

        @Override
        public CartDoc convert(CartUpdateRequest cartRequest) {
            return CartDoc.builder()
                    .id(cartRequest.getId())
                    .totalCost(cartRequest.getTotalCost())
                    .products(cartRequest.getProducts())
                    .build();
        }


        @Override
        public CartUpdateRequest unMapping(CartDoc cartDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<CartDoc, CartResponse> {

        @Override
        public CartResponse convert(CartDoc cartDoc) {
            return CartResponse.builder()
                    .id(cartDoc.getId().toString())
                    .cityId(cartDoc.getCityId().toString())
                    .totalCost(cartDoc.getTotalCost())
                    .products(cartDoc.getProducts()
                            .entrySet()
                            .stream()
                            .collect(Collectors.toMap(
                                    e -> e.getKey().toString(),
                                    Map.Entry::getValue
                            )))
                    .build();
        }

        @Override
        public CartDoc unMapping(CartResponse cartResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<CartDoc>, SearchResponse<CartResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<CartResponse> convert(SearchResponse<CartDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<CartDoc> unMapping(SearchResponse<CartResponse> cartResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();
    private final RequestUpdateMapping requestUpdate = new RequestUpdateMapping();


    public static CartMapping getInstance() {
        return new CartMapping();
    }

}
