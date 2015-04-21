package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.UserOrg;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUserOrg is a Querydsl query type for UserOrg
 */

public class QUserOrg extends EntityPathBase<UserOrg> {

    private static final long serialVersionUID = 442528443;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QUserOrg userOrg = new QUserOrg("userOrg");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QOrg org;

    public final NumberPath<Integer> orgIndex = createNumber("orgIndex", Integer.class);

    public final QUser user;

    public QUserOrg(String variable) {
        this(UserOrg.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QUserOrg(Path<? extends UserOrg> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserOrg(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserOrg(PathMetadata<?> metadata, PathInits inits) {
        this(UserOrg.class, metadata, inits);
    }

    public QUserOrg(Class<? extends UserOrg> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.org = inits.isInitialized("org") ? new QOrg(forProperty("org"), inits.get("org")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

