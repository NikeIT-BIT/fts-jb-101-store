package com.ntarasov.store.gues.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.gues.api.request.GuesRequest;
import com.ntarasov.store.gues.api.response.GuesResponse;
import com.ntarasov.store.gues.model.GuesDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class GuesMapping {

    public static class RequestMapping extends BaseMapping<GuesRequest, GuesDoc> {

        @Override
        public GuesDoc convert(GuesRequest guesRequest) {
            return GuesDoc.builder()
                    .id(guesRequest.getId())
                    .name(guesRequest.getName())
                    .phone(guesRequest.getPhone())
                    .time(guesRequest.getTime())
                    .cartId(guesRequest.getCartId())
                    .streetId(guesRequest.getStreetId())
                    .address(guesRequest.getAddress())
                    .paymentMethod(guesRequest.getPaymentMethod())
                    .build();
        }


        @Override
        public GuesRequest unMapping(GuesDoc guesDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<GuesDoc, GuesResponse> {

        @Override
        public GuesResponse convert(GuesDoc guesDoc) {
            return GuesResponse.builder()
                    .id(guesDoc.getId().toString())
                    .name(guesDoc.getName())
                    .phone(guesDoc.getPhone())
                    .time(guesDoc.getTime())
                    .cartId(guesDoc.getCartId().toString())
                    .streetId(guesDoc.getStreetId().toString())
                    .address(guesDoc.getAddress())
                    .paymentMethod(guesDoc.getPaymentMethod())
                    .build();
        }

        @Override
        public GuesDoc unMapping(GuesResponse guesResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<GuesDoc>, SearchResponse<GuesResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<GuesResponse> convert(SearchResponse<GuesDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<GuesDoc> unMapping(SearchResponse<GuesResponse> guesResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static GuesMapping getInstance() {
        return new GuesMapping();
    }

}
