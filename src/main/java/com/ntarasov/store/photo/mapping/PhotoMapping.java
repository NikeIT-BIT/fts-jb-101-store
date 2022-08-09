package com.ntarasov.store.photo.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.photo.api.request.PhotoRequest;
import com.ntarasov.store.photo.api.response.PhotoResponse;
import com.ntarasov.store.photo.model.PhotoDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class PhotoMapping {

    public static class RequestMapping extends BaseMapping<PhotoRequest, PhotoDoc> {

        @Override
        public PhotoDoc convert(PhotoRequest photoRequest) {
            return PhotoDoc.builder()
                    .id(photoRequest.getId())
                    .name(photoRequest.getName())
                    .contentType(photoRequest.getContentType())
                    .productId(photoRequest.getProductId())
                    .build();
        }


        @Override
        public PhotoRequest unMapping(PhotoDoc photoDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<PhotoDoc, PhotoResponse> {

        @Override
        public PhotoResponse convert(PhotoDoc photoDoc) {
            return PhotoResponse.builder()
                    .id(photoDoc.getId().toString())
                    .name(photoDoc.getName())
                    .contentType(photoDoc.getContentType())
                    .productId(photoDoc.getProductId().toString())
                    .build();
        }

        @Override
        public PhotoDoc unMapping(PhotoResponse photoResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<PhotoDoc>, SearchResponse<PhotoResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<PhotoResponse> convert(SearchResponse<PhotoDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<PhotoDoc> unMapping(SearchResponse<PhotoResponse> photoResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static PhotoMapping getInstance() {
        return new PhotoMapping();
    }

}
