package com.ntarasov.store.admin.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.admin.api.request.AdminRequest;
import com.ntarasov.store.admin.api.response.AdminResponse;
import com.ntarasov.store.admin.exception.AdminExistException;
import com.ntarasov.store.admin.exception.AdminNotExistException;
import com.ntarasov.store.admin.mapping.AdminMapping;
import com.ntarasov.store.admin.routers.AdminApiRoutes;
import com.ntarasov.store.admin.service.AdminApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Admin API")
public class AdminApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final AdminApiService adminApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(AdminApiRoutes.BY_ID)
    @ApiOperation(value = "Find admin by id", notes = "Admin this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Admin not found")
    })

    public OkResponse<AdminResponse> byId(
    @ApiParam(value = "Admin id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(AdminMapping.getInstance().getResponse().convert(
            adminApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(AdminApiRoutes.ROOT)
   @ApiOperation(value = "Search admin", notes = "Admin this when you need find admin by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<AdminResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(AdminMapping.getInstance().getSearch().convert(
                adminApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(AdminApiRoutes.BY_ID)
   @ApiOperation(value = "Update admin", notes = "Admin this when you need update admin info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Admin ID invalid")
   })

    public OkResponse<AdminResponse> admin(
            @ApiParam(value = "Admin id") @PathVariable String id,
            @RequestBody AdminRequest adminRequest
   ) throws AdminNotExistException {
        return OkResponse.of(AdminMapping.getInstance().getResponse().convert(
                adminApiService.update(adminRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(AdminApiRoutes.BY_ID)
   @ApiOperation(value = "Delete admin", notes = "User this when you need delete admin")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> deleteUser(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ){
        adminApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
