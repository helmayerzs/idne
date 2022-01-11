package hu.idne.backend.models.business;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Post.class)
public abstract class Post_ extends hu.idne.backend.models.system.AbstractEntity_ {

	public static volatile ListAttribute<Post, PostDocument> documents;
	public static volatile SingularAttribute<Post, String> videoLink;
	public static volatile SingularAttribute<Post, Long> id;
	public static volatile SingularAttribute<Post, String> title;
	public static volatile SingularAttribute<Post, String> content;

	public static final String DOCUMENTS = "documents";
	public static final String VIDEO_LINK = "videoLink";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";

}

