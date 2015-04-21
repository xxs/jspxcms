package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.Site;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QSite is a Querydsl query type for Site
 */

public class QSite extends EntityPathBase<Site> {

    private static final long serialVersionUID = 719786725;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QSite site = new QSite("site");

    public final ListPath<Site, QSite> children = this.<Site, QSite>createList("children", Site.class, QSite.class, PathInits.DIRECT);

    public final MapPath<String, String, StringPath> clobs = this.<String, String, StringPath>createMap("clobs", String.class, String.class, StringPath.class);

    public final MapPath<String, String, StringPath> customs = this.<String, String, StringPath>createMap("customs", String.class, String.class, StringPath.class);

    public final BooleanPath def = createBoolean("def");

    public final StringPath domain = createString("domain");

    public final StringPath fullName = createString("fullName");

    public final QGlobal global;

    public final StringPath htmlPath = createString("htmlPath");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath identifyDomain = createBoolean("identifyDomain");

    public final StringPath name = createString("name");

    public final StringPath noPicture = createString("noPicture");

    public final StringPath number = createString("number");

    public final QOrg org;

    public final QSite parent;

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class, PathInits.DIRECT);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath templateTheme = createString("templateTheme");

    public final NumberPath<Integer> treeLevel = createNumber("treeLevel", Integer.class);

    public final StringPath treeMax = createString("treeMax");

    public final StringPath treeNumber = createString("treeNumber");

    public final BooleanPath urlRewrite = createBoolean("urlRewrite");

    public QSite(String variable) {
        this(Site.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QSite(Path<? extends Site> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSite(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QSite(PathMetadata<?> metadata, PathInits inits) {
        this(Site.class, metadata, inits);
    }

    public QSite(Class<? extends Site> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.global = inits.isInitialized("global") ? new QGlobal(forProperty("global")) : null;
        this.org = inits.isInitialized("org") ? new QOrg(forProperty("org"), inits.get("org")) : null;
        this.parent = inits.isInitialized("parent") ? new QSite(forProperty("parent"), inits.get("parent")) : null;
    }

}

