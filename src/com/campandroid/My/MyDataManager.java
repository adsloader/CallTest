package com.campandroid.My;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.campandroid.calltest.DisplayData;

import android.content.Context;
import android.content.SharedPreferences;

public class MyDataManager {
	
	public static final String PREFER             = "PREFER";
	
	// ��й�ȣ�� �����Ѵ�.
	static public void SavePassWd(Context ctx, String sPW){
		SharedPreferences prefs = ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PREFER, sPW);
		editor.commit();		
	}
	
	// ��й�ȣ�� �����´�.
	static public String GetPassWd(Context ctx){
		SharedPreferences prefs = ctx.getSharedPreferences(PREFER, Context.MODE_PRIVATE);
		 return prefs.getString(PREFER, "1234");
	}
	
	// �ε��Ѵ�.
    static public DisplayData LoadData(Context context) throws Exception{
		
    	FileInputStream fis = context.openFileInput("object.ser");
	    ObjectInputStream objstream2 = new ObjectInputStream(fis);
	    Object object = objstream2.readObject();
	    objstream2.close();
	    
	    return (DisplayData)object;
	}
    
    // �����Ѵ�.
    static public void SaveData(Context context, Object Items) throws Exception{
		FileOutputStream fos = context.openFileOutput("object.ser", Context.MODE_PRIVATE);
	    ObjectOutputStream objstream = new ObjectOutputStream(fos);
	    objstream.writeObject(Items);
	    objstream.close();	
	}
 	
}
