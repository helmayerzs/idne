package hu.idne.backend.services.business.impl;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.repositories.business.PostRepository;
import hu.idne.backend.services.business.PostPaginationService;
import hu.idne.backend.services.system.impl.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostPaginationServiceImpl extends PaginationServiceImpl<Post, Long, PostRepository> implements PostPaginationService {

    @Autowired
    public PostPaginationServiceImpl(PostRepository repository) {
        super(repository);
    }
}