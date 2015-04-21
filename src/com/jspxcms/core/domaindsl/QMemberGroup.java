package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.MemberGroup;
import com.jspxcms.core.domain.NodeMemberGroup;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QMemberGroup is a Querydsl query type for MemberGroup
 */

public class QMemberGroup extends EntityPathBase<MemberGroup> {

    private static final long serialVersionUID = -431674713;

    public static final QMemberGroup memberGroup = new QMemberGroup("memberGroup");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ip = createString("ip");

    public final StringPath name = createString("name");

    public final SetPath<NodeMemberGroup, QNodeMemberGroup> nodeGroups = this.<NodeMemberGroup, QNodeMemberGroup>createSet("nodeGroups", NodeMemberGroup.class, QNodeMemberGroup.class, PathInits.DIRECT);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    public QMemberGroup(String variable) {
        super(MemberGroup.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QMemberGroup(Path<? extends MemberGroup> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QMemberGroup(PathMetadata<?> metadata) {
        super(MemberGroup.class, metadata);
    }

}

