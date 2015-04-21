package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.NodeRole;
import com.jspxcms.core.domain.Role;
import com.jspxcms.core.domain.UserRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QRole is a Querydsl query type for Role
 */

public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = 719762452;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QRole role = new QRole("role");

    public final BooleanPath allInfoPerm = createBoolean("allInfoPerm");

    public final BooleanPath allNodePerm = createBoolean("allNodePerm");

    public final BooleanPath allPerm = createBoolean("allPerm");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath infoFinalPerm = createBoolean("infoFinalPerm");

    public final NumberPath<Integer> infoPermType = createNumber("infoPermType", Integer.class);

    public final StringPath name = createString("name");

    public final SetPath<NodeRole, QNodeRole> nodeRoles = this.<NodeRole, QNodeRole>createSet("nodeRoles", NodeRole.class, QNodeRole.class, PathInits.DIRECT);

    public final StringPath perms = createString("perms");

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final QSite site;

    public final SetPath<UserRole, QUserRole> userRoles = this.<UserRole, QUserRole>createSet("userRoles", UserRole.class, QUserRole.class, PathInits.DIRECT);

    public QRole(String variable) {
        this(Role.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QRole(Path<? extends Role> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRole(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QRole(PathMetadata<?> metadata, PathInits inits) {
        this(Role.class, metadata, inits);
    }

    public QRole(Class<? extends Role> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
    }

}

