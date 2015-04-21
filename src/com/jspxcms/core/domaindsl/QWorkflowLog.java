package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.WorkflowLog;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflowLog is a Querydsl query type for WorkflowLog
 */

public class QWorkflowLog extends EntityPathBase<WorkflowLog> {

    private static final long serialVersionUID = -80616025;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflowLog workflowLog = new QWorkflowLog("workflowLog");

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final StringPath from = createString("from");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath opinion = createString("opinion");

    public final QWorkflowProcess process;

    public final QSite site;

    public final StringPath to = createString("to");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final QUser user;

    public QWorkflowLog(String variable) {
        this(WorkflowLog.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflowLog(Path<? extends WorkflowLog> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowLog(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowLog(PathMetadata<?> metadata, PathInits inits) {
        this(WorkflowLog.class, metadata, inits);
    }

    public QWorkflowLog(Class<? extends WorkflowLog> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.process = inits.isInitialized("process") ? new QWorkflowProcess(forProperty("process"), inits.get("process")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

