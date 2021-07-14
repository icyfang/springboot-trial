package com.example.jpa.queryDSL;

import com.example.jpa.association.onetoone.MainPO;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QMainPO is a Querydsl query type for MainPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainPO extends EntityPathBase<MainPO> {

    private static final long serialVersionUID = 1840421200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMainPO mainPO = new QMainPO("mainPO");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSubPO subPO;

    public QMainPO(String variable) {
        this(MainPO.class, forVariable(variable), INITS);
    }

    public QMainPO(Path<? extends MainPO> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMainPO(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMainPO(PathMetadata metadata, PathInits inits) {
        this(MainPO.class, metadata, inits);
    }

    public QMainPO(Class<? extends MainPO> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.subPO = inits.isInitialized("subPO") ? new QSubPO(forProperty("subPO")) : null;
    }

}

