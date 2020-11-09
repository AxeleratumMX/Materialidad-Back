package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.SectionTemplate;
import com.mx.axeleratum.americantower.contract.core.model.SectionType;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.StringPath;

public class QSectionTemplate extends EntityPathBase<SectionTemplate> {
	private static final long serialVersionUID = 6479610811405368586L;
	public static final QSectionTemplate sectionTemplate = new QSectionTemplate("sectionTemplate");

	public final StringPath id = createString("id");
	public final StringPath description = createString("description");
    public final EnumPath<SectionType> type = createEnum("type", SectionType.class);

	public QSectionTemplate(String variable) {
		super(SectionTemplate.class, PathMetadataFactory.forVariable(variable));
	}

	public QSectionTemplate(Path<? extends SectionTemplate> path) {
		super(path.getType(), path.getMetadata());
	}

	public QSectionTemplate(PathMetadata metadata) {
		super(SectionTemplate.class, metadata);
	}
}
