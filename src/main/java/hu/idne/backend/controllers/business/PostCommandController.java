package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.AbstractCommandController;
import hu.idne.backend.enums.system.ErrorMessages;
import hu.idne.backend.exceptions.UpdateException;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.models.system.ActionError;
import hu.idne.backend.models.system.MessageWrapper;
import hu.idne.backend.models.system.StoreLocation;
import hu.idne.backend.models.system.enums.DocType;
import hu.idne.backend.services.business.PostCommandService;
import hu.idne.backend.services.business.PostDocumentCommandService;
import hu.idne.backend.services.business.PostDocumentQueryService;
import hu.idne.backend.services.business.PostDocumentService;
import hu.idne.backend.services.system.FileStorageService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Log
@RestController
@RequestMapping("/resource/posts")
public class PostCommandController extends AbstractCommandController<PostCommandService, Post, Long> {

    private final FileStorageService fileStorageService;
    private final PostDocumentService postDocumentService;
    private final PostDocumentQueryService postDocumentQueryService;
    private final PostDocumentCommandService postDocumentCommandService;

    @Autowired
    public PostCommandController(
            PostCommandService commandService,
            @Qualifier("getFileStorageService") FileStorageService fileStorageService,
            PostDocumentService postDocumentService,
            PostDocumentQueryService postDocumentQueryService, PostDocumentCommandService postDocumentCommandService) {
        super(commandService);
        this.fileStorageService = fileStorageService;
        this.postDocumentService = postDocumentService;
        this.postDocumentQueryService = postDocumentQueryService;
        this.postDocumentCommandService = postDocumentCommandService;
    }

    @Override
    @ApiOperation(value = "Update one item with the given ID")
    @PutMapping(path = "/id")
    public Post update(@RequestParam(name = "id") Long id, @RequestBody Post entity) {
        if (!id.equals(entity.getIdentifier())) {
            throw new UpdateException("cannot update, no id preset or you want to change the id, that is illegal as hell :D");
        }
        return service.persist(entity);
    }

    @Override
    @ApiOperation(value = "Delete an item with the given ID")
    @DeleteMapping(path = "/id")
    public void delete(@RequestParam(name = "id") Long id) {
        service.delete(id);
    }

    @ApiOperation("Upload any photos")
    @PostMapping(value = "/upload-photo")
    public MessageWrapper uploadPhoto(@RequestParam Long postId, @RequestParam MultipartFile photo) throws IOException {
        log.info("Upload started");

        if (photo == null) {
            throw ActionError.of(ErrorMessages.FILE_IS_NOT_PRESET, "Either tried to not upload a file, or the file is empty").obtainException();
        }

        StoreLocation pathInfo = fileStorageService.storeFile(DocType.POST_PHOTOS, photo.getOriginalFilename(), photo.getInputStream());
        postDocumentService.addPhoto(postId, pathInfo.getFolderName(), pathInfo.getObjectName());

        return new MessageWrapper("Upload end");
    }

    @ApiOperation("Delete photo")
    @DeleteMapping(value = "/delete-photo")
    public MessageWrapper deletePhoto(@RequestParam(name = "id") Long id) {
        log.info("Removing started...");

        Optional<PostDocument> postDocumentOptional = postDocumentQueryService.findOne(id);
        if(postDocumentOptional.isPresent()){
            fileStorageService.removeFile(postDocumentOptional.get().getFolder(), postDocumentOptional.get().getFileName());
            postDocumentCommandService.delete(id);
        }

        return new MessageWrapper("Removing ended");
    }

}