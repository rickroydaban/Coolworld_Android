package com.example.coolworld.gateways;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
/* This class handles the data table of the application database
 * 
 */

public class DataGateway extends SQLiteOpenHelper {
	 private static final String TABLE_DATA = "data";

	 //if other columns will be added, you can define it here
	 private static final String KEY_NAME = "name";	 
	 private static final String KEY_VALUE = "value";
	
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CoolworldDB";
    private SQLiteDatabase db;
    
    public DataGateway(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); 
        db = getWritableDatabase();
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) { 
//    		this.db = db;
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA+" ( " +
            											 KEY_NAME + " TEXT unique, " +
										               KEY_VALUE + " TEXT )";
 
        db.execSQL(CREATE_DATA_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DATA);
 
        this.onCreate(db);
    }
    
    public void insertUniqueData(String name, String value){
    	System.out.println("TEST: now inserting "+name+": "+value+" to the data table");
    	if(getValue(name)==null)
    		db.execSQL("INSERT INTO "+TABLE_DATA+"("+KEY_NAME+", "+KEY_VALUE+") VALUES('"+name+"', '"+value+"')");
    	//must use execSQL for inserting or updating values to the database
    }
    
    public String getValue(String name){ 
      Cursor cursor = db.rawQuery("SELECT "+KEY_VALUE+" FROM "+TABLE_DATA+" WHERE "+KEY_NAME+"='"+name+"'", null);   

      System.out.println("TEST: getting value of "+name+" with "+cursor.getCount()+"rows and Column names: ");
      for(int i=0; i<cursor.getColumnNames().length;i++)
      	System.out.println("index "+i+": "+cursor.getColumnNames()[i].toString());
      
      if(cursor.moveToLast()){
      	System.out.println("value: "+cursor.getString(0));
      	return cursor.getString(0);
      }
   
      return null;
    }    
    
    public void updateValue(String name, String value){      
      db.rawQuery("UPDATE "+TABLE_DATA+" SET "+KEY_VALUE+" = '"+value+"' WHERE "+KEY_NAME+" = '"+name+"'", null).moveToFirst();
    	//must use execSQL for inserting or updating values to the database
    }
        
    public List<HashMap<String, String>> getAllData(){
      Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_DATA, null);   
      List<HashMap<String, String>> rows = null;
      HashMap<String, String> row;

      System.out.println("TEST: Retrieving all data with "+cursor.getCount()+" elements");
      
      if(cursor.moveToFirst()){
      	rows = new ArrayList<HashMap<String, String>>();
      	
      	do{
      		row = new HashMap<String, String>();
      		System.out.println("TEST: Traversing list. Got "+cursor.getString(0)+": "+cursor.getString(1));
      		row.put("name", cursor.getString(0));
      		row.put("value", cursor.getString(1));
      		
      		rows.add(row);
      	}while(cursor.moveToNext());
      }
    	
      return rows;
    }
    
    public void closeDB(){
    	db.close();
    }
// 
//    public List<Book> getAllBooks() {
//      List<Book> books = new LinkedList<Book>();
//
//      // 1. build the query
//      String query = "SELECT  * FROM " + TABLE_BOOKS;
//
//      // 2. get reference to writable DB
//      SQLiteDatabase db = this.getWritableDatabase();
//      Cursor cursor = db.rawQuery(query, null);
//
//      // 3. go over each row, build book and add it to list
//      Book book = null;
//      if (cursor.moveToFirst()) {
//          do {
//              book = new Book();
//              book.setId(Integer.parseInt(cursor.getString(0)));
//              book.setTitle(cursor.getString(1));
//              book.setAuthor(cursor.getString(2));
//
//              // Add book to books
//              books.add(book);
//          } while (cursor.moveToNext());
//      }
//
//      Log.d("ANDROID: getAllBooks()", books.toString());
//
//      // return books
//      return books;
//    }
//    
//    public int updateBook(Book book) {
//    	 
//      // 1. get reference to writable DB
//      SQLiteDatabase db = this.getWritableDatabase();
//   
//      // 2. create ContentValues to add key "column"/value
//      ContentValues values = new ContentValues();
//      values.put("title", book.getTitle()); // get title
//      values.put("author", book.getAuthor()); // get author
//   
//      // 3. updating row
//      int i = db.update(TABLE_BOOKS, //table
//              values, // column/value
//              KEY_COOLSITE+" = ?", // selections
//              new String[] { String.valueOf(book.getId()) }); //selection args
//   
//      // 4. close
//      db.close();
//   
//      return i;
//   
//    }
//    
//    public void deleteBook(Book book) {
//    	 
//      // 1. get reference to writable DB
//      SQLiteDatabase db = this.getWritableDatabase();
//
//      // 2. delete
//      db.delete(TABLE_BOOKS, //table name
//              KEY_COOLSITE+" = ?",  // selections
//              new String[] { String.valueOf(book.getId()) }); //selections args
//
//      // 3. close
//      db.close();
//
//      //log
//      Log.d("ANDROID: deleteBook", book.toString());
//
//    }    
}