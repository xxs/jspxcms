package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNodeRole is a Querydsl query type for NodeRole
 */

public class QNodeRole extends EntityPathBase<NodeRole> {

    private static final long serialVersionUID = -2071853514;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNodeRole nodeRole = new QNodeRole("nodeRole");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath infoPerm = createBoolean("infoPerm");

    public final QNode node;

    public final BooleanPath nodePerm = createBoolean("nodePerm");

    public final QRole role;

    public QNodeRole(String variable) {
        this(NodeRole.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNodeRole(Path<? extends NodeRole> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeRole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeRole(PathMetadata<?> metadata, PathInits inits) {
        this(NodeRole.class, metadata, inits);
    }

    public QNodeRole(Class<? extends NodeRole> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
    }

}

