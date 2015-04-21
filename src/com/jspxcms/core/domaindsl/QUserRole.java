package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.UserRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUserRole is a Querydsl query type for UserRole
 */

public class QUserRole extends EntityPathBase<UserRole> {

    private static final long serialVersionUID = 833566591;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QUserRole userRole = new QUserRole("userRole");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QRole role;

    public final NumberPath<Integer> roleIndex = createNumber("roleIndex", Integer.class);

    public final QUser user;

    public QUserRole(String variable) {
        this(UserRole.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QUserRole(Path<? extends UserRole> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserRole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserRole(PathMetadata<?> metadata, PathInits inits) {
        this(UserRole.class, metadata, inits);
    }

    public QUserRole(Class<? extends UserRole> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

