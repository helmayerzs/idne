package hu.idne.backend.models.business;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PostDocument.class)
public abstract class PostDocument_ extends hu.idne.backend.models.system.AbstractEntity_ {

	public static volatile SingularAttribute<PostDocument, String> fileName;
	public static volatile SingularAttribute<PostDocument, String> folder;
	public static volatile SingularAttribute<PostDocument, Post> post;
	public static volatile SingularAttribute<PostDocument, Long> id;

	public static final String FILE_NAME = "fileName";
	public static final String FOLDER = "folder";
	public static final String POST = "post";
	public static final String ID = "id";

}

