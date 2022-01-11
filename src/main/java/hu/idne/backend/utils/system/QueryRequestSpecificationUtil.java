package hu.idne.backend.utils.system;

import hu.idne.backend.models.system.QueryColumn;
import hu.idne.backend.models.system.QueryRequest;
import hu.idne.backend.models.system.SearchValue;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Iterator;

public class QueryRequestSpecificationUtil<T> {

    private static final String OR_SEPARATOR = "+";
    private static final String ATTRIBUTE_SEPARATOR = "\\.";
    private static final char ESCAPE_CHAR = '\\';

    private final EntityManager em;
    private final Root<T> root;
    private final CriteriaQuery<?> query;
    private final CriteriaBuilder cb;
    private final QueryRequest request;


    public QueryRequestSpecificationUtil(EntityManager em, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, QueryRequest request) {
        this.em = em;
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.request = request;
    }

    public Predicate buildPredicate() {
        Predicate predicate = cb.conjunction();

        if (request.getColumns() != null) {
            predicate = buildColumnPredicates();
        }

        if (request.hasSearch()) {
            SearchValue globalFilterValue = request.getSearch();
            Predicate matchOneColumnPredicate = cb.disjunction();
            for (QueryColumn column : request.getSearchableColumns()) {
                matchOneColumnPredicate = cb.or(matchOneColumnPredicate, buildColumnPredicates(column, globalFilterValue));
            }
            predicate = cb.and(predicate, matchOneColumnPredicate);
        }

        boolean isCountQuery = query.getResultType() == Long.class;
        if (isCountQuery) {
            return predicate;
        }

        return predicate;
    }

    private Predicate buildColumnPredicates() {
        return request.getColumns().stream()
                .filter(QueryColumn::hasSearch)
                .map(this::buildColumnPredicates)
                .reduce(cb::and)
                .orElse(cb.conjunction());
    }

    private Predicate buildColumnPredicates(QueryColumn column) {
        return buildColumnPredicates(column, column.getSearch());
    }

    private Predicate buildColumnPredicates(QueryColumn column, SearchValue searchValue) {
        Expression<String> expression = buildExpression(column.getAssociatedColumnName());

        if (searchValue.getStrict()) {
            return cb.equal(expression, searchValue.getValue());
        } else if (searchValue.getValue().contains(OR_SEPARATOR)) {
            return buildOrSeparatedPredicate(searchValue.getValue(), expression);
        } else {
            return booleanOrStringTypeMatcher(searchValue.getValue(), expression);
        }
    }

    private Predicate buildOrSeparatedPredicate(String filterValue, Expression<String> expression) {
        String[] values = filterValue.split("\\" + OR_SEPARATOR);
        return expression.in(Arrays.asList(values));
    }

    private Predicate booleanOrStringTypeMatcher(String filterValue, Expression<String> expression) {
        if (isBoolean(filterValue)) {
            return cb.equal(expression.as(Boolean.class), Boolean.valueOf(filterValue));
        } else {
            return cb.like(cb.lower(expression), getLikeFilterValue(filterValue), ESCAPE_CHAR);
        }
    }

    private Expression<String> buildExpression(String columnData) {
        String[] attributesStringPath = columnData.split(ATTRIBUTE_SEPARATOR);
        Iterator<String> itr = Arrays.stream(attributesStringPath).iterator();

        Path<?> path = root;

        while (itr.hasNext()) {
            String curr = itr.next();
            path = path.get(curr);
        }

        return path.as(String.class);
    }

    private String getLikeFilterValue(String filterValue) {
        return "%" + filterValue.toLowerCase().replaceAll("%", "\\\\" + "%").replaceAll("_", "\\\\" + "_") + "%";
    }

    private boolean isBoolean(String filterValue) {
        return "TRUE".equalsIgnoreCase(filterValue) || "FALSE".equalsIgnoreCase(filterValue);
    }

}
