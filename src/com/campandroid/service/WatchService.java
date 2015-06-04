package com.campandroid.service;

import com.campandroid.My.MyDataManager;
import com.campandroid.calltest.DisplayData;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class WatchService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
        super.onCreate();
    }
    
	MyContentObserver oObserver  = null;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        if(oObserver == null){
        	oObserver = new MyContentObserver( new Handler() );
        	getContentResolver()
    	    .registerContentObserver(
    	            android.provider.CallLog.Calls.CONTENT_URI, true, oObserver
    	            ); 
    			
        }
		
		return super.onStartCommand(intent, flags, startId);
    }
	
    @Override
    public void onDestroy() { 
        super.onDestroy();
        getContentResolver().unregisterContentObserver(oObserver);
    }
    
    public String GetRecentID(Context context){
    	final String[] projection = null;
    	final String selection = null;
    	final String[] selectionArgs = null;
    	final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";Cursor cursor = null;
    	try{
    	    cursor = context.getContentResolver().query(
    	            Uri.parse("content://call_log/calls"),
    	            projection,
    	            selection,
    	            selectionArgs,
    	            sortOrder);
    	    if( cursor.moveToFirst()){
    	    	String callLogID = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls._ID));
        	    String callNumber = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
        	    String callDate = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE));
        	    String callType = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE));
        	    String isCallNew = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NEW));
        	    
        	    if(isRejectCalllogNumber(context, callNumber, new Long(callDate) )){
        	    	Log.d("CALLTEST", callNumber + "(" + callLogID + ")"  + " deleted");
            	    return callLogID;
            	}	
        	     	
    	    }
    	    
    	}catch(Exception ex){}
    	finally{
    	    if(cursor !=null) cursor.close();
    	}
    	
    	return null;

    }
    
    public boolean isRejectLog(Context context, String phoneNumber, long regDate) throws Exception{
	    
	    DisplayData d = MyDataManager.LoadData(getApplicationContext());        
		if (d.number.equals(phoneNumber) && d.chkDate < regDate) {
			return true;
		}
	    
		return false;
	}
	
    private boolean isRejectCalllogNumber(Context context, String callNumber, long regDate){
		boolean b = false;
		try {
			b = isRejectLog(context, callNumber, regDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}


    
    class MyContentObserver extends ContentObserver {
	    public MyContentObserver(Handler h) {
	        super(h);
	    }

	    @Override
	    public boolean deliverSelfNotifications() {
	        return true;
	    }

	    boolean bDeleteResult =false;
	    @Override
	    public void onChange(boolean selfChange) {
	        super.onChange(selfChange);

	        Log.d("CALLTEST", " Changed " + selfChange);
	        
	        if(bDeleteResult){
	        	bDeleteResult = false;
	        	return;
	        }
	        
	        String sID = GetRecentID( getApplicationContext()  );
	    	if(sID != null){
	    		bDeleteResult = true;
	    		getApplicationContext().getContentResolver().delete(android.provider.CallLog.Calls.CONTENT_URI,"_ID = "+ sID, null);
	    	}
	    }
	}

        
 }
