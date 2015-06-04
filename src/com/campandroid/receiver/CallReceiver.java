package com.campandroid.receiver;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.campandroid.service.WatchService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver {
	
	@Override 
	public void onReceive(Context context, Intent intent) { 
		
		StartCallogObserver(context);
	}
	
	// 옵서버를 실행하는 서비스 실행!
	private void  StartCallogObserver(Context context){
		Intent itt = new Intent(context, WatchService.class);
		context.startService(itt);
	}
	
}	