package com.ntarasov.store.category.controller;

import com.ntarasov.store.base.api.request.SearchRequest;
import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.base.api.response.SearchResponse;
import com.ntarasov.store.category.api.request.CategoryRequest;
import com.ntarasov.store.category.api.response.CategoryResponse;
import com.ntarasov.store.category.exception.CategoryExistException;
import com.ntarasov.store.category.exception.CategoryNotExistException;
import com.ntarasov.store.category.mapping.CategoryMapping;
import com.ntarasov.store.category.routers.CategoryApiRoutes;
import com.ntarasov.store.category.service.CategoryApiService;
import com.ntarasov.store.photo.exception.PhotoNotExistException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Category API")
public class CategoryApiController {
//<---------------------------------FINAL------------------------------------------------->
    private final CategoryApiService categoryApiService;


//<---------------------------------ПОИСК ПО ID------------------------------------------------->
    @GetMapping(CategoryApiRoutes.BY_ID)
    @ApiOperation(value = "Find category by id", notes = "Category this when you need full info about")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Category not found")
    })

    public OkResponse<CategoryResponse> byId(
    @ApiParam(value = "Category id") @PathVariable ObjectId id
            ) throws ChangeSetPersister.NotFoundException {
            return OkResponse.of(CategoryMapping.getInstance().getResponse().convert(
            categoryApiService.findById(id).orElseThrow(
            ChangeSetPersister.NotFoundException::new)
            ));
            }

//<---------------------------------СОЗДАНАНИЕ------------------------------------------------->
    @PostMapping(CategoryApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Category this when you need create and new create category")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Category alreade exist")
    })

    public OkResponse<CategoryResponse> create(@RequestBody CategoryRequest request) throws CategoryExistException {
        return OkResponse.of(CategoryMapping.getInstance().getResponse().convert(categoryApiService.create(request)));
    }

//<---------------------------------СПИСОК БАЗЫ ДАННЫХ------------------------------------------------->
   @GetMapping(CategoryApiRoutes.ROOT)
   @ApiOperation(value = "Search category", notes = "Category this when you need find category by last name first or email")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<SearchResponse<CategoryResponse>> search(
           @ModelAttribute SearchRequest request
           ){
        return OkResponse.of(CategoryMapping.getInstance().getSearch().convert(
                categoryApiService.search(request)
        ));

   }

//<---------------------------------ИЗМЕНЕНИЕ ПО ID------------------------------------------------->
   @PutMapping(CategoryApiRoutes.BY_ID)
   @ApiOperation(value = "Update category", notes = "Category this when you need update category info")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success"),
           @ApiResponse(code = 400, message = "Category ID invalid")
   })

    public OkResponse<CategoryResponse> category(
            @ApiParam(value = "Category id") @PathVariable String id,
            @RequestBody CategoryRequest categoryRequest
   ) throws CategoryNotExistException {
        return OkResponse.of(CategoryMapping.getInstance().getResponse().convert(
                categoryApiService.update(categoryRequest)
        ));

   }

//<---------------------------------УДАЛЕНИЕ ПО ID------------------------------------------------->
   @DeleteMapping(CategoryApiRoutes.BY_ID)
   @ApiOperation(value = "Delete category", notes = "User this when you need delete category")
   @ApiResponses(value = {
           @ApiResponse(code = 200, message = "Success")
   })

    public OkResponse<String> delete(
            @ApiParam(value = "User id") @PathVariable ObjectId id
   ) throws PhotoNotExistException {
        categoryApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
   }
}
