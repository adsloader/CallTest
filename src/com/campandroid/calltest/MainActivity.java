package com.campandroid.calltest;

import com.campandroid.My.MyDataManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	// 위젯을 선언한다.
	Button btnSet     = null;
	EditText etPassWd = null;
	EditText etPhone = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = getIntent();
		
		// check if this intent is started via custom scheme link
		String sPassWord = "";
		if (Intent.ACTION_VIEW.equals(intent.getAction())) {
		  Uri uri = intent.getData();
		  sPassWord = uri.getQueryParameter("pw"); 
		  
		  Toast.makeText(getApplicationContext(), "pw=" + sPassWord, Toast.LENGTH_LONG).show();
		}
		
		// 비밀번호가 맞지않으면...
		String sSetPassWd = MyDataManager.GetPassWd(getApplicationContext());
		//if(!sPassWord.equals(sSetPassWd)) 
		//	finish();
		
		// 비밀번호설정
		etPassWd = (EditText)findViewById(R.id.edtPasswd);
		etPassWd.setText(sSetPassWd);
		
		// 번호설정
		etPhone = (EditText)findViewById(R.id.edtPhone);
		
		try {
			DisplayData d = MyDataManager.LoadData(getApplicationContext());
			etPhone.setText(d.number);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 버튼설정
		btnSet = (Button)findViewById(R.id.btnSetting);
		btnSet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// Hide 번호를 설정한다.
				SetHiddenNumber();
			}}
		);
	}
	
	// 전화번호와 비밀번호 정보를 저장한다.
	void SetHiddenNumber(){
		DisplayData d = new DisplayData();
		d.chkDate     = System.currentTimeMillis();
		d.number      = etPhone.getText().toString();
		
		try {
			MyDataManager.SaveData(getApplicationContext(), d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 비밀번호저장
		String sPassWd = etPassWd.getText().toString();
		MyDataManager.SavePassWd(getApplicationContext(), sPassWd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
