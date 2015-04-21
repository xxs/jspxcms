package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoSpecial;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoSpecial is a Querydsl query type for InfoSpecial
 */

public class QInfoSpecial extends EntityPathBase<InfoSpecial> {

    private static final long serialVersionUID = -1478651187;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoSpecial infoSpecial = new QInfoSpecial("infoSpecial");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final QSpecial special;

    public final NumberPath<Integer> specialIndex = createNumber("specialIndex", Integer.class);

    public QInfoSpecial(String variable) {
        this(InfoSpecial.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoSpecial(Path<? extends InfoSpecial> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoSpecial(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoSpecial(PathMetadata<?> metadata, PathInits inits) {
        this(InfoSpecial.class, metadata, inits);
    }

    public QInfoSpecial(Class<? extends InfoSpecial> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.special = inits.isInitialized("special") ? new QSpecial(forProperty("special"), inits.get("special")) : null;
    }

}

