package com.example.jpa.queryDSL;

import com.example.jpa.association.onetoone.SubPO;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QSubPO is a Querydsl query type for SubPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubPO extends EntityPathBase<SubPO> {

    private static final long serialVersionUID = -211596953L;

    public static final QSubPO subPO = new QSubPO("subPO");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QSubPO(String variable) {
        super(SubPO.class, forVariable(variable));
    }

    public QSubPO(Path<? extends SubPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubPO(PathMetadata metadata) {
        super(SubPO.class, metadata);
    }

}

