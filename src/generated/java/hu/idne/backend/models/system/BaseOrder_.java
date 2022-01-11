package hu.idne.backend.models.system;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseOrder.class)
public abstract class BaseOrder_ extends hu.idne.backend.models.system.AbstractEntity_ {

	public static volatile SingularAttribute<BaseOrder, Integer> orderQty;
	public static volatile SingularAttribute<BaseOrder, LocalDate> deliveryDate;

	public static final String ORDER_QTY = "orderQty";
	public static final String DELIVERY_DATE = "deliveryDate";

}

