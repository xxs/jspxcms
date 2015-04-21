package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoNode;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoNode is a Querydsl query type for InfoNode
 */

public class QInfoNode extends EntityPathBase<InfoNode> {

    private static final long serialVersionUID = 1277867118;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoNode infoNode = new QInfoNode("infoNode");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final QNode node;

    public final NumberPath<Integer> nodeIndex = createNumber("nodeIndex", Integer.class);

    public QInfoNode(String variable) {
        this(InfoNode.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoNode(Path<? extends InfoNode> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoNode(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoNode(PathMetadata<?> metadata, PathInits inits) {
        this(InfoNode.class, metadata, inits);
    }

    public QInfoNode(Class<? extends InfoNode> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
    }

}

