package com.ntarasov.store.price.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.price.api.request.PriceRequest;
import com.ntarasov.store.price.api.request.PriceUpdateRequest;
import com.ntarasov.store.price.api.response.PriceResponse;
import com.ntarasov.store.price.model.PriceDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class PriceMapping {

    public static class RequestMapping extends BaseMapping<PriceRequest, PriceDoc> {

        @Override
        public PriceDoc convert(PriceRequest priceRequest) {
            return PriceDoc.builder()
                    .id(priceRequest.getId())
                    .cityId(priceRequest.getCityId())
                    .productId(priceRequest.getProductId())
                    .price(priceRequest.getPrice())
                    .build();
        }


        @Override
        public PriceRequest unMapping(PriceDoc priceDoc) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class RequestUpdateMapping extends BaseMapping<PriceUpdateRequest, PriceDoc> {

        @Override
        public PriceDoc convert(PriceUpdateRequest request) {
            return PriceDoc.builder()
                    .id(request.getId())
                    .price(request.getPrice())
                    .build();
        }


        @Override
        public PriceUpdateRequest unMapping(PriceDoc priceDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<PriceDoc, PriceResponse> {

        @Override
        public PriceResponse convert(PriceDoc priceDoc) {
            return PriceResponse.builder()
                    .id(priceDoc.getId().toString())
                    .cityId(priceDoc.getCityId().toString())
                    .productId(priceDoc.getProductId().toString())
                    .price(priceDoc.getPrice())
                    .build();
        }

        @Override
        public PriceDoc unMapping(PriceResponse priceResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<PriceDoc>, SearchResponse<PriceResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<PriceResponse> convert(SearchResponse<PriceDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<PriceDoc> unMapping(SearchResponse<PriceResponse> priceResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();
    private final RequestUpdateMapping requestUpdate = new RequestUpdateMapping();


    public static PriceMapping getInstance() {
        return new PriceMapping();
    }

}
