package hu.idne.backend.repositories.system;

import com.google.common.collect.ListMultimap;
import hu.idne.backend.models.system.RevisionEntityContainer;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * jellenleg nincs használatban, az implementálása várat magára, ne használd!!
 *
 * @param <T> entitás típusa
 * @param <I> ID típusa
 */
@NoRepositoryBean
public interface RevisionRepository<T, I extends Serializable, R> extends OpenRepository<T, I> {

    /**
     * Ezzel a lekérdezéssel egy listában meg kapjuk az összes állapotátt az összes bejegyzésnek ami valaha volt az entitás által reprezentált osztályban
     *
     * @return Az összes álapot változás listáját
     */
    List<RevisionEntityContainer<T, R>> findAllRevision();

    /**
     * Két adott tádum közt történz összes entitás állapot változásnak a listáját meg kapjuk egy listában
     *
     * @param from A kezdő dátum
     * @param to   A vég dátum
     * @return A két dátum közötti entitás állapotok listája
     */
    List<RevisionEntityContainer<T, R>> findAllRevision(LocalDateTime from, LocalDateTime to);

    /**
     * Multimap-ben tudjuk lekérdezni az összes állapot változást az adot táblában. A Multimap kulcsa a JPA entitás ID-ja, az értkékek meg az adott ID-vel rendelkező bejegyzés összes állapota
     *
     * @return Multimap-ben az entitások állapota, ahol a kulcs az ID, és az értékek az entitás állapotai
     */
    ListMultimap<I, RevisionEntityContainer<T, R>> findAllRevisionById();

    /**
     * Két dátum közti entitás állapot változások ID alapján multimap-be rendezve
     *
     * @param from A kezdő dátum
     * @param to   A vég dátum
     * @return A két dátum közötti entitás állapotok Multimap-ben ID szerint rendezve
     */
    ListMultimap<I, RevisionEntityContainer<T, R>> findAllRevisionId(LocalDateTime from, LocalDateTime to);

    /**
     * Egy entitásnak az össze állapota az adott ID-hez
     *
     * @param id egy valaha létező, vagy még létező ID;
     * @return Az ID hoz tartozó bejegyzés összes állapota
     */
    List<RevisionEntityContainer<T, R>> getAllRevisionOfId(I id);

    /**
     * Egy adott ID-val rendelkező entitás összes
     *
     * @param id asdf
     */
    void deleteAllRevisionOfId(I id);
}
