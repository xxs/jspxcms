package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoImage;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;


/**
 * QInfoImage is a Querydsl query type for InfoImage
 */

public class QInfoImage extends BeanPath<InfoImage> {

    private static final long serialVersionUID = 954495087;

    public static final QInfoImage infoImage = new QInfoImage("infoImage");

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    public final StringPath text = createString("text");

    public QInfoImage(String variable) {
        super(InfoImage.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QInfoImage(Path<? extends InfoImage> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QInfoImage(PathMetadata<?> metadata) {
        super(InfoImage.class, metadata);
    }

}

