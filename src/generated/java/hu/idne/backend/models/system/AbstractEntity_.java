package hu.idne.backend.models.system;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractEntity.class)
public abstract class AbstractEntity_ {

	public static volatile SingularAttribute<AbstractEntity, LocalDateTime> createdDate;
	public static volatile SingularAttribute<AbstractEntity, String> updatedBy;
	public static volatile SingularAttribute<AbstractEntity, String> createdBy;
	public static volatile SingularAttribute<AbstractEntity, LocalDateTime> updatedDate;
	public static volatile SingularAttribute<AbstractEntity, Long> version;

	public static final String CREATED_DATE = "createdDate";
	public static final String UPDATED_BY = "updatedBy";
	public static final String CREATED_BY = "createdBy";
	public static final String UPDATED_DATE = "updatedDate";
	public static final String VERSION = "version";

}

