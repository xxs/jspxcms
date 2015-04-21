package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.WorkflowGroup;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflowGroup is a Querydsl query type for WorkflowGroup
 */

public class QWorkflowGroup extends EntityPathBase<WorkflowGroup> {

    private static final long serialVersionUID = -167105502;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflowGroup workflowGroup = new QWorkflowGroup("workflowGroup");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public QWorkflowGroup(String variable) {
        this(WorkflowGroup.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflowGroup(Path<? extends WorkflowGroup> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowGroup(PathMetadata<?> metadata, PathInits inits) {
        this(WorkflowGroup.class, metadata, inits);
    }

    public QWorkflowGroup(Class<? extends WorkflowGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

