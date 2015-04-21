package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.SpecialCategory;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSpecialCategory is a Querydsl query type for SpecialCategory
 */

public class QSpecialCategory extends EntityPathBase<SpecialCategory> {

    private static final long serialVersionUID = -2019584391;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QSpecialCategory specialCategory = new QSpecialCategory("specialCategory");

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath metaDescription = createString("metaDescription");

    public final StringPath metaKeywords = createString("metaKeywords");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public QSpecialCategory(String variable) {
        this(SpecialCategory.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QSpecialCategory(Path<? extends SpecialCategory> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSpecialCategory(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSpecialCategory(PathMetadata<?> metadata, PathInits inits) {
        this(SpecialCategory.class, metadata, inits);
    }

    public QSpecialCategory(Class<? extends SpecialCategory> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

