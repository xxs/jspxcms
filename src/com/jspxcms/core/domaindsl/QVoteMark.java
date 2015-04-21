package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.VoteMark;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QVoteMark is a Querydsl query type for VoteMark
 */

public class QVoteMark extends EntityPathBase<VoteMark> {

    private static final long serialVersionUID = -556368523;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QVoteMark voteMark = new QVoteMark("voteMark");

    public final StringPath cookie = createString("cookie");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final NumberPath<Integer> fid = createNumber("fid", Integer.class);

    public final StringPath ftype = createString("ftype");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final QUser user;

    public QVoteMark(String variable) {
        this(VoteMark.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QVoteMark(Path<? extends VoteMark> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVoteMark(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVoteMark(PathMetadata<?> metadata, PathInits inits) {
        this(VoteMark.class, metadata, inits);
    }

    public QVoteMark(Class<? extends VoteMark> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

