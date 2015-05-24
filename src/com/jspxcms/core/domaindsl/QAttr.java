package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Attr;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAttr is a Querydsl query type for Attr
 */

public class QAttr extends EntityPathBase<Attr> {

    private static final long serialVersionUID = -243529282;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QAttr attr = new QAttr("attr");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public QAttr(String variable) {
        this(Attr.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QAttr(Path<? extends Attr> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAttr(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAttr(PathMetadata<?> metadata, PathInits inits) {
        this(Attr.class, metadata, inits);
    }

    public QAttr(Class<? extends Attr> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

