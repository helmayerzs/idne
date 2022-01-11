package hu.idne.backend.services.business.impl;

import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.repositories.business.PostDocumentRepository;
import hu.idne.backend.services.business.PostDocumentPaginationService;
import hu.idne.backend.services.system.impl.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostDocumentPaginationServiceImpl extends PaginationServiceImpl<PostDocument, Long, PostDocumentRepository> implements PostDocumentPaginationService {

    @Autowired
    public PostDocumentPaginationServiceImpl(PostDocumentRepository repository) {
        super(repository);
    }
}