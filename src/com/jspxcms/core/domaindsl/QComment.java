package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Comment;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */

public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 1540075009;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QComment comment = new QComment("comment");

    public final DateTimePath<java.util.Date> auditDate = createDateTime("auditDate", java.util.Date.class);

    public final QUser auditor;

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final QUser creator;

    public final NumberPath<Integer> fid = createNumber("fid", Integer.class);

    public final StringPath ftype = createString("ftype");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath text = createString("text");

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QComment(Path<? extends Comment> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComment(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QComment(PathMetadata<?> metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.auditor = inits.isInitialized("auditor") ? new QUser(forProperty("auditor"), inits.get("auditor")) : null;
        this.creator = inits.isInitialized("creator") ? new QUser(forProperty("creator"), inits.get("creator")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

