package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import com.jspxcms.core.domain.Attr;
import com.jspxcms.core.domain.ParameterGroup;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathInits;
import com.mysema.query.types.path.StringPath;


/**
 * QParameterGroup is a Querydsl query type for ParameterGroup
 */

public class QParameterGroup extends EntityPathBase<ParameterGroup> {

	private static final long serialVersionUID = -1163110078546762031L;
	
	private static final PathInits INITS = PathInits.DIRECT;

	public static final QParameterGroup parameterGroup = new QParameterGroup("parameterGroup");


    public final NumberPath<Integer> id = createNumber("id", Integer.class);


    public final StringPath name = createString("name");
    
    public final QSite site;
    
    public final QNode node;

    public QParameterGroup(String variable) {
        this(ParameterGroup.class, forVariable(variable), INITS);
    }
    
    @SuppressWarnings("all")
    public QParameterGroup(Path<? extends Attr> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QParameterGroup(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QParameterGroup(PathMetadata<?> metadata, PathInits inits) {
        this(ParameterGroup.class, metadata, inits);
    }
    
    public QParameterGroup(Class<? extends ParameterGroup> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.site = inits.isInitialized("site") ? new QSite(forProperty("site"), inits.get("site")) : null;
        this.node = inits.isInitialized("node") ? new QNode(forProperty("node"), inits.get("node")) : null;
    }
    
   

}

