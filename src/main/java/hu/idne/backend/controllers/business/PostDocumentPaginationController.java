package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.ExtendedPaginationController;
import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.models.business.queries.PostDocumentQuery;
import hu.idne.backend.models.system.ExtendedPageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.services.business.PostDocumentPaginationService;
import hu.idne.backend.specifications.business.PostDocumentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource/post-documents")
public class PostDocumentPaginationController extends ExtendedPaginationController<PostDocumentPaginationService, PostDocument, PostDocumentQuery> {

    @Autowired
    public PostDocumentPaginationController(PostDocumentPaginationService paginationService) {
        super(paginationService);
    }

    @PostMapping(path = "/page")
    public PageResult<PostDocument> page(@RequestBody ExtendedPageRequest<PostDocumentQuery> request) {

        if (request.getQuery() == null) {
            return service.page(request);
        }
        return service.page(request, toSpecification(request.getQuery()));
    }

    @Override
    public Specification<PostDocument> toSpecification(@NonNull PostDocumentQuery postDocumentQuery){
        return new PostDocumentSpecification(postDocumentQuery);
    }
}
