package com.flyingh.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.flyingh.file.service.FileService;
import com.flyingh.file.service.impl.FileServiceImpl;

public class MainActivity extends Activity {
	private EditText fileNameText;
	private EditText contentText;
	private FileService fileService;
	private List<Error> errors;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fileNameText = (EditText) findViewById(R.id.fileName);
		contentText = (EditText) findViewById(R.id.content);
		fileService = new FileServiceImpl(this);
		errors = new ArrayList<Error>();
	}

	public void save(View view) {
		try {
			String fileName = fileNameText.getText().toString();
			String content = contentText.getText().toString();
			validateFileName(fileName);
			validateContext(content);
			if (existErrors()) {
				outputErrors();
				clearErrors();
				return;
			}
			fileService.save(fileName, content);
			makeShortText(R.string.saveSuccess);
		} catch (IOException e) {
			makeShortText(R.string.saveFailure);
		}
	}

	private boolean existErrors() {
		return errors.size() > 0;
	}

	private void makeShortText(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	private void validateContext(String content) {
		if (isEmpty(content)) {
			errors.add(new Error("the context is empty"));
		}
	}

	private boolean isEmpty(String content) {
		return content == null || content.trim().length() == 0;
	}

	private void validateFileName(String fileName) {
		if (isEmpty(fileName)) {
			errors.add(new Error("the fileName is empty"));
		}
	}

	private void makeShortText(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}

	public void read(View view) {
		String fileName = fileNameText.getText().toString();
		validateFileName(fileName);
		if (existErrors()) {
			outputErrors();
			clearErrors();
			return;
		}
		try {
			String content = fileService.read(fileName);
			contentText.setText(content);
			makeShortText(R.string.readSuccess);
		} catch (IOException e) {
			contentText.setText(null);
			makeShortText(R.string.readFailure);
		}
	}

	private void clearErrors() {
		errors.clear();
	}

	private void outputErrors() {
		StringBuilder sb = new StringBuilder();
		for (Error error : errors) {
			sb.append(error.getMessage()).append("\r\n");
		}
		makeShortText(sb.delete(sb.length() - 2, sb.length()).toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
