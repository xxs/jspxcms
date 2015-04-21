package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeBuffer;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNodeBuffer is a Querydsl query type for NodeBuffer
 */

public class QNodeBuffer extends EntityPathBase<NodeBuffer> {

    private static final long serialVersionUID = 1360898560;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNodeBuffer nodeBuffer = new QNodeBuffer("nodeBuffer");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QNode node;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QNodeBuffer(String variable) {
        this(NodeBuffer.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNodeBuffer(Path<? extends NodeBuffer> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeBuffer(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeBuffer(PathMetadata<?> metadata, PathInits inits) {
        this(NodeBuffer.class, metadata, inits);
    }

    public QNodeBuffer(Class<? extends NodeBuffer> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
    }

}

