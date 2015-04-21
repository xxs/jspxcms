package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeOrg;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNodeOrg is a Querydsl query type for NodeOrg
 */

public class QNodeOrg extends EntityPathBase<NodeOrg> {

    private static final long serialVersionUID = -1590857436;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNodeOrg nodeOrg = new QNodeOrg("nodeOrg");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QNode node;

    public final QOrg org;

    public final BooleanPath viewPerm = createBoolean("viewPerm");

    public QNodeOrg(String variable) {
        this(NodeOrg.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNodeOrg(Path<? extends NodeOrg> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeOrg(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeOrg(PathMetadata<?> metadata, PathInits inits) {
        this(NodeOrg.class, metadata, inits);
    }

    public QNodeOrg(Class<? extends NodeOrg> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
        this.org = inits.isInitialized("org") ? new QOrg(forProperty("org"), inits.get("org")) : null;
    }

}

