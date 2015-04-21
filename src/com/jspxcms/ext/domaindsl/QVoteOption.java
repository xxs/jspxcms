package com.jspxcms.ext.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.ext.domain.VoteOption;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QVoteOption is a Querydsl query type for VoteOption
 */

public class QVoteOption extends EntityPathBase<VoteOption> {

    private static final long serialVersionUID = -692736391;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QVoteOption voteOption = new QVoteOption("voteOption");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final StringPath title = createString("title");

    public final QVote vote;

    public QVoteOption(String variable) {
        this(VoteOption.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QVoteOption(Path<? extends VoteOption> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVoteOption(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QVoteOption(PathMetadata<?> metadata, PathInits inits) {
        this(VoteOption.class, metadata, inits);
    }

    public QVoteOption(Class<? extends VoteOption> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vote = inits.isInitialized("vote") ? new QVote(forProperty("vote"), inits.get("vote")) : null;
    }

}

