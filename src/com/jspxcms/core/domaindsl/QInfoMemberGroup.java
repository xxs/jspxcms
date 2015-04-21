package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoMemberGroup;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoMemberGroup is a Querydsl query type for InfoMemberGroup
 */

public class QInfoMemberGroup extends EntityPathBase<InfoMemberGroup> {

    private static final long serialVersionUID = 190919705;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoMemberGroup infoMemberGroup = new QInfoMemberGroup("infoMemberGroup");

    public final QMemberGroup group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final BooleanPath viewPerm = createBoolean("viewPerm");

    public QInfoMemberGroup(String variable) {
        this(InfoMemberGroup.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoMemberGroup(Path<? extends InfoMemberGroup> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoMemberGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoMemberGroup(PathMetadata<?> metadata, PathInits inits) {
        this(InfoMemberGroup.class, metadata, inits);
    }

    public QInfoMemberGroup(Class<? extends InfoMemberGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QMemberGroup(forProperty("group")) : null;
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
    }

}

