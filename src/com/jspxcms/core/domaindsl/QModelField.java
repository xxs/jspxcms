package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.ModelField;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QModelField is a Querydsl query type for ModelField
 */

public class QModelField extends EntityPathBase<ModelField> {

    private static final long serialVersionUID = -1578447761;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QModelField modelField = new QModelField("modelField");

    public final MapPath<String, String, StringPath> customs = this.<String, String, StringPath>createMap("customs", String.class, String.class, StringPath.class);

    public final BooleanPath dblColumn = createBoolean("dblColumn");

    public final StringPath defValue = createString("defValue");

    public final BooleanPath disabled = createBoolean("disabled");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> innerType = createNumber("innerType", Integer.class);

    public final StringPath label = createString("label");

    public final QModel model;

    public final StringPath name = createString("name");

    public final StringPath prompt = createString("prompt");

    public final BooleanPath required = createBoolean("required");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QModelField(String variable) {
        this(ModelField.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QModelField(Path<? extends ModelField> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QModelField(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QModelField(PathMetadata<?> metadata, PathInits inits) {
        this(ModelField.class, metadata, inits);
    }

    public QModelField(Class<? extends ModelField> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.model = inits.isInitialized("model") ? new QModel(forProperty("model"), inits.get("model")) : null;
    }

}

