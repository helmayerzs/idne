package hu.idne.backend.utils.system;

import hu.idne.backend.models.system.PageRequest;
import hu.idne.backend.models.system.QueryColumn;
import hu.idne.backend.models.system.QueryOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingAndFilterUtil {

    private PagingAndFilterUtil() {
    }

    public static Pageable toPageable(PageRequest pageRequest) {
        if (pageRequest.getLength() == -1) {
            pageRequest.setStart(0);
            pageRequest.setLength(Integer.MAX_VALUE);
        }

        return org.springframework.data.domain.PageRequest.of(pageRequest.getStart(), pageRequest.getLength(), Sort.by(Sort.Direction.fromString(pageRequest.getDirection()),
                pageRequest.getSortedField()));
    }

    @Getter
    @AllArgsConstructor
    private static class ColumnOrder {
        private final String column;
        private final String direction;
        private final Integer precedence;


        ColumnOrder(QueryColumn column) {
            QueryOrder order = column.getOrder();

            this.column = column.getAssociatedColumnName();
            this.direction = order.getDir().getValue();
            this.precedence = order.getPrecedence();
        }
    }


}
