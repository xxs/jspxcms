package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.Ad;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAd is a Querydsl query type for Ad
 */

public class QAd extends EntityPathBase<Ad> {

    private static final long serialVersionUID = -211983203;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QAd ad = new QAd("ad");

    public final DateTimePath<java.util.Date> beginDate = createDateTime("beginDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final StringPath flash = createString("flash");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    public final StringPath script = createString("script");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public final QAdSlot slot;

    public final StringPath text = createString("text");

    public final StringPath url = createString("url");

    public QAd(String variable) {
        this(Ad.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QAd(Path<? extends Ad> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAd(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAd(PathMetadata<?> metadata, PathInits inits) {
        this(Ad.class, metadata, inits);
    }

    public QAd(Class<? extends Ad> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
        this.slot = inits.isInitialized("slot") ? new QAdSlot(forProperty("slot"), inits.get("slot")) : null;
    }

}

