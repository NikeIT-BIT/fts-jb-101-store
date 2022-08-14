package com.ntarasov.store.order.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.order.api.request.OrderRequest;
import com.ntarasov.store.order.api.response.OrderResponse;
import com.ntarasov.store.order.exception.OrderExistException;
import com.ntarasov.store.order.exception.OrderNotExistException;
import com.ntarasov.store.order.mapping.OrderMapping;
import com.ntarasov.store.order.routers.OrderApiRoutes;
import com.ntarasov.store.order.service.OrderApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Order API")
public class OrderApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final OrderApiService orderApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(OrderApiRoutes.BY_ID)
    @ApiOperation(value = "Find order by id", notes = "Order this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Order not found")
    })

    public OkResponse<OrderResponse> byId(
    @ApiParam(value = "Order id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(OrderMapping.getInstance().getResponse().convert(
            orderApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(OrderApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Order this when you need create and new create order")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Order alreade exist")
    })

    public OkResponse<OrderResponse> create(@RequestBody OrderRequest request) throws OrderExistException {
        return OkResponse.of(OrderMapping.getInstance().getResponse().convert(orderApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(OrderApiRoutes.ROOT)
   @ApiOperation(value = "Search order", notes = "Order this when you need find order by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<OrderResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(OrderMapping.getInstance().getSearch().convert(
                orderApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(OrderApiRoutes.BY_ID)
   @ApiOperation(value = "Update order", notes = "Order this when you need update order info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Order ID invalid")
   })

    public OkResponse<OrderResponse> order(
            @ApiParam(value = "Order id") @PathVariable String id,
            @RequestBody OrderRequest orderRequest
   ) throws OrderNotExistException {
        return OkResponse.of(OrderMapping.getInstance().getResponse().convert(
                orderApiService.update(orderRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
//   @DeleteMapping(OrderApiRoutes.BY_ID)
//   @ApiOperation(value = "Delete order", notes = "User this when you need delete order")
//   @ApiResponses(value = {
//           @ApiResponse(code = 200, message = "Success")
//   })

//    public OkResponse<String> deleteUser(
//            @ApiParam(value = "User id") @PathVariable ObjectId id
//   ){
//        orderApiService.delete(id);
//        return OkResponse.of(HttpStatus.OK.toString());
//   }
}
