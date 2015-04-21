package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.ScoreGroup;
import com.jspxcms.core.domain.ScoreItem;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QScoreGroup is a Querydsl query type for ScoreGroup
 */

public class QScoreGroup extends EntityPathBase<ScoreGroup> {

    private static final long serialVersionUID = -2098423765;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QScoreGroup scoreGroup = new QScoreGroup("scoreGroup");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final ListPath<ScoreItem, QScoreItem> items = this.<ScoreItem, QScoreItem>createList("items", ScoreItem.class, QScoreItem.class, PathInits.DIRECT);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public QScoreGroup(String variable) {
        this(ScoreGroup.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QScoreGroup(Path<? extends ScoreGroup> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreGroup(PathMetadata<?> metadata, PathInits inits) {
        this(ScoreGroup.class, metadata, inits);
    }

    public QScoreGroup(Class<? extends ScoreGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

