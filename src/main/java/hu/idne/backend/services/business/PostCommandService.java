package hu.idne.backend.services.business;

import hu.idne.backend.models.business.Post;
import hu.idne.backend.services.system.CommandService;
import hu.idne.backend.services.system.MergePersist;

public interface PostCommandService extends CommandService<Post, Long>, MergePersist<Post> {
}

