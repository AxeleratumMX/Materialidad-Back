package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.ContractValueTemplate;
import com.mx.axeleratum.americantower.contract.core.model.DataType;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

public class QContractValueTemplate extends EntityPathBase<ContractValueTemplate> {
	private static final long serialVersionUID = 1L;

	public static final QContractValueTemplate contractValueTemplate = new QContractValueTemplate(
			"contractValueTemplate");

	private static final PathInits INITS = PathInits.DIRECT2;

	public final StringPath id = createString("id");
	public final StringPath sectionId = createString("sectionId");
	public final StringPath description = createString("sectionId");
	public final StringPath dataType = createString("sectionId");
	public final EnumPath<DataType> type = createEnum(
			"type", DataType.class);
	public final StringPath content = createString("sectionId");
	public final BooleanPath editable = createBoolean("editable");
	public final BooleanPath optional = createBoolean("optional");
	public final StringPath apiRestListOfValue = createString("apiRestListOfValue");
	public final StringPath regex = createString("regex");

	public final QOperation operation = this.createPath("operation");

	protected QOperation createPath(String variable) {
		return add(QOperation.operation);
	}

	protected <T> PathBuilder<T> createBuilder(Class<? extends T> type, String variable) {
		PathBuilder<T> entityPath = new PathBuilder<T>(type, variable);
		return add(entityPath);
	}

	public QContractValueTemplate(String variable) {
		super(ContractValueTemplate.class, PathMetadataFactory.forVariable(variable), INITS);
	}

	public QContractValueTemplate(Path<? extends ContractValueTemplate> path) {
		super(path.getType(), path.getMetadata(), INITS);
	}

	public QContractValueTemplate(PathMetadata metadata) {
		super(ContractValueTemplate.class, metadata, INITS);
	}

}
