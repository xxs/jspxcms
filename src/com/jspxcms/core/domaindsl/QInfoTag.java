package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoTag;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoTag is a Querydsl query type for InfoTag
 */

public class QInfoTag extends EntityPathBase<InfoTag> {

    private static final long serialVersionUID = -1759888466;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoTag infoTag = new QInfoTag("infoTag");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final QTag tag;

    public final NumberPath<Integer> tagIndex = createNumber("tagIndex", Integer.class);

    public QInfoTag(String variable) {
        this(InfoTag.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoTag(Path<? extends InfoTag> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoTag(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoTag(PathMetadata<?> metadata, PathInits inits) {
        this(InfoTag.class, metadata, inits);
    }

    public QInfoTag(Class<? extends InfoTag> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.tag = inits.isInitialized("tag") ? new QTag(forProperty("tag"), inits.get("tag")) : null;
    }

}

