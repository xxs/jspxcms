package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.jspxcms.core.domain.SensitiveWord;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;

import com.mysema.query.types.Path;


/**
 * QSensitiveWord is a Querydsl query type for SensitiveWord
 */

public class QSensitiveWord extends EntityPathBase<SensitiveWord> {

    private static final long serialVersionUID = -65188638;

    public static final QSensitiveWord sensitiveWord = new QSensitiveWord("sensitiveWord");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath replacement = createString("replacement");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public QSensitiveWord(String variable) {
        super(SensitiveWord.class, forVariable(variable));
    }

    @SuppressWarnings("all")
    public QSensitiveWord(Path<? extends SensitiveWord> path) {
        super((Class)path.getType(), path.getMetadata());
    }

    public QSensitiveWord(PathMetadata<?> metadata) {
        super(SensitiveWord.class, metadata);
    }

}

