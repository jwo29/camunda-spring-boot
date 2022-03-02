package com.example.workflow;

import java.io.File;
import java.io.IOException;

import org.apache.tika.Tika;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("detectFileType")
public class DetectFileType implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws IOException {
		// execution.getVariable("file"); // 파일 명 변수 값 가져오기
		File file = new File("C:\\prj_jiwoo\\java\\my-project\\my-project\\src\\main\\resources\\static\\files\\emlfile.eml");
		
		Tika tika = new Tika();
		String type = tika.detect(file);
		
		execution.setVariable("fileType", type);
	}
}
