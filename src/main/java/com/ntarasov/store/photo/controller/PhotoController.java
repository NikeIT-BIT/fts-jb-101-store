package com.ntarasov.store.photo.controller;

import com.ntarasov.store.base.api.response.OkResponse;
import com.ntarasov.store.photo.api.response.PhotoResponse;
import com.ntarasov.store.photo.exception.PhotoNotExistException;
import com.ntarasov.store.photo.mapping.PhotoMapping;
import com.ntarasov.store.photo.model.PhotoDoc;
import com.ntarasov.store.photo.routers.PhotoApiRoutes;
import com.ntarasov.store.photo.service.PhotoApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api(value = "Photo API")
public class PhotoController {
//<---------------------------------FINAL------------------------------------------------->
    private final PhotoApiService photoApiService;


//<---------------------------------------------------------------------------------->
    @GetMapping(PhotoApiRoutes.DOWNLOAD)
    @ApiOperation(value = "Find photo by id", notes = "Photo this when you need full info about")

    public  void byId(
    @ApiParam(value = "Photo id") @PathVariable ObjectId id, HttpServletResponse response
            ) throws ChangeSetPersister.NotFoundException, IOException, PhotoNotExistException {
        PhotoDoc photoDoc = photoApiService.findById(id).orElseThrow();
        response.addHeader("Content-Type", "image/png");
        response.addHeader("Content-Disposition", "inline; filename=\""+photoDoc.getName()+"\"");
        FileCopyUtils.copy(photoApiService.downloadById(id),response.getOutputStream());

    }

//<---------------------------------------------------------------------------------->
    @PostMapping(PhotoApiRoutes.ROOT)
    @ApiOperation(value = "create", notes = "Photo this when you need create and new create photo")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Photo alreade exist")
    })

    public @ResponseBody OkResponse<PhotoResponse> create(
            @RequestParam MultipartFile file
    ) throws IOException {
        return OkResponse.of(PhotoMapping.getInstance().getResponse().convert(photoApiService.create(file)));
    }
}
