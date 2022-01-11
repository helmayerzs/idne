package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.AbstractQueryController;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.services.business.PostDocumentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource/post-documents")
public class PostDocumentQueryController extends AbstractQueryController<PostDocumentQueryService, PostDocument, Long> {

    @Autowired
    public PostDocumentQueryController(PostDocumentQueryService service) {
        super(service);
    }
}
