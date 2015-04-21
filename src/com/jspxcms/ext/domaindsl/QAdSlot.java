package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.Ad;
import com.jspxcms.ext.domain.AdSlot;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QAdSlot is a Querydsl query type for AdSlot
 */

public class QAdSlot extends EntityPathBase<AdSlot> {

    private static final long serialVersionUID = -2032718789;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QAdSlot adSlot = new QAdSlot("adSlot");

    public final ListPath<Ad, QAd> ads = this.<Ad, QAd>createList("ads", Ad.class, QAd.class, PathInits.DIRECT);

    public final StringPath description = createString("description");

    public final NumberPath<Integer> height = createNumber("height", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final com.jspxcms.core.domaindsl.QSite site;

    public final StringPath template = createString("template");

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final NumberPath<Integer> width = createNumber("width", Integer.class);

    public QAdSlot(String variable) {
        this(AdSlot.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QAdSlot(Path<? extends AdSlot> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAdSlot(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAdSlot(PathMetadata<?> metadata, PathInits inits) {
        this(AdSlot.class, metadata, inits);
    }

    public QAdSlot(Class<? extends AdSlot> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
    }

}

