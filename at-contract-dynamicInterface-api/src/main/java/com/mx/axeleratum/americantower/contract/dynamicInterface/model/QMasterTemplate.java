package com.mx.axeleratum.americantower.contract.dynamicInterface.model;

import com.mx.axeleratum.americantower.contract.core.model.ContractValueTemplate;
import com.mx.axeleratum.americantower.contract.core.model.SectionTemplate;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

public class QMasterTemplate extends EntityPathBase<MasterTemplate> {
 
    private static final long serialVersionUID = 1;
 
    public static final QMasterTemplate masterTemplate = new QMasterTemplate("masterTemplate");
 
    public final StringPath id = createString("id");
 
    public final StringPath tipo = createString("tipo");
    
    public final StringPath nameTemplate = createString("nameTemplate");
    
    public final ListPath<ContractValueTemplate,QContractValueTemplate> values = this.<ContractValueTemplate, QContractValueTemplate>createList("values", ContractValueTemplate.class, QContractValueTemplate.class, PathInits.DIRECT2);
   
    public final ListPath<SectionTemplate,QSectionTemplate> sections = this.<SectionTemplate, QSectionTemplate>createList("sections", SectionTemplate.class, QSectionTemplate.class, PathInits.DIRECT2);
    
    public QMasterTemplate(String variable) {
        super(MasterTemplate.class, PathMetadataFactory.forVariable(variable));
    }
 
    public QMasterTemplate(Path<? extends MasterTemplate> path) {
        super(path.getType(), path.getMetadata());
    }
 
    public QMasterTemplate(PathMetadata metadata) {
        super(MasterTemplate.class, metadata);
    }
}
