package com.ntarasov.store.gues.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.gues.api.request.GuesRequest;
import com.ntarasov.store.gues.api.response.GuesResponse;
import com.ntarasov.store.gues.exception.GuesExistException;
import com.ntarasov.store.gues.exception.GuesNotExistException;
import com.ntarasov.store.gues.mapping.GuesMapping;
import com.ntarasov.store.gues.routers.GuesApiRoutes;
import com.ntarasov.store.gues.service.GuesApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Gues API")
public class GuesApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final GuesApiService guesApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(GuesApiRoutes.BY_ID)
    @ApiOperation(value = "Find gues by id", notes = "Gues this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Gues not found")
    })

    public OkResponse<GuesResponse> byId(
    @ApiParam(value = "Gues id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(GuesMapping.getInstance().getResponse().convert(
            guesApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(GuesApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Gues this when you need create and new create gues")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Gues alreade exist")
    })

    public OkResponse<GuesResponse> create(@RequestBody GuesRequest request) throws GuesExistException {
        return OkResponse.of(GuesMapping.getInstance().getResponse().convert(guesApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(GuesApiRoutes.ROOT)
   @ApiOperation(value = "Search gues", notes = "Gues this when you need find gues by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<GuesResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(GuesMapping.getInstance().getSearch().convert(
                guesApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
//   @PutMapping(GuesApiRoutes.BY_ID)
//   @ApiOperation(value = "Update gues", notes = "Gues this when you need update gues info")
//   @ApiResponses(value = {
//           @ApiResponse(code = 200, message = "Success"),
//           @ApiResponse(code = 400, message = "Gues ID invalid")
//   })
//
//    public OkResponse<GuesResponse> gues(
//            @ApiParam(value = "Gues id") @PathVariable String id,
//            @RequestBody GuesRequest guesRequest
//   ) throws GuesNotExistException {
//        return OkResponse.of(GuesMapping.getInstance().getResponse().convert(
//                guesApiService.update(guesRequest)
//        ));
//
//   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(GuesApiRoutes.BY_ID)
   @ApiOperation(value = "Delete gues", notes = "User this when you need delete gues")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        guesApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
