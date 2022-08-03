package com.ntarasov.store.category.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.category.api.request.CategoryRequest;
import com.ntarasov.store.category.api.response.CategoryResponse;
import com.ntarasov.store.category.model.CategoryDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class CategoryMapping {

    public static class RequestMapping extends BaseMapping<CategoryRequest, CategoryDoc> {

        @Override
        public CategoryDoc convert(CategoryRequest categoryRequest) {
            return CategoryDoc.builder()
                    .id(categoryRequest.getId())
                    .name(categoryRequest.getName())
                    .build();
        }


        @Override
        public CategoryRequest unMapping(CategoryDoc categoryDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<CategoryDoc, CategoryResponse> {

        @Override
        public CategoryResponse convert(CategoryDoc categoryDoc) {
            return CategoryResponse.builder()
                    .id(categoryDoc.getId().toString())
                    .name(categoryDoc.getName())
                    .build();
        }

        @Override
        public CategoryDoc unMapping(CategoryResponse categoryResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<CategoryDoc>, SearchResponse<CategoryResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<CategoryResponse> convert(SearchResponse<CategoryDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<CategoryDoc> unMapping(SearchResponse<CategoryResponse> categoryResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static CategoryMapping getInstance() {
        return new CategoryMapping();
    }

}
