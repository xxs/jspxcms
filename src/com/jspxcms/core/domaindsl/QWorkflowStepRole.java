package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.WorkflowStepRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflowStepRole is a Querydsl query type for WorkflowStepRole
 */
public class QWorkflowStepRole extends EntityPathBase<WorkflowStepRole> {

    private static final long serialVersionUID = 549288159;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflowStepRole workflowStepRole = new QWorkflowStepRole("workflowStepRole");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QRole role;

    public final NumberPath<Integer> roleIndex = createNumber("roleIndex", Integer.class);

    public final QWorkflowStep step;

    public QWorkflowStepRole(String variable) {
        this(WorkflowStepRole.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflowStepRole(Path<? extends WorkflowStepRole> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowStepRole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowStepRole(PathMetadata<?> metadata, PathInits inits) {
        this(WorkflowStepRole.class, metadata, inits);
    }

    public QWorkflowStepRole(Class<? extends WorkflowStepRole> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.role = inits.isInitialized("role") ? new QRole(forProperty("role"), inits.get("role")) : null;
        this.step = inits.isInitialized("step") ? new QWorkflowStep(forProperty("step"), inits.get("step")) : null;
    }

}

