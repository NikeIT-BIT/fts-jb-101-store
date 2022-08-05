package com.ntarasov.store.city.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.api.request.CityRequest;
import com.ntarasov.store.city.api.response.CityResponse;
import com.ntarasov.store.city.exception.CityExistException;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.city.mapping.CityMapping;
import com.ntarasov.store.city.routers.CityApiRoutes;
import com.ntarasov.store.city.service.CityApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "City API")
public class CityApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final CityApiService cityApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(CityApiRoutes.BY_ID)
    @ApiOperation(value = "Find city by id", notes = "City this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "City not found")
    })

    public OkResponse<CityResponse> byId(
    @ApiParam(value = "City id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(CityMapping.getInstance().getResponse().convert(
            cityApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(CityApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "City this when you need create and new create city")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "City alreade exist")
    })

    public OkResponse<CityResponse> create(
            @RequestBody CityRequest request
    ) throws CityExistException {
        return OkResponse.of(CityMapping.getInstance().getResponse().convert(cityApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(CityApiRoutes.ROOT)
   @ApiOperation(value = "Search city", notes = "City this when you need find city by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<CityResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(CityMapping.getInstance().getSearch().convert(
                cityApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(CityApiRoutes.BY_ID)
   @ApiOperation(value = "Update city", notes = "City this when you need update city info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "City ID invalid")
   })

    public OkResponse<CityResponse> city(
            @ApiParam(value = "City id") @PathVariable String id,
            @RequestBody CityRequest cityRequest
   ) throws CityNotExistException {
        return OkResponse.of(CityMapping.getInstance().getResponse().convert(
                cityApiService.update(cityRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(CityApiRoutes.BY_ID)
   @ApiOperation(value = "Delete city", notes = "User this when you need delete city")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> delete(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        cityApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
