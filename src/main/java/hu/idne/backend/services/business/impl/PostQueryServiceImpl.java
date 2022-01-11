package hu.idne.backend.services.business.impl;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.repositories.business.PostRepository;
import hu.idne.backend.services.business.PostQueryService;
import hu.idne.backend.services.system.impl.QueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostQueryServiceImpl extends QueryServiceImpl<Post, Long, PostRepository> implements PostQueryService {
    
    @Autowired
    public PostQueryServiceImpl(PostRepository repository) {
        super(repository);
    }
}
