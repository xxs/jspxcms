package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.UserMemberGroup;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUserMemberGroup is a Querydsl query type for UserMemberGroup
 */

public class QUserMemberGroup extends EntityPathBase<UserMemberGroup> {

    private static final long serialVersionUID = -745469732;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QUserMemberGroup userMemberGroup = new QUserMemberGroup("userMemberGroup");

    public final QMemberGroup group;

    public final NumberPath<Integer> groupIndex = createNumber("groupIndex", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser user;

    public QUserMemberGroup(String variable) {
        this(UserMemberGroup.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QUserMemberGroup(Path<? extends UserMemberGroup> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserMemberGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUserMemberGroup(PathMetadata<?> metadata, PathInits inits) {
        this(UserMemberGroup.class, metadata, inits);
    }

    public QUserMemberGroup(Class<? extends UserMemberGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QMemberGroup(forProperty("group")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

