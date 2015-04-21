package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Attribute;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAttribute is a Querydsl query type for Attribute
 */

public class QAttribute extends EntityPathBase<Attribute> {

    private static final long serialVersionUID = -243529282;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QAttribute attribute = new QAttribute("attribute");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> imageHeight = createNumber("imageHeight", Integer.class);

    public final NumberPath<Integer> imageWidth = createNumber("imageWidth", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final BooleanPath withImage = createBoolean("withImage");

    public QAttribute(String variable) {
        this(Attribute.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QAttribute(Path<? extends Attribute> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAttribute(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAttribute(PathMetadata<?> metadata, PathInits inits) {
        this(Attribute.class, metadata, inits);
    }

    public QAttribute(Class<? extends Attribute> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

