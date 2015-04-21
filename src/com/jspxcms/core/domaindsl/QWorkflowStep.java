package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.WorkflowStep;
import com.jspxcms.core.domain.WorkflowStepRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflowStep is a Querydsl query type for WorkflowStep
 */

public class QWorkflowStep extends EntityPathBase<WorkflowStep> {

    private static final long serialVersionUID = 1796083913;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflowStep workflowStep = new QWorkflowStep("workflowStep");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final ListPath<WorkflowStepRole, QWorkflowStepRole> stepRoles = this.<WorkflowStepRole, QWorkflowStepRole>createList("stepRoles", WorkflowStepRole.class, QWorkflowStepRole.class, PathInits.DIRECT);

    public final QWorkflow workflow;

    public QWorkflowStep(String variable) {
        this(WorkflowStep.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflowStep(Path<? extends WorkflowStep> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowStep(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflowStep(PathMetadata<?> metadata, PathInits inits) {
        this(WorkflowStep.class, metadata, inits);
    }

    public QWorkflowStep(Class<? extends WorkflowStep> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.workflow = inits.isInitialized("workflow") ? new QWorkflow(forProperty("workflow"), inits.get("workflow")) : null;
    }

}

