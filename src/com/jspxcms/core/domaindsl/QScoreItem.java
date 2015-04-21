package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.ScoreItem;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QScoreItem is a Querydsl query type for ScoreItem
 */

public class QScoreItem extends EntityPathBase<ScoreItem> {

    private static final long serialVersionUID = 1872032743;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QScoreItem scoreItem = new QScoreItem("scoreItem");

    public final QScoreGroup group;

    public final StringPath icon = createString("icon");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public QScoreItem(String variable) {
        this(ScoreItem.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QScoreItem(Path<? extends ScoreItem> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreItem(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QScoreItem(PathMetadata<?> metadata, PathInits inits) {
        this(ScoreItem.class, metadata, inits);
    }

    public QScoreItem(Class<? extends ScoreItem> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QScoreGroup(forProperty("group"), inits.get("group")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

