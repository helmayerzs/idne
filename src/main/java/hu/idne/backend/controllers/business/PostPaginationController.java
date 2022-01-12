package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.ExtendedPaginationController;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.business.queries.PostQuery;
import hu.idne.backend.models.system.ExtendedPageRequest;
import hu.idne.backend.models.system.PageResult;
import hu.idne.backend.services.business.PostPaginationService;
import hu.idne.backend.specifications.business.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource/posts")
public class PostPaginationController extends ExtendedPaginationController<PostPaginationService, Post, PostQuery> {


    @Autowired
    public PostPaginationController(PostPaginationService paginationService) {
        super(paginationService);
    }

    @PostMapping(path = "/page")
    public PageResult<Post> page(@RequestBody ExtendedPageRequest<PostQuery> request) {

        if (request.getQuery() == null) {
            PageResult<Post> res = service.page(request);
            return res;
        }

        PageResult<Post> res = service.page(request, toSpecification(request.getQuery()));
        return  res;
    }

    @Override
    public Specification<Post> toSpecification(@NonNull PostQuery messageQuery){
        return new PostSpecification(messageQuery);
    }
}
