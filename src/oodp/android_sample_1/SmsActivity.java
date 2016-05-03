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
		 * 여기서의 Cursor는 우리가 아는 그 커서와 동일한 작업을 DB에 대해 수행하기 위해 사용됩니다.
		 * 이 예제 코드에서 Cursor는 people 테이블의 각 요소를 자동으로 탐색하며 ListView를 채워 주는 코드에 사용되고 있습니다.
		 */
		Cursor cursor = db.rawQuery("SELECT * FROM sms", null);
		cursor.moveToFirst();
		
		/*
		 * SCA는 argument로 받은 Cursor를 내부적으로 사용하며 ListView에 내용을 채워 줍니다.
		 * 아래 코드의 new 바로 다음에 있는 클래스 이름에 마우스를 대면 argument의 의미를 알 수 있습니다.
		 * 
		 * android.R에는 '내장된, 미리 만들어 둔 정보들'이 들어 있으며
		 * 사실 여기 이외에 다른 곳에서 쓰일 일은 거의 없습니다.
		 */
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[] { "message", "time" }, new int[] { android.R.id.text1, android.R.id.text2 }, 0);

		/*
		 * .xml에서 정해 둔 이름을 통해 원하는 화면 구성 요소를 가져올 때는
		 * 해당 Activity 코드 안에서 findViewById()를 호출하고
		 * R.id. 을 입력한 다음 원하는 이름을 찾아 넣어 주면 됩니다.
		 * 
		 * .xml 파일을 저장(Ctrl+S)해야 변경점이 반영된다는 것을 잊지 마세요!
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
			 * 해당 Activity가 보내 준 정보는 이 메서드의 argument인 data에 들어 있습니다.
			 */
			String receiver = data.getStringExtra("receiver");
			String message = data.getStringExtra("message");
			

			/*
			 * DB에 새 tuple(한 줄)을 추가하는 코드입니다. 여기서 괄호 안에 있는 순서는 DBHelper.java의
			 * CREATE TABLE 부분에서 적어 둔 순서와 동일해야 하며, 아마도 _id가 있을 맨 앞 부분은 항상 null을
			 * 적어 주면 됩니다.
			 */
			// 이미 전화번호가 저장되어 있으면 person_number에 추가하지 않는다.
			db.execSQL("INSERT OR IGNORE INTO person_number (number) VALUES ('"+ receiver +"');");
			
			 db.execSQL("INSERT INTO sms (message, time, type, opposite_number_id) " + 
					   "SELECT '"+ message +"', CURRENT_TIMESTAMP, 1, person_number._id " + 
					   "FROM  person_number " +
					   "WHERE number = '" + receiver + "';");
			
			/*
			 * 추가가 끝난 다음 바로 ListView가 갱신되지는 않습니다. 즉시 갱신을 원하는 경우 아래의 코드를 연이어 적어
			 * 주면 됩니다.
			 */
			Cursor cursor = db.rawQuery("SELECT * FROM sms", null);
			cursor.moveToFirst();
			adapter.changeCursor(cursor);
		}
	}
}
