package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Global;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;


/**
 * QGlobal is a Querydsl query type for Global
 */

public class QGlobal extends EntityPathBase<Global> {

    private static final long serialVersionUID = -115619903;

    public static final QGlobal global = new QGlobal("global");

    public final MapPath<String, String, StringPath> clobs = this.<String, String, StringPath>createMap("clobs", String.class, String.class, StringPath.class);

    public final StringPath contextPath = createString("contextPath");

    public final MapPath<String, String, StringPath> customs = this.<String, String, StringPath>createMap("customs", String.class, String.class, StringPath.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> port = createNumber("port", Integer.class);

    public final StringPath protocol = createString("protocol");

    public final StringPath version = createString("version");

    public final BooleanPath withDomain = createBoolean("withDomain");

    public QGlobal(String variable) {
        super(Global.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QGlobal(Path<? extends Global> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QGlobal(PathMetadata<?> metadata) {
        super(Global.class, metadata);
    }

}

