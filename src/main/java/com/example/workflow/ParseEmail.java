package com.example.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.mail.RFC822Parser;
import org.apache.tika.parser.ParseContext;

import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;
//import org.xml.sax.SAXException;

@Service("parseEmail")
public class ParseEmail implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		
		File file = new File("C:\\prj_jiwoo\\java\\my-project\\my-project\\src\\main\\resources\\static\\files\\emlfile.eml");
		FileInputStream inputStream = new FileInputStream(file);
		
		Parser parser = new RFC822Parser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		
		parser.parse(inputStream, handler, metadata, pcontext);
		
		System.out.println(handler.toString());
		
		String[] metaNames = metadata.names();
		for(String name : metaNames) {
			String value = metadata.get(name);
			System.out.println(name + ": " + value);
			execution.setVariable(name, value);
		}
		
		System.out.println("pcontext: " + pcontext.toString());
		execution.setVariable("context", handler.toString());
	
		inputStream.close();
	}
}
