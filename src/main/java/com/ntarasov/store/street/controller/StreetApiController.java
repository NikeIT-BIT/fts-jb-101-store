package com.ntarasov.store.street.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.city.exception.CityNotExistException;
import com.ntarasov.store.street.api.request.StreetRequest;
import com.ntarasov.store.street.api.response.StreetResponse;
import com.ntarasov.store.street.exception.StreetExistException;
import com.ntarasov.store.street.exception.StreetNotExistException;
import com.ntarasov.store.street.mapping.StreetMapping;
import com.ntarasov.store.street.routers.StreetApiRoutes;
import com.ntarasov.store.street.service.StreetApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Street API")
public class StreetApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final StreetApiService streetApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(StreetApiRoutes.BY_ID)
    @ApiOperation(value = "Find street by id", notes = "Street this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Street not found")
    })

    public OkResponse<StreetResponse> byId(
    @ApiParam(value = "Street id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(StreetMapping.getInstance().getResponse().convert(
            streetApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(StreetApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Street this when you need create and new create street")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Street alreade exist")
    })

    public OkResponse<StreetResponse> create(@RequestBody StreetRequest request) throws StreetExistException, CityNotExistException {
        return OkResponse.of(StreetMapping.getInstance().getResponse().convert(streetApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(StreetApiRoutes.ROOT)
   @ApiOperation(value = "Search street", notes = "Street this when you need find street by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<StreetResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(StreetMapping.getInstance().getSearch().convert(
                streetApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(StreetApiRoutes.BY_ID)
   @ApiOperation(value = "Update street", notes = "Street this when you need update street info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Street ID invalid")
   })

    public OkResponse<StreetResponse> street(
            @ApiParam(value = "Street id") @PathVariable String id,
            @RequestBody StreetRequest streetRequest
   ) throws StreetNotExistException {
        return OkResponse.of(StreetMapping.getInstance().getResponse().convert(
                streetApiService.update(streetRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(StreetApiRoutes.BY_ID)
   @ApiOperation(value = "Delete street", notes = "User this when you need delete street")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        streetApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
