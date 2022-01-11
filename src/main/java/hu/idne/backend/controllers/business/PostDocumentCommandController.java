package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.AbstractCommandController;
import hu.idne.backend.exceptions.UpdateException;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.services.business.PostDocumentCommandService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/resource/post-documents")
public class PostDocumentCommandController extends AbstractCommandController<PostDocumentCommandService, PostDocument, Long> {

    @Autowired
    public PostDocumentCommandController(PostDocumentCommandService commandService) {
        super(commandService);
    }

    @Override
    @ApiOperation(value = "Update one item with the given ID")
    @PutMapping(path = "/id")
    public PostDocument update(@RequestParam(name = "id") Long id, @RequestBody PostDocument entity) {
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
}