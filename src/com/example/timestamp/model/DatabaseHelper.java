package com.example.timestamp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
 // Database Version
 
    // Database Name
    private static final String DATABASE_NAME = "TimeStamp";
 
    // Table Names
    private static final String TABLE_TIMEPOST = "TimePost";
    private static final String TABLE_PROJECTS = "Projects";
    private static final String TABLE_USERS = "Users";
    private static final String TABLE_PROJECT_USERS = "Project_Users";
    
    // Common column names
    private static final String KEY_PID = "pID";
    private static final String KEY_MAIL_ADRESS = "mailAdress";
 
    // TimePost Table - column names
    private static final String KEY_TID = "tID";
    private static final String KEY_START_TIME = "startTime";
    private static final String KEY_END_TIME = "endTime";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_IS_SIGNED = "isSigned";
    private static final String KEY_COMMENT_SHARED = "commentShared";
    // Projects table - column names
    private static final String KEY_PROJECT_NAME = "projectName";
    private static final String KEY_PROJECT_OWNER = "projectOwner";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_CUSTOMER = "customer";
    // USER Table - column names
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PASSWORD = "userPassword";
 
    
    // Table Create Statements
    
    // TimePost table create statement
    private static final String CREATE_TABLE_TIME_STAMP = "CREATE TABLE " + TABLE_TIMEPOST
            + "(" + KEY_TID + " INTEGER PRIMARY KEY," 
    		+ KEY_PID + " INTEGER(5) NOT NULL AUTO_INCREMENT,"
            + KEY_START_TIME + " DATETIME,"
    		+ KEY_END_TIME +" DATETIME,"
    		+ KEY_COMMENT +" VARCHAR(50)," 
    		+ KEY_IS_SIGNED +" TINYINT(1)," 
    		+ KEY_COMMENT_SHARED +" TINYINT(1),"
            +")";
 
 
   
    // Projects table create statement
    private static final String CREATE_TABLE_TABLE_PROJECTS = "CREATE TABLE " + TABLE_PROJECTS
            + "(" + KEY_PID + "INTEGER PRIMARY KEY INTEGER(5) AUTO_INCREMENT,"
            + KEY_PROJECT_NAME + " VARCHAR(50),"
            + KEY_PROJECT_OWNER + " VARCHAR(50),"
    		+ KEY_DESCRIPTION +" VARCHAR(50)," 
    		+ KEY_CUSTOMER +" VARCHAR(50)," 
    		+ KEY_COMMENT_SHARED +" TINYINT(1),"
            +")";
    
    
 // Users  table
    private static final String CREATE_TABLE_USERS= "CREATE TABLE "
            + TABLE_USERS + "(" + KEY_MAIL_ADRESS + " VARCHAR(50) PRIMARY KEY,"
    		+ KEY_USER_NAME + " VARCHAR(50)," 
            + KEY_USER_PASSWORD + " VARCHAR(50),"+ ")";
    
 // Users projects table
    private static final String CREATE_TABLE_PROJECT_USER = "CREATE TABLE "
            + TABLE_PROJECT_USERS + "(" + KEY_PID + " INTEGER(5) PRIMARY KEY,"
    		+ KEY_MAIL_ADRESS + " VARCHAR(50) PRIMARY KEY ,"
            + ")";
 
   
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	Log.d("DatabaseHelper", "Creating database..." );
        // creating required tables
        db.execSQL(CREATE_TABLE_TIME_STAMP);
        db.execSQL(CREATE_TABLE_TABLE_PROJECTS);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_PROJECT_USER);
        Log.d("DatabaseHelper","Done" );
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMEPOST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_USERS);
        
        //Log.d("query", selectQuery);
        // create new tables
        onCreate(db);
    }
    
 
    public void createTimePost(TimePost timePost) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_TID, timePost.id);
        values.put(KEY_PID, timePost.projectId);
        values.put(KEY_START_TIME, timePost.getStartTime());
        values.put(KEY_END_TIME, timePost.getEndTime());
        values.put(KEY_COMMENT, timePost.comment);
        values.put(KEY_IS_SIGNED, timePost.isSigned);
        values.put(KEY_COMMENT_SHARED, timePost.commentShared);
 
        // insert row
        db.insert(TABLE_TIMEPOST, null, values);
        
        //Return auto-ink ID?
    }
// 
//    /**
//     * get single todo
//     */
//    public Todo getTodo(long todo_id) {
//        SQLiteDatabase db = this.getReadableDatabase();
// 
//        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
//                + KEY_ID + " = " + todo_id;
// 
//        Log.e(LOG, selectQuery);
// 
//        Cursor c = db.rawQuery(selectQuery, null);
// 
//        if (c != null)
//            c.moveToFirst();
// 
//        Todo td = new Todo();
//        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
// 
//        return td;
//    }
// 
//    /**
//     * getting all todos
//     * */
//    public List<Todo> getAllToDos() {
//        List<Todo> todos = new ArrayList<Todo>();
//        String selectQuery = "SELECT  * FROM " + TABLE_TODO;
// 
//        Log.e(LOG, selectQuery);
// 
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
// 
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Todo td = new Todo();
//                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
// 
//                // adding to todo list
//                todos.add(td);
//            } while (c.moveToNext());
//        }
// 
//        return todos;
//    }
// 
//    /**
//     * getting all todos under single tag
//     * */
//    public List<Todo> getAllToDosByTag(String tag_name) {
//        List<Todo> todos = new ArrayList<Todo>();
// 
//        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, "
//                + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
//                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
//                + " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
//                + "tt." + KEY_TODO_ID;
// 
//        Log.e(LOG, selectQuery);
// 
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
// 
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Todo td = new Todo();
//                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
// 
//                // adding to todo list
//                todos.add(td);
//            } while (c.moveToNext());
//        }
// 
//        return todos;
//    }
// 
//    /**
//     * getting todo count
//     */
//    public int getToDoCount() {
//        String countQuery = "SELECT  * FROM " + TABLE_TODO;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
// 
//        int count = cursor.getCount();
//        cursor.close();
// 
//        // return count
//        return count;
//    }
// 
//    /**
//     * Updating a todo
//     */
//    public int updateToDo(Todo todo) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO, todo.getNote());
//        values.put(KEY_STATUS, todo.getStatus());
// 
//        // updating row
//        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(todo.getId()) });
//    }
// 
//    /**
//     * Deleting a todo
//     */
//    public void deleteToDo(long tado_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TODO, KEY_ID + " = ?",
//                new String[] { String.valueOf(tado_id) });
//    }
// 
//    // ------------------------ "tags" table methods ----------------//
// 
//    /**
//     * Creating tag
//     */
//    public long createTag(Tag tag) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_NAME, tag.getTagName());
//        values.put(KEY_CREATED_AT, getDateTime());
// 
//        // insert row
//        long tag_id = db.insert(TABLE_TAG, null, values);
// 
//        return tag_id;
//    }
// 
//    /**
//     * getting all tags
//     * */
//    public List<Tag> getAllTags() {
//        List<Tag> tags = new ArrayList<Tag>();
//        String selectQuery = "SELECT  * FROM " + TABLE_TAG;
// 
//        Log.e(LOG, selectQuery);
// 
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
// 
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Tag t = new Tag();
//                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));
// 
//                // adding to tags list
//                tags.add(t);
//            } while (c.moveToNext());
//        }
//        return tags;
//    }
// 
//    /**
//     * Updating a tag
//     */
//    public int updateTag(Tag tag) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_NAME, tag.getTagName());
// 
//        // updating row
//        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(tag.getId()) });
//    }
// 
//    /**
//     * Deleting a tag
//     */
//    public void deleteTag(Tag tag, boolean should_delete_all_tag_todos) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        // before deleting tag
//        // check if todos under this tag should also be deleted
//        if (should_delete_all_tag_todos) {
//            // get all todos under this tag
//            List<Todo> allTagToDos = getAllToDosByTag(tag.getTagName());
// 
//            // delete all todos
//            for (Todo todo : allTagToDos) {
//                // delete todo
//                deleteToDo(todo.getId());
//            }
//        }
// 
//        // now delete the tag
//        db.delete(TABLE_TAG, KEY_ID + " = ?",
//                new String[] { String.valueOf(tag.getId()) });
//    }
// 
//    // ------------------------ "todo_tags" table methods ----------------//
// 
//    /**
//     * Creating todo_tag
//     */
//    public long createTodoTag(long todo_id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_TODO_ID, todo_id);
//        values.put(KEY_TAG_ID, tag_id);
//        values.put(KEY_CREATED_AT, getDateTime());
// 
//        long id = db.insert(TABLE_TODO_TAG, null, values);
// 
//        return id;
//    }
// 
//    /**
//     * Updating a todo tag
//     */
//    public int updateNoteTag(long id, long tag_id) {
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        ContentValues values = new ContentValues();
//        values.put(KEY_TAG_ID, tag_id);
// 
//        // updating row
//        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
// 
//    /**
//     * Deleting a todo tag
//     */
//    public void deleteToDoTag(long id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_TODO, KEY_ID + " = ?",
//                new String[] { String.valueOf(id) });
//    }
// 
//    // closing database
//    public void closeDB() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        if (db != null && db.isOpen())
//            db.close();
//    }
// 
//    /**
//     * get datetime
//     * */
//    private String getDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Date date = new Date();
//        return dateFormat.format(date);
//    }
}