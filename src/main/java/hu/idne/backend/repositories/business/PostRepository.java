package hu.idne.backend.repositories.business;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.models.system.CommonRevisionEntity;
import hu.idne.backend.repositories.system.BaseRepository;
import hu.idne.backend.repositories.system.RevisionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface PostRepository extends BaseRepository<Post, Long>, RevisionRepository<Post, Long, CommonRevisionEntity> {
}
