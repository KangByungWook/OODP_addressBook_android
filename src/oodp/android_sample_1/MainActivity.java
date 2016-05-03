package oodp.android_sample_1;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if ( id == R.id.action_settings )
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * �ڵ����� ������ �ڵ��� ��
	 * ---------------------------------------------------------------------------------
	 */
	
	public void bt_people_Click(View view)
	{
		/*
		 * �� Activity���� ���ٸ� ó�� ���� �ٸ� Activity�� ������ �� ����� �� �ִ� �ڵ��Դϴ�.
		 */
		Intent peopleActivity = new Intent(this, PeopleActivity.class);
		startActivity(peopleActivity);
	}
	
	public void bt_sms_Click(View view){
		Intent SmsActivity = new Intent(this, SmsActivity.class);
		startActivity(SmsActivity);
	}
	
	public void bt_call_Click(View view){
		Intent callActivity = new Intent(this, CallActivity.class);
		startActivity(callActivity);
	}
	
	
	
	public void bt_reset_Click(View view)
	{
		/*
		 * ������ ��ϵ� DB�� ������ �ʱ�ȭ�ϰ� ���� �� ����� �� �ִ� �ڵ��Դϴ�.
		 */
		DBHelper helper = new DBHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.onUpgrade(db, 1, 1);
	}
}
