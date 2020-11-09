package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.Param;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;

public class QParam extends EntityPathBase<Param> {
	private static final long serialVersionUID = 1L;

	public static final QParam param = new QParam("param");

	public final StringPath id = createString("id");
	public final StringPath value = createString("value");

	public QParam(String variable) {
		super(Param.class, PathMetadataFactory.forVariable(variable));
	}

	public QParam(Path<? extends Param> path) {
		super(path.getType(), path.getMetadata());
	}

	public QParam(PathMetadata metadata) {
		super(Param.class, metadata);
	}
}
