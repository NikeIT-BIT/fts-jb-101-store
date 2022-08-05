package com.ntarasov.store.city.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.city.api.request.CityRequest;
import com.ntarasov.store.city.api.response.CityResponse;
import com.ntarasov.store.city.model.CityDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class CityMapping {

    public static class RequestMapping extends BaseMapping<CityRequest, CityDoc> {

        @Override
        public CityDoc convert(CityRequest cityRequest) {
            return CityDoc.builder()
                    .id(cityRequest.getId())
                    .name(cityRequest.getName())
                    .minTimeDelivery(cityRequest.getMinTimeDelivery())
                    .priceDelivery(cityRequest.getPriceDelivery())
                    .build();
        }


        @Override
        public CityRequest unMapping(CityDoc cityDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<CityDoc, CityResponse> {

        @Override
        public CityResponse convert(CityDoc cityDoc) {
            return CityResponse.builder()
                    .id(cityDoc.getId().toString())
                    .name(cityDoc.getName())
                    .minTimeDelivery(cityDoc.getMinTimeDelivery())
                    .priceDelivery(cityDoc.getPriceDelivery())
                    .build();
        }

        @Override
        public CityDoc unMapping(CityResponse cityResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<CityDoc>, SearchResponse<CityResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<CityResponse> convert(SearchResponse<CityDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<CityDoc> unMapping(SearchResponse<CityResponse> cityResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static CityMapping getInstance() {
        return new CityMapping();
    }

}
