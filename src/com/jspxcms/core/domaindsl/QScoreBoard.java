package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.ScoreBoard;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QScoreBoard is a Querydsl query type for ScoreBoard
 */

public class QScoreBoard extends EntityPathBase<ScoreBoard> {

    private static final long serialVersionUID = -2103144302;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QScoreBoard scoreBoard = new QScoreBoard("scoreBoard");

    public final NumberPath<Integer> fid = createNumber("fid", Integer.class);

    public final StringPath ftype = createString("ftype");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QScoreItem item;

    public final NumberPath<Integer> votes = createNumber("votes", Integer.class);

    public QScoreBoard(String variable) {
        this(ScoreBoard.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QScoreBoard(Path<? extends ScoreBoard> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreBoard(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreBoard(PathMetadata<?> metadata, PathInits inits) {
        this(ScoreBoard.class, metadata, inits);
    }

    public QScoreBoard(Class<? extends ScoreBoard> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QScoreItem(forProperty("item"), inits.get("item")) : null;
    }

}

