package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.Node;
import com.jspxcms.core.domain.NodeBuffer;
import com.jspxcms.core.domain.NodeDetail;
import com.jspxcms.core.domain.NodeMemberGroup;
import com.jspxcms.core.domain.NodeOrg;
import com.jspxcms.core.domain.NodeRole;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QNode is a Querydsl query type for Node
 */

public class QNode extends EntityPathBase<Node> {

    private static final long serialVersionUID = 719643040;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QNode node = new QNode("node");

    public final SetPath<NodeBuffer, QNodeBuffer> buffers = this.<NodeBuffer, QNodeBuffer>createSet("buffers", NodeBuffer.class, QNodeBuffer.class, PathInits.DIRECT);

    public final ListPath<Node, QNode> children = this.<Node, QNode>createList("children", Node.class, QNode.class, PathInits.DIRECT);

    public final MapPath<String, String, StringPath> clobs = this.<String, String, StringPath>createMap("clobs", String.class, String.class, StringPath.class);

    public final DateTimePath<java.util.Date> creationDate = createDateTime("creationDate", java.util.Date.class);

    public final QUser creator;

    public final MapPath<String, String, StringPath> customs = this.<String, String, StringPath>createMap("customs", String.class, String.class, StringPath.class);

    public final SetPath<NodeDetail, QNodeDetail> details = this.<NodeDetail, QNodeDetail>createSet("details", NodeDetail.class, QNodeDetail.class, PathInits.DIRECT);

    public final StringPath domain = createString("domain");

    public final StringPath domainPath = createString("domainPath");

    public final BooleanPath hidden = createBoolean("hidden");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QModel infoModel;

    public final StringPath name = createString("name");

    public final SetPath<NodeMemberGroup, QNodeMemberGroup> nodeGroups = this.<NodeMemberGroup, QNodeMemberGroup>createSet("nodeGroups", NodeMemberGroup.class, QNodeMemberGroup.class, PathInits.DIRECT);

    public final QModel nodeModel;

    public final SetPath<NodeOrg, QNodeOrg> nodeOrgs = this.<NodeOrg, QNodeOrg>createSet("nodeOrgs", NodeOrg.class, QNodeOrg.class, PathInits.DIRECT);

    public final SetPath<NodeRole, QNodeRole> nodeRoles = this.<NodeRole, QNodeRole>createSet("nodeRoles", NodeRole.class, QNodeRole.class, PathInits.DIRECT);

    public final StringPath number = createString("number");

    public final NumberPath<Byte> p1 = createNumber("p1", Byte.class);

    public final NumberPath<Byte> p2 = createNumber("p2", Byte.class);

    public final NumberPath<Byte> p3 = createNumber("p3", Byte.class);

    public final NumberPath<Byte> p4 = createNumber("p4", Byte.class);

    public final NumberPath<Byte> p5 = createNumber("p5", Byte.class);

    public final NumberPath<Byte> p6 = createNumber("p6", Byte.class);

    public final QNode parent;

    public final BooleanPath realNode = createBoolean("realNode");

    public final NumberPath<Integer> refers = createNumber("refers", Integer.class);

    public final QSite site;

    public final NumberPath<Integer> treeLevel = createNumber("treeLevel", Integer.class);

    public final StringPath treeMax = createString("treeMax");

    public final StringPath treeNumber = createString("treeNumber");

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public final QWorkflow workflow;

    public QNode(String variable) {
        this(Node.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QNode(Path<? extends Node> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNode(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QNode(PathMetadata<?> metadata, PathInits inits) {
        this(Node.class, metadata, inits);
    }

    public QNode(Class<? extends Node> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new QUser(forProperty("creator"), inits.get("creator")) : null;
        this.infoModel = inits.isInitialized("infoModel") ? new QModel(forProperty("infoModel"), inits.get("infoModel")) : null;
        this.nodeModel = inits.isInitialized("nodeModel") ? new QModel(forProperty("nodeModel"), inits.get("nodeModel")) : null;
        this.parent = inits.isInitialized("parent") ? new QNode(forProperty("parent"), inits.get("parent")) : null;
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
        this.workflow = inits.isInitialized("workflow") ? new QWorkflow(forProperty("workflow"), inits.get("workflow")) : null;
    }

}

