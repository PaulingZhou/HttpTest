package com.example.httptest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;
import android.util.Log;

public class GetXMLFromSDcard {
	public static InputStream getInputStreamFromSDcard(String fileName) {
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/IIS2014/";			
		Log.v("", "path : " + path);
		File xmlFlie = new File(path + fileName);
		try {
			InputStream inputStream = new FileInputStream(xmlFlie);
			return inputStream;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static OutputStream getOutputStreamFromSDcard(String fileName) {
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/IIS2014/";			
		Log.v("", "path : " + path);
		File xmlFlie = new File(path + fileName);
		try {
			//false表示覆盖，true表示追加
			OutputStream outputStream = new FileOutputStream(xmlFlie, false);
			return outputStream;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
