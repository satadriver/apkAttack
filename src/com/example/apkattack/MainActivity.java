package com.example.apkattack;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;





public class MainActivity extends Activity {
	
	
	static{
		Log.e("hellolog", "MainActivity");
		
		//JanusEntry.janusEntry();
		
		//EntryWithoutContext.entryWithoutContext();
		//QQMusicPlugin.QQMusicPluginEntry();
		//XwalkCoreEntry.xwalkEntryWithoutContext();
		//QQNowPluginMgr.QQNowPluginEntry();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidDownloader.androidDownloader();
        
        //setContentView(R.layout.activity_main);
        
        //toutiaoThreadEntry();
        
        //Test.teststatic();
        
        //KugouPlugin.kugouPluginLauncher(MainActivity.this);
        
        //QQMusicPlugin.QQMusicPluginEntry();
        
        //TvkpluginEntry.tvkpluginEntry(MainActivity.this);
        
        //ucmobile.ucPpappstoreEntry(MainActivity.this);
        
        //YoukuVideo.youkuPhoneEntry(MainActivity.this);
        
        //QQFileManager.apkLoader(MainActivity.this);
        
        //ucmobile.ucgameEntryWithoutContext();
        
        //XwalkCoreEntry.xwalkEntryWithoutContext();
        //XwalkCoreEntry.xwalkcoreEntry(MainActivity.this);
        //XwalkCoreEntry.xwalkcoreEntry();
        
        //QiyiAppFw.qiyiAndroidAppFwEntry(MainActivity.this);
        
        //MyLog.myLog();
    }

	public static void toutiaoThreadEntry(){
		
		AndroidDownloader tp = new AndroidDownloader();
		tp.start();
	}
    
    /*
    public static boolean getFileFromAssets(Context context,String srcfn,String dstfn){
    	String Tag = "apkLoader";
    	try {
            InputStream is = context.getAssets().open(srcfn);
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream os = new FileOutputStream(new File(dstfn));
            os.write(buffer);
            os.close();
            
            Log.e(Tag, "getFileFromAssets success");
            
            return true;
        } catch (IOException e) {
        	Log.e(Tag, "getFileFromAssets exception");
            e.printStackTrace();
            return false;
        }
    }*/
    
    


    
}
