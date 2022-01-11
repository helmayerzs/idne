package hu.idne.backend.services.business;

import hu.idne.backend.models.business.PostDocument;
import hu.idne.backend.services.system.CommandService;
import hu.idne.backend.services.system.MergePersist;

public interface PostDocumentCommandService extends CommandService<PostDocument, Long>, MergePersist<PostDocument> {}

