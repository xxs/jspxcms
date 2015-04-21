package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.GuestbookType;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QGuestbookType is a Querydsl query type for GuestbookType
 */

public class QGuestbookType extends EntityPathBase<GuestbookType> {

    private static final long serialVersionUID = -1734086943;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QGuestbookType guestbookType = new QGuestbookType("guestbookType");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public QGuestbookType(String variable) {
        this(GuestbookType.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QGuestbookType(Path<? extends GuestbookType> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGuestbookType(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QGuestbookType(PathMetadata<?> metadata, PathInits inits) {
        this(GuestbookType.class, metadata, inits);
    }

    public QGuestbookType(Class<? extends GuestbookType> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
    }

}

