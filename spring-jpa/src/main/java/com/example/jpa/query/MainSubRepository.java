package com.example.jpa.query;

import com.example.jpa.association.onetoone.MainPO;
import com.example.jpa.association.onetoone.MainSubPO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

/**
 * @author Hodur
 * @date 2021/7/13
 */
@RepositoryDefinition(domainClass = MainPO.class, idClass = Long.class)
public interface MainSubRepository {

    Optional<MainPO> findById(Long id);

    MainPO save(MainPO po);

    /**
     * association query using jpql, implicit join
     *
     * @param mainId
     * @return
     */
    @Query("select new com.example.jpa.association.onetoone.MainSubPO(main.id, main.content, sub.id, sub.name)" +
            " from MainPO main, SubPO sub where main.subPO.id = sub.id and main.id = :mainId")
    MainSubPO getMainSubByMainId(Long mainId);

    @Query("select new com.example.jpa.association.onetoone.MainSubPO(main.id, main.content, sub.id, sub.name)" +
            " from MainPO main, SubPO sub where main.subPO.id = sub.id")
    Page<MainSubPO> ListMainSub(Pageable pageable);

    /**
     * association query using jpql, left join
     *
     * @param mainId
     * @return
     */
    @Query("select new com.example.jpa.association.onetoone.MainSubPO(main.id, main.content, sub.id, sub.name)" +
            " from MainPO main left join SubPO sub on main.subPO.id = sub.id where main.id = :mainId")
    MainSubPO getMainSubByMainIdLeftJoin(Long mainId);

    /**
     * jpql query using attribute of sub entity
     *
     * @param
     * @return
     */
    @Query("select main from MainPO main where main.subPO.name is not null")
    MainPO getMainPOBySubNameIsNotNull();

    /**
     * association query using native query, implicit join
     *
     * @param mainId
     * @return
     */
    @Query(value = "select main.id, main.content, sub.id as subId, sub.name from onetoone_main main, onetoone_sub sub" +
            " where main.sub_id = sub.id and main.id = :mainId"
            , nativeQuery = true
            , name = "queryMainSubByMainId")
    MainSubPO queryMainSubByMainId(Long mainId);

    @Query(value = "select main.id, main.content, sub.id as subId, sub.name from onetoone_main main, onetoone_sub sub" +
            " where main.sub_id = sub.id \n#pageable\n"
            , countQuery = "select count(*) from onetoone_main"
            , nativeQuery = true
            , name = "ListMainSubByNative")
    Page<MainSubPO> ListMainSubByNative(Pageable pageable);

}
