package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoBuffer;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoBuffer is a Querydsl query type for InfoBuffer
 */

public class QInfoBuffer extends EntityPathBase<InfoBuffer> {

    private static final long serialVersionUID = -668291156;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoBuffer infoBuffer = new QInfoBuffer("infoBuffer");

    public final NumberPath<Integer> burys = createNumber("burys", Integer.class);

    public final NumberPath<Integer> comments = createNumber("comments", Integer.class);

    public final NumberPath<Integer> diggs = createNumber("diggs", Integer.class);

    public final NumberPath<Integer> downloads = createNumber("downloads", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final NumberPath<Integer> involveds = createNumber("involveds", Integer.class);

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QInfoBuffer(String variable) {
        this(InfoBuffer.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoBuffer(Path<? extends InfoBuffer> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoBuffer(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoBuffer(PathMetadata<?> metadata, PathInits inits) {
        this(InfoBuffer.class, metadata, inits);
    }

    public QInfoBuffer(Class<? extends InfoBuffer> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
    }

}

