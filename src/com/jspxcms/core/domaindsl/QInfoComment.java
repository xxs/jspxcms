package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoComment;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QInfoComment is a Querydsl query type for InfoComment
 */

public class QInfoComment extends EntityPathBase<InfoComment> {

    private static final long serialVersionUID = 1480212595;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QInfoComment infoComment = new QInfoComment("infoComment");

    public final QComment _super;

    //inherited
    public final DateTimePath<java.util.Date> auditDate;

    // inherited
    public final QUser auditor;

    //inherited
    public final DateTimePath<java.util.Date> creationDate;

    // inherited
    public final QUser creator;

    //inherited
    public final NumberPath<Integer> fid;

    //inherited
    public final StringPath ftype;

    //inherited
    public final NumberPath<Integer> id;

    public final QInfo info;

    //inherited
    public final StringPath ip;

    //inherited
    public final NumberPath<Integer> score;

    // inherited
    public final QSite site;

    //inherited
    public final NumberPath<Integer> status;

    //inherited
    public final StringPath text;

    public QInfoComment(String variable) {
        this(InfoComment.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QInfoComment(Path<? extends InfoComment> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoComment(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QInfoComment(PathMetadata<?> metadata, PathInits inits) {
        this(InfoComment.class, metadata, inits);
    }

    public QInfoComment(Class<? extends InfoComment> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QComment(type, metadata, inits);
        this.auditDate = _super.auditDate;
        this.auditor = _super.auditor;
        this.creationDate = _super.creationDate;
        this.creator = _super.creator;
        this.fid = _super.fid;
        this.ftype = _super.ftype;
        this.id = _super.id;
        this.info = inits.isInitialized("info") ? new QInfo(forProperty("info"), inits.get("info")) : null;
        this.ip = _super.ip;
        this.score = _super.score;
        this.site = _super.site;
        this.status = _super.status;
        this.text = _super.text;
    }

}

