package com.flyingh.file.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.flyingh.file.service.FileService;

import android.content.Context;

public class FileServiceImpl implements FileService {
	private Context context;

	public FileServiceImpl(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void save(String fileName, String content) throws IOException {
		FileOutputStream os = context.openFileOutput(fileName,
				Context.MODE_PRIVATE);
		os.write(content.getBytes());
		os.close();
	}

	@Override
	public String read(String fileName) throws IOException {
		FileInputStream is = context.openFileInput(fileName);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int len = -1;
		byte[] buf = new byte[1024];
		while ((len = is.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		return new String(os.toByteArray());
	}

}
