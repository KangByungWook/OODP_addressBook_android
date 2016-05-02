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
		 * 여기서의 Cursor는 우리가 아는 그 커서와 동일한 작업을 DB에 대해 수행하기 위해 사용됩니다.
		 * 이 예제 코드에서 Cursor는 people 테이블의 각 요소를 자동으로 탐색하며 ListView를 채워 주는 코드에 사용되고 있습니다.
		 */
		Cursor cursor = db.rawQuery("SELECT * FROM person", null);
		cursor.moveToFirst();
		
		/*
		 * SCA는 argument로 받은 Cursor를 내부적으로 사용하며 ListView에 내용을 채워 줍니다.
		 * 아래 코드의 new 바로 다음에 있는 클래스 이름에 마우스를 대면 argument의 의미를 알 수 있습니다.
		 * 
		 * android.R에는 '내장된, 미리 만들어 둔 정보들'이 들어 있으며
		 * 사실 여기 이외에 다른 곳에서 쓰일 일은 거의 없습니다.
		 */
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] { "name", "email" }, new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		/*
		 * .xml에서 정해 둔 이름을 통해 원하는 화면 구성 요소를 가져올 때는
		 * 해당 Activity 코드 안에서 findViewById()를 호출하고
		 * R.id. 을 입력한 다음 원하는 이름을 찾아 넣어 주면 됩니다.
		 * 
		 * .xml 파일을 저장(Ctrl+S)해야 변경점이 반영된다는 것을 잊지 마세요!
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
		 * 이 Activity에서 어떤 정보들을 담아 새 Activity로 전달 및 전이하고
		 * 해당 Activity가 끝날 때 보내 준 정보들을 처리하기 위한 코드입니다.
		 * 
		 * 주의:
		 * 여기 있는 startActivityForResult()는 '새 Activity로 전이'만을 담당하며,
		 * 해당 Activity가 보내 준 정보들을 처리하기 위한 코드는
		 * 아래에 있는 onActivityResult()에 적어 주어야 합니다.
		 * 
		 * onActivityResult()는
		 * 해당 Activity의 실행이 끝난 다음 이 Activity로 돌아올 때 호출됩니다.
		 */
		Intent intent = new Intent(this, NewPeopleActivity.class);
		intent.putExtra("name", "새 이름");
		intent.putExtra("number", "새 번호");
		intent.putExtra("email", "새 이메일");
		intent.putExtra("group", "그룹명");
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if ( resultCode == RESULT_OK )
		{
			/*
			 * 해당 Activity가 보내 준 정보는 이 메서드의 argument인 data에 들어 있습니다.
			 */
			String newName = data.getStringExtra("name");
			String newNumber = data.getStringExtra("number");
			String newEmail = data.getStringExtra("email");
			String newGroup = data.getStringExtra("group");
			
			/*
			 * DB에 새 tuple(한 줄)을 추가하는 코드입니다.
			 * 여기서 괄호 안에 있는 순서는 DBHelper.java의 CREATE TABLE 부분에서 적어 둔 순서와 동일해야 하며,
			 * 아마도 _id가 있을 맨 앞 부분은 항상 null을 적어 주면 됩니다. 
			 */
			db.execSQL("INSERT INTO person VALUES (null, '" + newName + "', '" + newEmail + "');");
			// 그룹명이 중복되면 무시
			db.execSQL("INSERT OR IGNORE INTO person_group VALUES (null, '" + newGroup + "');");
			db.execSQL("INSERT INTO person_number (number, person_id, group_id) " + 
						"SELECT      '"+ newNumber +"', person._id, person_group._id " + 
						"FROM        person " + 
						"INNER JOIN  person_group " + 
						"ON          person.name = '" + newName + "' " + 
						"AND       	 person_group.name = '" + newGroup + "';");
			
			
			/*
			 * 추가가 끝난 다음 바로 ListView가 갱신되지는 않습니다.
			 * 즉시 갱신을 원하는 경우 아래의 코드를 연이어 적어 주면 됩니다.
			 */
			Cursor cursor = db.rawQuery("SELECT * FROM person", null);
			cursor.moveToFirst();
			adapter.changeCursor(cursor);			
		}
	}
}
