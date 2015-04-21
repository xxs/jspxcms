package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.WorkflowLog;
import com.jspxcms.core.domain.WorkflowProcess;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflowProcess is a Querydsl query type for WorkflowProcess
 */

public class QWorkflowProcess extends EntityPathBase<WorkflowProcess> {

    private static final long serialVersionUID = 2017425234;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflowProcess workflowProcess = new QWorkflowProcess("workflowProcess");

    public final DateTimePath<java.util.Date> beginDate = createDateTime("beginDate", java.util.Date.class);

    public final NumberPath<Integer> dataId = createNumber("dataId", Integer.class);

    public final NumberPath<Integer> dataType = createNumber("dataType", Integer.class);

    public final BooleanPath end = createBoolean("end");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final SetPath<WorkflowLog, QWorkflowLog> logs = this.<WorkflowLog, QWorkflowLog>createSet("logs", WorkflowLog.class, QWorkflowLog.class, PathInits.DIRECT);

    public final BooleanPath rejection = createBoolean("rejection");

    public final QSite site;

    public final QWorkflowStep step;

    public final QUser user;

    public final QWorkflow workflow;

    public QWorkflowProcess(String variable) {
        this(WorkflowProcess.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflowProcess(Path<? extends WorkflowProcess> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowProcess(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowProcess(PathMetadata<?> metadata, PathInits inits) {
        this(WorkflowProcess.class, metadata, inits);
    }

    public QWorkflowProcess(Class<? extends WorkflowProcess> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
        this.step = inits.isInitialized("step") ? new QWorkflowStep(forProperty("step"), inits.get("step")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
        this.workflow = inits.isInitialized("workflow") ? new QWorkflow(forProperty("workflow"), inits.get("workflow")) : null;
    }

}

