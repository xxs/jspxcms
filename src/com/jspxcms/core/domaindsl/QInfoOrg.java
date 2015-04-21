package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoOrg;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoOrg is a Querydsl query type for InfoOrg
 */

public class QInfoOrg extends EntityPathBase<InfoOrg> {

    private static final long serialVersionUID = -1759892744;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoOrg infoOrg = new QInfoOrg("infoOrg");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final QOrg org;

    public final BooleanPath viewPerm = createBoolean("viewPerm");

    public QInfoOrg(String variable) {
        this(InfoOrg.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoOrg(Path<? extends InfoOrg> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoOrg(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoOrg(PathMetadata<?> metadata, PathInits inits) {
        this(InfoOrg.class, metadata, inits);
    }

    public QInfoOrg(Class<? extends InfoOrg> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.org = inits.isInitialized("org") ? new QOrg(forProperty("org"), inits.get("org")) : null;
    }

}

