package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Spec;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSpec is a Querydsl query type for Spec
 */

public class QSpec extends EntityPathBase<Spec> {

    private static final long serialVersionUID = -243529282;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QSpec spec = new QSpec("spec");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public QSpec(String variable) {
        this(Spec.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QSpec(Path<? extends Spec> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSpec(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSpec(PathMetadata<?> metadata, PathInits inits) {
        this(Spec.class, metadata, inits);
    }

    public QSpec(Class<? extends Spec> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

