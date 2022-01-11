package hu.idne.backend.models.system;

import hu.idne.backend.enums.system.Language;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageId.class)
public abstract class MessageId_ {

	public static volatile SingularAttribute<MessageId, String> messageCode;
	public static volatile SingularAttribute<MessageId, Language> language;

	public static final String MESSAGE_CODE = "messageCode";
	public static final String LANGUAGE = "language";

}

