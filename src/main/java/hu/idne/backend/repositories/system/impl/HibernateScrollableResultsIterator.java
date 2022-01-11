package hu.idne.backend.repositories.system.impl;

import org.hibernate.ScrollableResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.CloseableIterator;

import java.util.NoSuchElementException;

public class HibernateScrollableResultsIterator implements CloseableIterator<Object> {

    private static final Logger logger = LoggerFactory.getLogger(HibernateScrollableResultsIterator.class);
    private final ScrollableResults scrollableResults;

    public HibernateScrollableResultsIterator(ScrollableResults scrollableResults) {
        logger.debug("constructed");
        this.scrollableResults = scrollableResults;
    }

    @Override
    public Object next() {
        logger.debug("next is called");

        if (scrollableResults == null) {
            throw new NoSuchElementException("No ScrollableResults");
        }

        Object[] row = scrollableResults.get();

        return row.length == 1 ? row[0] : row;
    }

    @Override
    public boolean hasNext() {
        logger.debug("hasNext is called");
        return scrollableResults != null && scrollableResults.next();
    }

    @Override
    public void close() {
        logger.info("Iterator close called");
        if (scrollableResults != null) {
            scrollableResults.close();
        }
    }
}
