package com.mx.axeleratum.americantower.contract.dynamicInterface.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.querydsl.binding.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

import com.mx.axeleratum.americantower.contract.dynamicInterface.model.MasterTemplate;
import com.mx.axeleratum.americantower.contract.dynamicInterface.model.QMasterTemplate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;


public interface MasterTemplateRepository extends CrudRepository<MasterTemplate,String>, QuerydslPredicateExecutor<MasterTemplate>, QuerydslBinderCustomizer<QMasterTemplate>,  MongoRepository<MasterTemplate,String> {
	
	@Override
    default public void customize(QuerydslBindings bindings, QMasterTemplate root) {
	   bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
	
	public MasterTemplate findByTipo(String tipo);
}

