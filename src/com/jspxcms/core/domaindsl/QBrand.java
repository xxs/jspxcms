package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.jspxcms.core.domain.Brand;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.BooleanPath;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;


/**
 * QBrand is a Querydsl query type for Brand
 */

public class QBrand extends EntityPathBase<Brand> {

    private static final long serialVersionUID = 209222162;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QBrand brand = new QBrand("brand");

    public final StringPath description = createString("description");

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath logo = createString("logo");

    public final StringPath name = createString("name");

    public final BooleanPath recommend = createBoolean("recommend");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath url = createString("url");

    public final BooleanPath withLogo = createBoolean("withLogo");

    public QBrand(String variable) {
        this(Brand.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QBrand(Path<? extends Brand> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBrand(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QBrand(PathMetadata<?> metadata, PathInits inits) {
        this(Brand.class, metadata, inits);
    }

    public QBrand(Class<? extends Brand> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
    }

}

