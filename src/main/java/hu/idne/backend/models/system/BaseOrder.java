package hu.idne.backend.models.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseOrder<I extends Serializable> extends AbstractEntity<I> {

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    //@Column(name = "order_date")
    //private LocalDate orderDate;

    @Column(name = "order_qty")
    private Integer orderQty;
}

