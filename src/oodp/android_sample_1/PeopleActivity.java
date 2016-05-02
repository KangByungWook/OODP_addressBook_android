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


public class PeopleActivity extends Activity
{
	SQLiteDatabase db;
	SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people);

		DBHelper helper = new DBHelper(this);
		db = helper.getWritableDatabase();
		
		/*
		 * ���⼭�� Cursor�� �츮�� �ƴ� �� Ŀ���� ������ �۾��� DB�� ���� �����ϱ� ���� ���˴ϴ�.
		 * �� ���� �ڵ忡�� Cursor�� people ���̺��� �� ��Ҹ� �ڵ����� Ž���ϸ� ListView�� ä�� �ִ� �ڵ忡 ���ǰ� �ֽ��ϴ�.
		 */
		Cursor cursor = db.rawQuery("SELECT * FROM person", null);
		cursor.moveToFirst();
		
		/*
		 * SCA�� argument�� ���� Cursor�� ���������� ����ϸ� ListView�� ������ ä�� �ݴϴ�.
		 * �Ʒ� �ڵ��� new �ٷ� ������ �ִ� Ŭ���� �̸��� ���콺�� ��� argument�� �ǹ̸� �� �� �ֽ��ϴ�.
		 * 
		 * android.R���� '�����, �̸� ����� �� ������'�� ��� ������
		 * ��� ���� �̿ܿ� �ٸ� ������ ���� ���� ���� �����ϴ�.
		 */
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] { "name", "email" }, new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		/*
		 * .xml���� ���� �� �̸��� ���� ���ϴ� ȭ�� ���� ��Ҹ� ������ ����
		 * �ش� Activity �ڵ� �ȿ��� findViewById()�� ȣ���ϰ�
		 * R.id. �� �Է��� ���� ���ϴ� �̸��� ã�� �־� �ָ� �˴ϴ�.
		 * 
		 * .xml ������ ����(Ctrl+S)�ؾ� �������� �ݿ��ȴٴ� ���� ���� ������!
		 */
		ListView lv_people = (ListView)findViewById(R.id.lv_people);
		lv_people.setAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.people, menu);
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
	
	public void bt_new_Click(View view)
	{
		/*
		 * �� Activity���� � �������� ��� �� Activity�� ���� �� �����ϰ�
		 * �ش� Activity�� ���� �� ���� �� �������� ó���ϱ� ���� �ڵ��Դϴ�.
		 * 
		 * ����:
		 * ���� �ִ� startActivityForResult()�� '�� Activity�� ����'���� ����ϸ�,
		 * �ش� Activity�� ���� �� �������� ó���ϱ� ���� �ڵ��
		 * �Ʒ��� �ִ� onActivityResult()�� ���� �־�� �մϴ�.
		 * 
		 * onActivityResult()��
		 * �ش� Activity�� ������ ���� ���� �� Activity�� ���ƿ� �� ȣ��˴ϴ�.
		 */
		Intent intent = new Intent(this, NewPeopleActivity.class);
		intent.putExtra("name", "�� �̸�");
		intent.putExtra("number", "�� ��ȣ");
		intent.putExtra("email", "�� �̸���");
		intent.putExtra("group", "�׷��");
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ( resultCode == RESULT_OK )
		{
			/*
			 * �ش� Activity�� ���� �� ������ �� �޼����� argument�� data�� ��� �ֽ��ϴ�.
			 */
			String newName = data.getStringExtra("name");
			String newNumber = data.getStringExtra("number");
			String newEmail = data.getStringExtra("email");
			String newGroup = data.getStringExtra("group");
			
			/*
			 * DB�� �� tuple(�� ��)�� �߰��ϴ� �ڵ��Դϴ�.
			 * ���⼭ ��ȣ �ȿ� �ִ� ������ DBHelper.java�� CREATE TABLE �κп��� ���� �� ������ �����ؾ� �ϸ�,
			 * �Ƹ��� _id�� ���� �� �� �κ��� �׻� null�� ���� �ָ� �˴ϴ�. 
			 */
			db.execSQL("INSERT INTO person VALUES (null, '" + newName + "', '" + newEmail + "');");
			// �׷���� �ߺ��Ǹ� ����
			db.execSQL("INSERT OR IGNORE INTO person_group VALUES (null, '" + newGroup + "');");
			db.execSQL("INSERT INTO person_number (number, person_id, group_id) " + 
						"SELECT      '"+ newNumber +"', person._id, person_group._id " + 
						"FROM        person " + 
						"INNER JOIN  person_group " + 
						"ON          person.name = '" + newName + "' " + 
						"AND       	 person_group.name = '" + newGroup + "';");
			
			
			/*
			 * �߰��� ���� ���� �ٷ� ListView�� ���ŵ����� �ʽ��ϴ�.
			 * ��� ������ ���ϴ� ��� �Ʒ��� �ڵ带 ���̾� ���� �ָ� �˴ϴ�.
			 */
			Cursor cursor = db.rawQuery("SELECT * FROM person", null);
			cursor.moveToFirst();
			adapter.changeCursor(cursor);			
		}
	}
}
