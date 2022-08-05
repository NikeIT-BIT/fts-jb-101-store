package com.ntarasov.store.admin.mapping;

import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.base.mapping.BaseMapping;
import com.ntarasov.store.admin.api.request.AdminRequest;
import com.ntarasov.store.admin.api.response.AdminResponse;
import com.ntarasov.store.admin.model.AdminDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter

public class AdminMapping {

    public static class RequestMapping extends BaseMapping<AdminRequest, AdminDoc> {

        @Override
        public AdminDoc convert(AdminRequest adminRequest) {
            return AdminDoc.builder()
                    .id(adminRequest.getId())
                    .email(adminRequest.getEmail())
                    .password(adminRequest.getPassword())
                    .build();
        }


        @Override
        public AdminRequest unMapping(AdminDoc adminDoc) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class ResponseMapping extends BaseMapping<AdminDoc, AdminResponse> {

        @Override
        public AdminResponse convert(AdminDoc adminDoc) {
            return AdminResponse.builder()
                    .id(adminDoc.getId().toString())
                    .email(adminDoc.getEmail())
                    .password(adminDoc.getPassword())
                    .build();
        }

        @Override
        public AdminDoc unMapping(AdminResponse adminResponse) {
            throw new RuntimeException("dont use this");
        }
    }


    public static class SearchMapping extends BaseMapping<SearchResponse<AdminDoc>, SearchResponse<AdminResponse>> {
        private final ResponseMapping responseMapping = new ResponseMapping();

        @Override
        public SearchResponse<AdminResponse> convert(SearchResponse<AdminDoc> searchResponse) {
            return SearchResponse.of(
                    searchResponse.getList().stream().map(responseMapping::convert).collect(Collectors.toList()),
                    searchResponse.getCount()
            );
        }

        @Override
        public SearchResponse<AdminDoc> unMapping(SearchResponse<AdminResponse> adminResponses) {
            throw new RuntimeException("dont use this");
        }
    }


    private final RequestMapping request = new RequestMapping();
    private final ResponseMapping response = new ResponseMapping();
    private final SearchMapping search = new SearchMapping();


    public static AdminMapping getInstance() {
        return new AdminMapping();
    }

}
