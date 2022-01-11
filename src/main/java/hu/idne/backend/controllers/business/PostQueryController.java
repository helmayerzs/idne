package hu.idne.backend.controllers.business;

import hu.idne.backend.controllers.system.AbstractQueryController;
import hu.idne.backend.models.business.Post;
import hu.idne.backend.services.business.PostQueryService;
import hu.idne.backend.utils.system.OAuth2TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/resource/posts")
public class PostQueryController extends AbstractQueryController<PostQueryService, Post, Long> {

    @Autowired
    public PostQueryController(PostQueryService service) {
        super(service);
    }
}
