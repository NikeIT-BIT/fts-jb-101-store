package com.ntarasov.store.product.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.product.api.request.ProductRequest;
import com.ntarasov.store.product.api.response.ProductFullResponse;
import com.ntarasov.store.product.api.response.ProductResponse;
import com.ntarasov.store.product.model.ProductDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class ProductMapping {

    public static class RequestMapping extends BaseMapping<ProductRequest, ProductDoc> {

        @Override
        public ProductDoc convert(ProductRequest productRequest) {
            return ProductDoc.builder()
                    .id(productRequest.getId())
                    .categoryId(productRequest.getCategoryId())
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .nutritionFacts(productRequest.getNutritionFacts())
                    .build();
        }


        @Override
        public ProductRequest unMapping(ProductDoc productDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<ProductDoc, ProductResponse> {

        @Override
        public ProductResponse convert(ProductDoc productDoc) {
            return ProductResponse.builder()
                    .id(productDoc.getId().toString())
                    .categoryId(productDoc.getCategoryId().toString())
                    .name(productDoc.getName())
                    .description(productDoc.getDescription())
                    .build();
        }

        @Override
        public ProductDoc unMapping(ProductResponse productResponse) {
            throw new RuntimeException("dont use this");
        }
    }

    public static class ResponseFullMapping extends BaseMapping<ProductDoc, ProductFullResponse> {

        @Override
        public ProductFullResponse convert(ProductDoc productDoc) {
            return ProductFullResponse.builder()
                    .id(productDoc.getId().toString())
                    .categoryId(productDoc.getCategoryId().toString())
                    .name(productDoc.getName())
                    .description(productDoc.getDescription())
                    .nutritionFacts(productDoc.getNutritionFacts())
                    .build();
        }


        @Override
        public ProductDoc unMapping(ProductFullResponse response) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<ProductDoc>, SearchResponse<ProductResponse>>{
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<ProductResponse> convert(SearchResponse<ProductDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<ProductDoc> unMapping(SearchResponse<ProductResponse> productResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();
    private final ResponseFullMapping fullResponse = new ResponseFullMapping();


    public static ProductMapping getInstance() {
        return new ProductMapping();
    }

}
