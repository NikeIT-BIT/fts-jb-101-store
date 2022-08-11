package com.ntarasov.store.cart.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.cart.api.request.CartRequest;
import com.ntarasov.store.cart.api.request.CartUpdateRequest;
import com.ntarasov.store.cart.api.response.CartResponse;
import com.ntarasov.store.cart.exception.CartExistException;
import com.ntarasov.store.cart.exception.CartNotExistException;
import com.ntarasov.store.cart.mapping.CartMapping;
import com.ntarasov.store.cart.routers.CartApiRoutes;
import com.ntarasov.store.cart.service.CartApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Cart API")
public class CartApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final CartApiService cartApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(CartApiRoutes.BY_ID)
    @ApiOperation(value = "Find cart by id", notes = "Cart this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Cart not found")
    })

    public OkResponse<CartResponse> byId(
    @ApiParam(value = "Cart id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(CartMapping.getInstance().getResponse().convert(
            cartApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(CartApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Cart this when you need create and new create cart")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Cart alreade exist")
    })

    public OkResponse<CartResponse> create(@RequestBody CartRequest request) throws CartExistException {
        return OkResponse.of(CartMapping.getInstance().getResponse().convert(cartApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(CartApiRoutes.ROOT)
   @ApiOperation(value = "Search cart", notes = "Cart this when you need find cart by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<CartResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(CartMapping.getInstance().getSearch().convert(
                cartApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(CartApiRoutes.BY_ID)
   @ApiOperation(value = "Update cart", notes = "Cart this when you need update cart info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Cart ID invalid")
   })

    public OkResponse<CartResponse> cart(
            @ApiParam(value = "Cart id") @PathVariable String id,
            @RequestBody CartUpdateRequest cartRequest
   ) throws CartNotExistException {
        return OkResponse.of(CartMapping.getInstance().getResponse().convert(
                cartApiService.update(cartRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(CartApiRoutes.BY_ID)
   @ApiOperation(value = "Delete cart", notes = "User this when you need delete cart")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        cartApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
