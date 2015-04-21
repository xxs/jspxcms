package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserDetail;
import com.jspxcms.core.domain.UserMemberGroup;
import com.jspxcms.core.domain.UserOrg;
import com.jspxcms.core.domain.UserRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for User
 */

public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 719855465;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.util.Date> birthDate = createDateTime("birthDate", java.util.Date.class);

    public final SetPath<UserDetail, QUserDetail> details = this.<UserDetail, QUserDetail>createSet("details", UserDetail.class, QUserDetail.class, PathInits.DIRECT);

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final QMemberGroup group;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mobile = createString("mobile");

    public final QOrg org;

    public final StringPath password = createString("password");

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final StringPath realName = createString("realName");

    public final StringPath salt = createString("salt");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public final ListPath<UserMemberGroup, QUserMemberGroup> userGroups = this.<UserMemberGroup, QUserMemberGroup>createList("userGroups", UserMemberGroup.class, QUserMemberGroup.class, PathInits.DIRECT);

    public final StringPath username = createString("username");

    public final ListPath<UserOrg, QUserOrg> userOrgs = this.<UserOrg, QUserOrg>createList("userOrgs", UserOrg.class, QUserOrg.class, PathInits.DIRECT);

    public final ListPath<UserRole, QUserRole> userRoles = this.<UserRole, QUserRole>createList("userRoles", UserRole.class, QUserRole.class, PathInits.DIRECT);

    public final StringPath validationKey = createString("validationKey");

    public final StringPath validationType = createString("validationType");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QUser(Path<? extends User> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata<?> metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QMemberGroup(forProperty("group")) : null;
        this.org = inits.isInitialized("org") ? new QOrg(forProperty("org"), inits.get("org")) : null;
    }

}

