package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeDetail;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNodeDetail is a Querydsl query type for NodeDetail
 */

public class QNodeDetail extends EntityPathBase<NodeDetail> {

    private static final long serialVersionUID = 1403792913;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNodeDetail nodeDetail = new QNodeDetail("nodeDetail");

    public final BooleanPath defPage = createBoolean("defPage");

    public final BooleanPath generateInfo = createBoolean("generateInfo");

    public final BooleanPath generateNode = createBoolean("generateNode");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath infoExtension = createString("infoExtension");

    public final StringPath infoPath = createString("infoPath");

    public final StringPath infoTemplate = createString("infoTemplate");

    public final StringPath largeImage = createString("largeImage");

    public final StringPath link = createString("link");

    public final StringPath metaDescription = createString("metaDescription");

    public final StringPath metaKeywords = createString("metaKeywords");

    public final BooleanPath newWindow = createBoolean("newWindow");

    public final QNode node;

    public final StringPath nodeExtension = createString("nodeExtension");

    public final StringPath nodePath = createString("nodePath");

    public final StringPath nodeTemplate = createString("nodeTemplate");

    public final StringPath smallImage = createString("smallImage");

    public final NumberPath<Integer> staticMethod = createNumber("staticMethod", Integer.class);

    public final NumberPath<Integer> staticPage = createNumber("staticPage", Integer.class);

    public QNodeDetail(String variable) {
        this(NodeDetail.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNodeDetail(Path<? extends NodeDetail> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeDetail(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNodeDetail(PathMetadata<?> metadata, PathInits inits) {
        this(NodeDetail.class, metadata, inits);
    }

    public QNodeDetail(Class<? extends NodeDetail> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
    }

}

