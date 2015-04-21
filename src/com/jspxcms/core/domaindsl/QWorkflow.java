package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Workflow;
import com.jspxcms.core.domain.WorkflowStep;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QWorkflow is a Querydsl query type for Workflow
 */

public class QWorkflow extends EntityPathBase<Workflow> {

    private static final long serialVersionUID = 1135479901;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QWorkflow workflow = new QWorkflow("workflow");

    public final StringPath description = createString("description");

    public final QWorkflowGroup group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final ListPath<WorkflowStep, QWorkflowStep> steps = this.<WorkflowStep, QWorkflowStep>createList("steps", WorkflowStep.class, QWorkflowStep.class, PathInits.DIRECT);

    public QWorkflow(String variable) {
        this(Workflow.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QWorkflow(Path<? extends Workflow> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflow(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QWorkflow(PathMetadata<?> metadata, PathInits inits) {
        this(Workflow.class, metadata, inits);
    }

    public QWorkflow(Class<? extends Workflow> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QWorkflowGroup(forProperty("group"), inits.get("group")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

