package oodp.android_sample_1;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CallActivity extends Activity {
	SQLiteDatabase db;
	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		
		DBHelper helper = new DBHelper(this);
		db = helper.getWritableDatabase();
		
		/*
		 * ���⼭�� Cursor�� �츮�� �ƴ� �� Ŀ���� ������ �۾��� DB�� ���� �����ϱ� ���� ���˴ϴ�. �� ���� �ڵ忡��
		 * Cursor�� people ���̺��� �� ��Ҹ� �ڵ����� Ž���ϸ� ListView�� ä�� �ִ� �ڵ忡 ���ǰ� �ֽ��ϴ�.
		 */
		
		Cursor cursor = db.rawQuery("SELECT * FROM call", null);
		cursor.moveToFirst();

		/*
		 * SCA�� argument�� ���� Cursor�� ���������� ����ϸ� ListView�� ������ ä�� �ݴϴ�. �Ʒ� �ڵ��� new
		 * �ٷ� ������ �ִ� Ŭ���� �̸��� ���콺�� ��� argument�� �ǹ̸� �� �� �ֽ��ϴ�.
		 * 
		 * android.R���� '�����, �̸� ����� �� ������'�� ��� ������ ��� ���� �̿ܿ� �ٸ� ������ ���� ���� ����
		 * �����ϴ�.
		 */
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
				new String[] { "start_time", "end_time" }, new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		/*
		 * .xml���� ���� �� �̸��� ���� ���ϴ� ȭ�� ���� ��Ҹ� ������ ���� �ش� Activity �ڵ� �ȿ���
		 * findViewById()�� ȣ���ϰ� R.id. �� �Է��� ���� ���ϴ� �̸��� ã�� �־� �ָ� �˴ϴ�.
		 * 
		 * .xml ������ ����(Ctrl+S)�ؾ� �������� �ݿ��ȴٴ� ���� ���� ������!
		 */
		ListView lv_people = (ListView) findViewById(R.id.lv_call);
		lv_people.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call, menu);
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
	
	public void bt_do_call_Click(View view){
		Intent NewCallActivity = new Intent(this, NewCallActivity.class);
		startActivityForResult(NewCallActivity, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String number = data.getStringExtra("number");
			String start_time = data.getStringExtra("start_time");
			String end_time = data.getStringExtra("end_time");
			
			//TODO sql�� �����ϱ�
			
			Cursor cursor = db.rawQuery("SELECT * FROM call", null);
			cursor.moveToFirst();
			adapter.changeCursor(cursor);
		}
	}
	
}
