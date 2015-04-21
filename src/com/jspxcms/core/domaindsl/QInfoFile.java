package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.InfoFile;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;


/**
 * QInfoFile is a Querydsl query type for InfoFile
 */

public class QInfoFile extends BeanPath<InfoFile> {

    private static final long serialVersionUID = 1277623272;

    public static final QInfoFile infoFile = new QInfoFile("infoFile");

    public final NumberPath<Integer> downloads = createNumber("downloads", Integer.class);

    public final StringPath file = createString("file");

    public final NumberPath<Long> length = createNumber("length", Long.class);

    public final StringPath name = createString("name");

    public QInfoFile(String variable) {
        super(InfoFile.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QInfoFile(Path<? extends InfoFile> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QInfoFile(PathMetadata<?> metadata) {
        super(InfoFile.class, metadata);
    }

}

