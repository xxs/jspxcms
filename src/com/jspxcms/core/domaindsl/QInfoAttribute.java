package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoAttribute;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoAttribute is a Querydsl query type for InfoAttribute
 */

public class QInfoAttribute extends EntityPathBase<InfoAttribute> {

    private static final long serialVersionUID = -1936734288;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoAttribute infoAttribute = new QInfoAttribute("infoAttribute");

    public final QAttribute attribute;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final QInfo info;

    public QInfoAttribute(String variable) {
        this(InfoAttribute.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoAttribute(Path<? extends InfoAttribute> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoAttribute(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoAttribute(PathMetadata<?> metadata, PathInits inits) {
        this(InfoAttribute.class, metadata, inits);
    }

    public QInfoAttribute(Class<? extends InfoAttribute> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attribute = inits.isInitialized("attribute") ? new QAttribute(forProperty("attribute"), inits.get("attribute")) : null;
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
    }

}

