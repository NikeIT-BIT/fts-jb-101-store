package com.ntarasov.store.product.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.product.api.request.ProductRequest;
import com.ntarasov.store.product.api.response.ProductFullResponse;
import com.ntarasov.store.product.api.response.ProductResponse;
import com.ntarasov.store.product.exception.ProductNotExistException;
import com.ntarasov.store.product.routers.ProductApiRoutes;
import com.ntarasov.store.photo.exception.PhotoExistException;
import com.ntarasov.store.product.exception.ProductExistException;
import com.ntarasov.store.product.mapping.ProductMapping;
import com.ntarasov.store.product.service.ProductApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Product API")
public class ProductApiController {
    //<---------------------------------FINAL------------------------------------------------->
    private final ProductApiService productApiService;


    //<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(ProductApiRoutes.BY_ID)
    @ApiOperation(value = "Find product by id", notes = "Product this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Product not found")
    })

    public OkResponse<ProductFullResponse> byId(
            @ApiParam(value = "Product id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        return OkResponse.of(ProductMapping.getInstance().getFullResponse().convert(
                productApiService.findById(id).orElseThrow(
                        ChangeSetPersister.NotFoundException::new)
        ));
    }

    //<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(ProductApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Product this when you need create and new create product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Product alreade exist")
    })

    public OkResponse<ProductResponse> create(@RequestBody ProductRequest request) throws ProductExistException, PhotoExistException {
        return OkResponse.of(ProductMapping.getInstance().getResponse().convert(productApiService.create(request)));
    }

    //<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
    @GetMapping(ProductApiRoutes.ROOT)
    @ApiOperation(value = "Search product", notes = "Product this when you need find product by last name first or email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })

    public OkResponse<SearchResponse<ProductResponse>> search(
            @ModelAttribute SearchRequest request
    ) {
        return OkResponse.of(ProductMapping.getInstance().getSearch().convert(
                productApiService.search(request)
        ));

    }

    //<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
    @PutMapping(ProductApiRoutes.BY_ID)
    @ApiOperation(value = "Update product", notes = "Product this when you need update product info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Product ID invalid")
    })

    public OkResponse<ProductResponse> product(
            @ApiParam(value = "Product id") @PathVariable String id,
            @RequestBody ProductRequest productRequest
    ) throws ProductNotExistException {
        return OkResponse.of(ProductMapping.getInstance().getResponse().convert(
                productApiService.update(productRequest)
        ));

    }

    //<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
    @DeleteMapping(ProductApiRoutes.BY_ID)
    @ApiOperation(value = "Delete product", notes = "User this when you need delete product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
    ) {
        productApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }
}
