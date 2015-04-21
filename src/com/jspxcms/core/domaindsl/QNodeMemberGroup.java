package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeMemberGroup;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNodeMemberGroup is a Querydsl query type for NodeMemberGroup
 */

public class QNodeMemberGroup extends EntityPathBase<NodeMemberGroup> {

    private static final long serialVersionUID = -2070835643;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNodeMemberGroup nodeMemberGroup = new QNodeMemberGroup("nodeMemberGroup");

    public final BooleanPath commentPerm = createBoolean("commentPerm");

    public final BooleanPath contriPerm = createBoolean("contriPerm");

    public final QMemberGroup group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QNode node;

    public final BooleanPath viewPerm = createBoolean("viewPerm");

    public QNodeMemberGroup(String variable) {
        this(NodeMemberGroup.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNodeMemberGroup(Path<? extends NodeMemberGroup> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeMemberGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeMemberGroup(PathMetadata<?> metadata, PathInits inits) {
        this(NodeMemberGroup.class, metadata, inits);
    }

    public QNodeMemberGroup(Class<? extends NodeMemberGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QMemberGroup(forProperty("group")) : null;
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
    }

}

