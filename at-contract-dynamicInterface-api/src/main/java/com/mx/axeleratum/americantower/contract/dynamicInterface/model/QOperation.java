package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.Operation;
import com.mx.axeleratum.americantower.contract.core.model.Param;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

public class QOperation extends EntityPathBase<Operation> {
	public static final QOperation operation = new QOperation("operation");

	private static final long serialVersionUID = 1L;
	public final StringPath name = createString("name");
	public final StringPath descripcion = createString("descripcion");
	public final NumberPath<Integer> level = createNumber("level", Integer.class);
	public final ListPath<Param, QParam> params = this.<Param, QParam>createList("params", Param.class, QParam.class,
			PathInits.DIRECT2);

	public QOperation(String variable) {
		super(Operation.class, PathMetadataFactory.forVariable(variable));
	}

	public QOperation(Path<? extends Operation> path) {
		super(path.getType(), path.getMetadata());
	}

	public QOperation(PathMetadata metadata) {
		super(Operation.class, metadata);
	}
}
