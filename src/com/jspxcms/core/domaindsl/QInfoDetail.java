package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoDetail;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoDetail is a Querydsl query type for InfoDetail
 */

public class QInfoDetail extends EntityPathBase<InfoDetail> {

    private static final long serialVersionUID = -625396803;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoDetail infoDetail = new QInfoDetail("infoDetail");

    public final BooleanPath allowComment = createBoolean("allowComment");

    public final StringPath author = createString("author");

    public final StringPath color = createString("color");

    public final BooleanPath em = createBoolean("em");

    public final StringPath file = createString("file");

    public final NumberPath<Long> fileLength = createNumber("fileLength", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath fullTitle = createString("fullTitle");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QInfo info;

    public final StringPath infoPath = createString("infoPath");

    public final StringPath infoTemplate = createString("infoTemplate");

    public final StringPath largeImage = createString("largeImage");

    public final StringPath link = createString("link");

    public final StringPath metaDescription = createString("metaDescription");

    public final BooleanPath newWindow = createBoolean("newWindow");

    public final StringPath smallImage = createString("smallImage");

    public final StringPath source = createString("source");

    public final StringPath sourceUrl = createString("sourceUrl");

    public final StringPath stepName = createString("stepName");

    public final BooleanPath strong = createBoolean("strong");

    public final StringPath subtitle = createString("subtitle");

    public final StringPath title = createString("title");

    public final StringPath video = createString("video");

    public final StringPath videoName = createString("videoName");

    public QInfoDetail(String variable) {
        this(InfoDetail.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoDetail(Path<? extends InfoDetail> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoDetail(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoDetail(PathMetadata<?> metadata, PathInits inits) {
        this(InfoDetail.class, metadata, inits);
    }

    public QInfoDetail(Class<? extends InfoDetail> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
    }

}

