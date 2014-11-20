package com.example.httptest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button requestButton;
	private HttpResponse httpResponse = null;
	private HttpEntity httpEntity = null;
	private TextView tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		requestButton = (Button) findViewById(R.id.Mbtn);
		tv = (TextView) findViewById(R.id.tv);
		requestButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(runnable).start();

			}
		});
	}

	Runnable runnable = new Runnable() {
		public String inputStream2String(InputStream in) throws IOException {
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			return out.toString();
		}

		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			InputStream inputStream_1 = GetXMLFromSDcard
					.getInputStreamFromSDcard("mission_list.xml");
			String string = "";
			try {
				string = inputStream2String(inputStream_1);
			String string_1 = URLEncoder.encode(string);
			// string转xml
			FileOutputStream os;
//				File xmldir = new File(Environment
//						.getExternalStorageDirectory().toString() ,"aaa");
//				Log.e("path", Environment.getExternalStorageDirectory()
//						.toString() + "/IIS2014/");
//				xmldir.mkdirs();
				String filename = "xml.xml";
				File xml = new File(Environment
						.getExternalStorageDirectory().toString() + "/IIS2014/", filename);
//				xml.createNewFile();
//				xml.canWrite();
				Log.e("creatnewfile", ""+xml.createNewFile());
				Log.e("xml.canWrite()", ""+xml.canWrite());
				os = new FileOutputStream(xml,false);
				os.write(string.getBytes("UTF8"));
				os.flush();
				os.close();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			// 生成一个请求对象
//			Log.e("string", string_1);
			HttpGet httpGet = new HttpGet(
					"http://192.168.1.2:8081/Program/umd.asp?mode=1&data=aaa");
			HttpGet httpGet_login = new HttpGet(
					"http://192.168.1.2:8081/Program/clm.asp?&data=aaa");
			// 生成一个Http客户端对象
			HttpClient httpClient = new DefaultHttpClient();
			// 使用Http客户端发送请求对象
			InputStream inputStream = null;
			String result = "";
			String line = "";
			try {
				httpResponse = httpClient.execute(httpGet);
				httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "GB2312"));
				// XmlPullParser xrp_mission_equip;
				// // 定义工厂 XmlPullParserFactory
				// XmlPullParserFactory factory =
				// XmlPullParserFactory.newInstance();
				// // 定义解析器 XmlPullParser
				// xrp_mission_equip = factory.newPullParser();
				// // 获取xml输入数据
				// xrp_mission_equip.setInput(inputStream, "GB2312");
				// while
				// (xrp_mission_equip.getEventType()!=XmlResourceParser.END_DOCUMENT)
				// {
				// String tagName = xrp_mission_equip.getName();
				// if(xrp_mission_equip.getEventType()==XmlResourceParser.START_TAG){
				// if(tagName.equals("MName")){
				// result = xrp_mission_equip.nextText();
				// }
				// }
				// xrp_mission_equip.next();
				// }
				while ((line = reader.readLine()) != null) {
					result = result + line;
				}
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("value", result);
//			Log.e("result", result);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			System.out.println(val);
			tv.setText(val);
		}

	};
}