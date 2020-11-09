package com.mx.axeleratum.americantower.contract.dynamicInterfaces;

import com.mx.axeleratum.americantower.contract.core.model.ContractTower;
import com.mx.axeleratum.americantower.contract.dynamicInterface.dto.TemplateHeaderDto;
import com.mx.axeleratum.americantower.contract.dynamicInterface.mapper.DynamicInterfaceMapper;
import com.mx.axeleratum.americantower.contract.core.model.Template;
import com.mx.axeleratum.americantower.contract.dynamicInterface.service.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("unused")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.mx.axeleratum.americantower.contract.dynamicInterface.DynamicInterfaceApplication.class)
@Slf4j
public class ContratTemplateTest {

	@Autowired
	TemplateService service;

	@Autowired
	DynamicInterfaceMapper mapper;
	
	@Test
	public void testFindAllTemplate() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Template> templates = service.findAllTemplates();
		log.info(templates + "");
		assertEquals(templates.size()>0,true);
	}
	
	@Test
	public void testFormatContent() {	
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<Template> templates = service.findAllTemplates();
		assertEquals(templates.size()>0,true);
		String idTemplate = templates.get(0).getIdTemplate();
		Template template = service.findTemplateById(idTemplate);
		if(template == null || template.getContent()==null){
			return;
		}
		byte[] decodedBytes = Base64.getDecoder().decode(template.getContent());
		String decodedString = new String(decodedBytes);
		log.info(decodedString);
		String pattern = "[^{\\}]+(?=})";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(decodedString);
		//Optengo todas las etiquetas del template, que se va hacer con eso aun no se
		while (m.find()) {
			log.info("Found value: " + m.group(0));
		}
		String tipo = template.getTipoContrato();
		log.info("Tipo contrato"+tipo);
	}
	
	@Test
	public void getNewTemplateInstace() {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName());		
		ContractTower contract = new ContractTower();		
		List<Field> privateFields = new ArrayList<>();
		List<String> fields = new ArrayList<String>();
		Field[] allFields = ContractTower.class.getDeclaredFields();
		for (Field field : allFields) {
		    if (Modifier.isPrivate(field.getModifiers())) {
		    	fields.add(field.getName());
		        privateFields.add(field);
		    }
		}
		
		for (String field : fields) {
		  log.info(">>>> "+field);	
		}
		
	}

	@Test
	public void testClientToName() {
		Template template = new Template();
		template.setClientId("5f166b855ed56d41a0225023");
		TemplateHeaderDto templateHeaderDto = mapper.toTemplateHeaderDto(template);
		log.info("sali");
	}

	@Test
	public void testListClientToName() {
		List<Template> list = new ArrayList<>();

		Template template1 = new Template();
		template1.setClientId("5f166b855ed56d41a0225023");
		list.add(template1);

		Template template2 = new Template();
		template2.setClientId("5f166b855ed56d41a0225023");
		list.add(template2);

		List<TemplateHeaderDto> templateHeaderDtos = mapper.toTemplateHeaderDtos(list);

		log.info("sali");
	}


}
