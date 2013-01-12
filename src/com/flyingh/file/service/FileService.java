package com.flyingh.file.service;

import java.io.IOException;

public interface FileService {

	void save(String fileName, String content) throws IOException;

	String read(String fileName) throws IOException;
}
