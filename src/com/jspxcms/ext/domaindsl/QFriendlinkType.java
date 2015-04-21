package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.FriendlinkType;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFriendlinkType is a Querydsl query type for FriendlinkType
 */

public class QFriendlinkType extends EntityPathBase<FriendlinkType> {

    private static final long serialVersionUID = -925817748;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFriendlinkType friendlinkType = new QFriendlinkType("friendlinkType");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public QFriendlinkType(String variable) {
        this(FriendlinkType.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFriendlinkType(Path<? extends FriendlinkType> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFriendlinkType(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFriendlinkType(PathMetadata<?> metadata, PathInits inits) {
        this(FriendlinkType.class, metadata, inits);
    }

    public QFriendlinkType(Class<? extends FriendlinkType> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
    }

}

