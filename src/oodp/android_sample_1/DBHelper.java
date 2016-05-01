package oodp.android_sample_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Android app���� SQLite �����ͺ��̽��� �� �� ���� ����ϱ� ���� �����ϴ� ���� Ŭ�����Դϴ�.
 * �� Ŭ�������� ������ �� ���� onCreate()�� onUpgrade()�Դϴ�.
 * 
 * @author Racin
 *
 */
public class DBHelper extends SQLiteOpenHelper
{
	public DBHelper(Context context)
	{
		/*
		 * SQLite�� �ϳ��� ������ ����Ͽ� ������ �����մϴ�.
		 * ���� �̸��� �ٲٰ� ���� ��� �Ʒ��� ���ڿ��� �����ϼ���.
		 * ����, �������� �� ������ ���� �ǵ帱 ���� ���� ���� ���Դϴ�. 
		 */
		super(context, "DB.db", null, 1);
	}

	/**
	 * �� ó�� app�� �����Ű�� �� ó�� DB�� ���� �� �ڵ����� ȣ��Ǵ� �޼����Դϴ�.
	 * ���⼭�� �ַ� '�� ���̺� �����' �۾��� �����ϸ�
	 * �� �۾��� SQL�� CREATE TABLE�� ���� �̷�����ϴ�.
	 */
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		/*
		 * �Ʒ� ������ ������ �ǹ��ϴ����� �����ϱ� �������
		 * �������� ���̺��� ��Ҹ� �Ը��� �°� �ٷ�� ��(�� �ʵ� �߰� ��)�� ���� ���Դϴ�.
		 * 
		 * '�ش� ��ȭ��ȣ�� ����� ����� �̸� ��������'�� ���� �����
		 * �ϴ� TP#3 ������ �κ����� �̷�� �ӽô�.
		 * 
		 * �׸���, _id�� ����� �ϴ� �ּҷ� ���α׷������� �׸� �Ű澲�� �ʾƵ� �˴ϴ�.
		 */
		db.execSQL(
				"CREATE TABLE person ( " +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, " +
				"email TEXT );");

		db.execSQL(
				"CREATE TABLE person_group ( " +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT );");
		
		db.execSQL(
				"CREATE TABLE person_number ( " +
				"_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				"name TEXT, " +
				"person_id INTEGER, " +
				"group_id INTEGER," +
				"FOREIGN KEY(person_id) REFERENCES person(_id),"+
				"FOREIGN KEY(group_id) REFERENCES person_group(_id)"+
				" );");

		
	}

	/**
	 * �׽�Ʈ ���� ������ DB�� ������ �ٲٱ� ���� ���� ȣ���� �޼����Դϴ�.
	 * ���� onCreate()���� �� ���̺��� ������ų� ���̺��� �̸��� �ٲ� ���
	 * �� �޼����� ���뵵 �׿� �°� ������ �ּ���.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// ���⼭�� '���׷��̵�'�� Ī�ϰ� �׳� '�� ����� �ٽ� �����'�� �����ϰ� �ֽ��ϴ�.
		db.execSQL("DROP TABLE IF EXISTS people");
		db.execSQL("DROP TABLE IF EXISTS sms");
		db.execSQL("DROP TABLE IF EXISTS callhistory");
		db.execSQL("DROP TABLE IF EXISTS person_number");
		db.execSQL("DROP TABLE IF EXISTS person");
		db.execSQL("DROP TABLE IF EXISTS person_group");
		onCreate(db);
	}

}
