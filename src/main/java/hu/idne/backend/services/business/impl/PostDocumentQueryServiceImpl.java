package hu.idne.backend.services.business.impl;

import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.repositories.business.PostDocumentRepository;
import hu.idne.backend.services.business.PostDocumentQueryService;
import hu.idne.backend.services.system.impl.QueryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostDocumentQueryServiceImpl extends QueryServiceImpl<PostDocument, Long, PostDocumentRepository> implements PostDocumentQueryService {

    @Autowired
    public PostDocumentQueryServiceImpl(PostDocumentRepository repository) {
        super(repository);
    }

}
