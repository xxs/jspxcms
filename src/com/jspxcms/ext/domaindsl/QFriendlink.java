package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.Friendlink;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QFriendlink is a Querydsl query type for Friendlink
 */

public class QFriendlink extends EntityPathBase<Friendlink> {

    private static final long serialVersionUID = 209222162;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QFriendlink friendlink = new QFriendlink("friendlink");

    public final StringPath description = createString("description");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath logo = createString("logo");

    public final StringPath name = createString("name");

    public final BooleanPath recommend = createBoolean("recommend");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final QFriendlinkType type;

    public final StringPath url = createString("url");

    public final BooleanPath withLogo = createBoolean("withLogo");

    public QFriendlink(String variable) {
        this(Friendlink.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QFriendlink(Path<? extends Friendlink> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFriendlink(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QFriendlink(PathMetadata<?> metadata, PathInits inits) {
        this(Friendlink.class, metadata, inits);
    }

    public QFriendlink(Class<? extends Friendlink> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
        this.type = inits.isInitialized("type") ? new QFriendlinkType(forProperty("type"), inits.get("type")) : null;
    }

}

