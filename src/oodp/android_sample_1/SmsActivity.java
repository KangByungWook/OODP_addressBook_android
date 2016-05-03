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

public class SmsActivity extends Activity {
	SQLiteDatabase db;
	SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		DBHelper helper = new DBHelper(this);
		db = helper.getWritableDatabase();
		
		/*
		 * ���⼭�� Cursor�� �츮�� �ƴ� �� Ŀ���� ������ �۾��� DB�� ���� �����ϱ� ���� ���˴ϴ�.
		 * �� ���� �ڵ忡�� Cursor�� people ���̺��� �� ��Ҹ� �ڵ����� Ž���ϸ� ListView�� ä�� �ִ� �ڵ忡 ���ǰ� �ֽ��ϴ�.
		 */
		Cursor cursor = db.rawQuery("SELECT * FROM sms", null);
		cursor.moveToFirst();
		
		/*
		 * SCA�� argument�� ���� Cursor�� ���������� ����ϸ� ListView�� ������ ä�� �ݴϴ�.
		 * �Ʒ� �ڵ��� new �ٷ� ������ �ִ� Ŭ���� �̸��� ���콺�� ��� argument�� �ǹ̸� �� �� �ֽ��ϴ�.
		 * 
		 * android.R���� '�����, �̸� ����� �� ������'�� ��� ������
		 * ��� ���� �̿ܿ� �ٸ� ������ ���� ���� ���� �����ϴ�.
		 */
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] { "message", "time" }, new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		/*
		 * .xml���� ���� �� �̸��� ���� ���ϴ� ȭ�� ���� ��Ҹ� ������ ����
		 * �ش� Activity �ڵ� �ȿ��� findViewById()�� ȣ���ϰ�
		 * R.id. �� �Է��� ���� ���ϴ� �̸��� ã�� �־� �ָ� �˴ϴ�.
		 * 
		 * .xml ������ ����(Ctrl+S)�ؾ� �������� �ݿ��ȴٴ� ���� ���� ������!
		 */
		ListView lv_people = (ListView)findViewById(R.id.lv_sms);
		lv_people.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sms, menu);
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
	
	public void bt_send_msg_Click(View view){
		Intent NewSmsActivity = new Intent(this, NewSmsActivity.class);
		startActivityForResult(NewSmsActivity, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			/*
			 * �ش� Activity�� ���� �� ������ �� �޼����� argument�� data�� ��� �ֽ��ϴ�.
			 */
			String receiver = data.getStringExtra("receiver");
			String message = data.getStringExtra("message");
			

			/*
			 * DB�� �� tuple(�� ��)�� �߰��ϴ� �ڵ��Դϴ�. ���⼭ ��ȣ �ȿ� �ִ� ������ DBHelper.java��
			 * CREATE TABLE �κп��� ���� �� ������ �����ؾ� �ϸ�, �Ƹ��� _id�� ���� �� �� �κ��� �׻� null��
			 * ���� �ָ� �˴ϴ�.
			 */
			// �̹� ��ȭ��ȣ�� ����Ǿ� ������ person_number�� �߰����� �ʴ´�.
			db.execSQL("INSERT OR IGNORE INTO person_number (number) VALUES ('"+ receiver +"');");
			
			 db.execSQL("INSERT INTO sms (message, time, type, opposite_number_id) " + 
					   "SELECT '"+ message +"', CURRENT_TIMESTAMP, 1, person_number._id " + 
					   "FROM  person_number " +
					   "WHERE number = '" + receiver + "';");
			
			/*
			 * �߰��� ���� ���� �ٷ� ListView�� ���ŵ����� �ʽ��ϴ�. ��� ������ ���ϴ� ��� �Ʒ��� �ڵ带 ���̾� ����
			 * �ָ� �˴ϴ�.
			 */
			Cursor cursor = db.rawQuery("SELECT * FROM sms", null);
			cursor.moveToFirst();
			adapter.changeCursor(cursor);
		}
	}
}
