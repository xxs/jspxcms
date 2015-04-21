package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Org;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QOrg is a Querydsl query type for Org
 */

public class QOrg extends EntityPathBase<Org> {

    private static final long serialVersionUID = 715952006;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOrg org = new QOrg("org");

    public final StringPath address = createString("address");

    public final ListPath<Org, QOrg> children = this.<Org, QOrg>createList("children", Org.class, QOrg.class, PathInits.DIRECT);

    public final StringPath description = createString("description");

    public final StringPath fax = createString("fax");

    public final StringPath fullName = createString("fullName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath number = createString("number");

    public final QOrg parent;

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> treeLevel = createNumber("treeLevel", Integer.class);

    public final StringPath treeMax = createString("treeMax");

    public final StringPath treeNumber = createString("treeNumber");

    public QOrg(String variable) {
        this(Org.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QOrg(Path<? extends Org> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrg(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrg(PathMetadata<?> metadata, PathInits inits) {
        this(Org.class, metadata, inits);
    }

    public QOrg(Class<? extends Org> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QOrg(forProperty("parent"), inits.get("parent")) : null;
    }

}

