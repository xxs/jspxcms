package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Model;
import com.jspxcms.core.domain.ModelField;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QModel is a Querydsl query type for Model
 */

public class QModel extends EntityPathBase<Model> {

    private static final long serialVersionUID = 833174347;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QModel model = new QModel("model");

    public final MapPath<String, String, StringPath> customs = this.<String, String, StringPath>createMap("customs", String.class, String.class, StringPath.class);

    public final ListPath<ModelField, QModelField> fields = this.<ModelField, QModelField>createList("fields", ModelField.class, QModelField.class, PathInits.DIRECT);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final StringPath type = createString("type");

    public QModel(String variable) {
        this(Model.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QModel(Path<? extends Model> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QModel(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QModel(PathMetadata<?> metadata, PathInits inits) {
        this(Model.class, metadata, inits);
    }

    public QModel(Class<? extends Model> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

