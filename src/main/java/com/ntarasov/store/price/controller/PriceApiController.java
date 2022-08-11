package com.ntarasov.store.price.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.price.api.request.PriceRequest;
import com.ntarasov.store.price.api.request.PriceSearchRequest;
import com.ntarasov.store.price.api.request.PriceUpdateRequest;
import com.ntarasov.store.price.api.response.PriceResponse;
import com.ntarasov.store.price.exception.PriceExistException;
import com.ntarasov.store.price.exception.PriceNotExistException;
import com.ntarasov.store.price.mapping.PriceMapping;
import com.ntarasov.store.price.routers.PriceApiRoutes;
import com.ntarasov.store.price.service.PriceApiService;
import com.ntarasov.store.product.exception.ProductNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Price API")
public class PriceApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final PriceApiService priceApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(PriceApiRoutes.BY_ID)
    @ApiOperation(value = "Find price by id", notes = "Price this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Price not found")
    })

    public OkResponse<PriceResponse> byId(
    @ApiParam(value = "Price id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(PriceMapping.getInstance().getResponse().convert(
            priceApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(PriceApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Price this when you need create and new create price")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Price alreade exist")
    })

    public OkResponse<PriceResponse> create(@RequestBody PriceRequest request) throws PriceExistException, CityNotExistException, ProductNotExistException {
        return OkResponse.of(PriceMapping.getInstance().getResponse().convert(priceApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(PriceApiRoutes.ROOT)
   @ApiOperation(value = "Search price", notes = "Price this when you need find price by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<PriceResponse>> search(
           @ModelAttribute PriceSearchRequest request
           ){
        return OkResponse.of(PriceMapping.getInstance().getSearch().convert(
                priceApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(PriceApiRoutes.BY_ID)
   @ApiOperation(value = "Update price", notes = "Price this when you need update price info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Price ID invalid")
   })

    public OkResponse<PriceResponse> price(
            @ApiParam(value = "Price id") @PathVariable String id,
            @RequestBody PriceUpdateRequest priceRequest
   ) throws PriceNotExistException {
        return OkResponse.of(PriceMapping.getInstance().getResponse().convert(
                priceApiService.update(priceRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(PriceApiRoutes.BY_ID)
   @ApiOperation(value = "Delete price", notes = "User this when you need delete price")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        priceApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
