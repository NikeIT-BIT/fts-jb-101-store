package com.ntarasov.store.street.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.street.api.request.StreetRequest;
import com.ntarasov.store.street.api.response.StreetResponse;
import com.ntarasov.store.street.model.StreetDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class StreetMapping {

    public static class RequestMapping extends BaseMapping<StreetRequest, StreetDoc> {

        @Override
        public StreetDoc convert(StreetRequest streetRequest) {
            return StreetDoc.builder()
                    .id(streetRequest.getId())
                    .cityId(streetRequest.getCityId())
                    .name(streetRequest.getName())
                    .build();
        }


        @Override
        public StreetRequest unMapping(StreetDoc streetDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<StreetDoc, StreetResponse> {

        @Override
        public StreetResponse convert(StreetDoc streetDoc) {
            return StreetResponse.builder()
                    .id(streetDoc.getId().toString())
                    .cityId(streetDoc.getCityId().toString())
                    .name(streetDoc.getName())
                    .build();
        }

        @Override
        public StreetDoc unMapping(StreetResponse streetResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<StreetDoc>, SearchResponse<StreetResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<StreetResponse> convert(SearchResponse<StreetDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<StreetDoc> unMapping(SearchResponse<StreetResponse> streetResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static StreetMapping getInstance() {
        return new StreetMapping();
    }

}
