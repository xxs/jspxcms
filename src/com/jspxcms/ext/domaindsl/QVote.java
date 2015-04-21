package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.Vote;
import com.jspxcms.ext.domain.VoteOption;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QVote is a Querydsl query type for Vote
 */

public class QVote extends EntityPathBase<Vote> {

    private static final long serialVersionUID = -1851755292;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QVote vote = new QVote("vote");

    public final DateTimePath<java.util.Date> beginDate = createDateTime("beginDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> interval = createNumber("interval", Integer.class);

    public final NumberPath<Integer> maxSelected = createNumber("maxSelected", Integer.class);

    public final NumberPath<Integer> mode = createNumber("mode", Integer.class);

    public final StringPath number = createString("number");

    public final ListPath<VoteOption, QVoteOption> options = this.<VoteOption, QVoteOption>createList("options", VoteOption.class, QVoteOption.class, PathInits.DIRECT);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final com.jspxcms.core.domaindsl.QSite site;

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> total = createNumber("total", Integer.class);

    public QVote(String variable) {
        this(Vote.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QVote(Path<? extends Vote> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVote(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVote(PathMetadata<?> metadata, PathInits inits) {
        this(Vote.class, metadata, inits);
    }

    public QVote(Class<? extends Vote> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new com.jspxcms.core.domaindsl.QSite(forProperty("site"), inits.get("site")) : null;
    }

}

