package com.example.jpa.association.onetoone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OneToOneMainRepository extends JpaRepository<MainPO, Long> {

    @Query("select new com.example.jpa.association.onetoone.MainSubPO(main.id, main.content, sub.id, sub.name)" +
            " from MainPO main, SubPO sub where main.subPO.id = sub.id and main.id = :mainId")
    MainSubPO getMainSubByMainId(Long mainId);

    @Query("select new com.example.jpa.association.onetoone.MainSubPO(main.id, main.content, sub.id, sub.name)" +
            " from MainPO main left join SubPO sub on main.subPO.id = sub.id where main.id = :mainId")
    MainSubPO getMainSubByMainIdLeftJoin(Long mainId);

    @Query(value = "select main.id, main.content, sub.id as subId, sub.name from onetoone_main main, onetoone_sub sub" +
            " where main.sub_id = sub.id and main.id = :mainId"
            , nativeQuery = true
            , name = "queryMainSubByMainId")
    MainSubPO queryMainSubByMainId(Long mainId);

    @Query("select main from MainPO main where main.id = :mainId and main.subPO.name is null")
    MainPO getMainPOByMainIdAndSubNameIsNull(Long mainId);
}
