package com.example.weightlosstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Database
     */
    private static final String DATABASE_NAME = "WEIGHTTRACKER.DB";

    /**
     * Tables
     */
    private static final String TABLE_PROGRESS = "PROGRESS_TABLE";
    private static final String TABLE_WORKOUT = "WORKOUT_TABLE";
    private static final String TABLE_ROUTINE = "ROUTINE_TABLE";
    private static final String TABLE_WBUNCH = "WBUNCH_TABLE";
    private static final String TABLE_DAY = "DAY_TABLE";
    private static final String TABLE_FOOD = "FOOD_TABLE";
    private static final String TABLE_NUTRITION = "NUTRITION_TABLE";
    private static final String TABLE_FBUNCH = "FBUNCH_TABLE";

    /**
     * Columns
     */
    //Progress Table
    private static final String PROGRESS_COL_ID = "ID_PROGRESS";
    private static final String PROGRESS_COL_WEIGHT = "WEIGHT";
    private static final String PROGRESS_COL_DATE = "DATE";
    //Workout Table
    private static final String WORKOUT_COL_ID = "ID_WORKOUT";
    private static final String WORKOUT_COL_NAME = "NAME";
    private static final String WORKOUT_COL_SETS = "SETS";
    private static final String WORKOUT_COL_REPS = "REPS";
    //Food Table
    private static final String FOOD_COL_ID = "ID_FOOD";
    private static final String FOOD_COL_NAME = "NAME";
    private static final String FOOD_COL_CALORIES = "CALORIES";
    //FBunch Table
    private static final String FBUNCH_COL_ID = "ID_FBUNCH";
    private static final String FBUNCH_COL_FOOD = "ID_FOOD";
    private static final String FBUNCH_COL_NUTRITION = "ID_NUTRITION";
    //Nutrition Table
    private static final String NUTRITION_COL_ID = "ID_NUTRITION";
    private static final String NUTRITION_COL_TEXT = "DATA";
    //WBunch Table
    private static final String WBUNCH_COL_ID = "ID_WBUNCH";
    private static final String WBUNCH_COL_WORKOUT = "ID_WORKOUT";
    private static final String WBUNCH_COL_ROUTINE = "ID_ROUTINE";
    //Routine Table
    private static final String ROUTINE_COL_ID = "ID_ROUTINE";
    private static final String ROUTINE_COL_TEXT = "DATA";
    //Day Table
    private static final String DAY_COL_ID = "ID_DAY";
    private static final String DAY_COL_NUTRITION = "ID_NUTRITION";
    private static final String DAY_COL_ROUTINE = "ID_ROUTINE";
    private static final String DAY_COL_DATE = "DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Query to create tables here
        db.execSQL("CREATE TABLE " + TABLE_PROGRESS + " (ID_PROGRESS INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "WEIGHT REAL, " +
                "DATE TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_WORKOUT + " (ID_WORKOUT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "SETS INTEGER, " +
                "REPS INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_ROUTINE + " (ID_ROUTINE INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATA TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_WBUNCH + " (ID_WBUNCH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_WORKOUT INTEGER, " +
                "ID_ROUTINE INTEGER, " +
                "FOREIGN KEY (ID_WORKOUT) " +
                "REFERENCES WORKOUT_TABLE(ID_WORKOUT), " +
                "FOREIGN KEY (ID_ROUTINE) " +
                "REFERENCES ROUTINE_TABLE(ID_ROUTINE))");

        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (ID_FOOD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "CALORIES INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_NUTRITION + " (ID_NUTRITION INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATA TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_FBUNCH + " (ID_FBUNCH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ID_FOOD INTEGER, " +
                "ID_NUTRITION INTEGER, " +
                "FOREIGN KEY (ID_FOOD) " +
                "REFERENCES FOOD_TABLE(ID_FOOD), " +
                "FOREIGN KEY (ID_NUTRITION) " +
                "REFERENCES NUTRITION_TABLE(ID_NUTRITION))");

        db.execSQL("CREATE TABLE " + TABLE_DAY + " (ID_DAY INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "DATE TEXT, " +
                "ID_NUTRITION INTEGER, " +
                "ID_ROUTINE INTEGER, " +
                "FOREIGN KEY (ID_NUTRITION) " +
                "REFERENCES NUTRITION_TABLE(ID_NUTRITION), " +
                "FOREIGN KEY (ID_ROUTINE) " +
                "REFERENCES ROUTINE_TABLE(ID_ROUTINE))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Deletes then recreates the database
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WBUNCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FBUNCH);
        onCreate(db);
    }


    /**
     * TABLE PROGRESS
     */

    /**
     * Insert data into TABLE PROGRESS
     *
     * @return
     */
    public boolean progressInsertData(String weight, String date) {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();

        // The var that we put the values we want to insert into the database in
        ContentValues contentValues = new ContentValues();
        // We get the column name and then the value from the parameter
        contentValues.put(PROGRESS_COL_WEIGHT, weight);
        contentValues.put(PROGRESS_COL_DATE, date);
        // Use this command to execute
        // If failed to insert it will return -1
        long result = db.insert(TABLE_PROGRESS, null, contentValues);
        db.close();
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * Get all data from the database
     * @return
     */
    public Cursor progressGetAllData() {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_PROGRESS, null);
        return res;
    }


    /**
     * Get the first and last records from the database
     * takes inputs either "Start" or "Current" and gets the first or last recording in the database
     * @param
     * @return
     */
    public Cursor progressGetMinMaxData(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (s.equals("Start")) {
            Cursor res = db.rawQuery("SELECT * FROM  PROGRESS_TABLE ORDER BY ID_PROGRESS ASC LIMIT 1;", null);
            return res;
        } else if (s.equals("Current")) {
            Cursor res = db.rawQuery("SELECT * FROM  PROGRESS_TABLE ORDER BY ID_PROGRESS DESC LIMIT 1;", null);
            return res;
        }
        return null;
    }

    /**
     * Removes a record from the database by the ID of the record
     *
     * Returns the number of records deleted
     *
     * @param id
     * @return
     */
    public Integer progressRemoveData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // To use db.delete we require 3 parameters:
        // The name of the table
        // The field we are looking to delete on what column
        // The Array of String values that we want deleted (In this case we just want one String value)
        return db.delete(TABLE_PROGRESS, "ID_PROGRESS = ?", new String[]{id});
    }

    /**
     * WORKOUT TABLE
     */

    /**
     * Insert data into the WORKOUT TABLE
     * @param name
     * @param sets
     * @param reps
     * @return
     */
    public boolean workoutInsertData(String name, String sets, String reps) {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();

        // The var that we put the values we want to insert into the database in
        ContentValues contentValues = new ContentValues();
        // We get the column name and then the value from the parameter
        contentValues.put(WORKOUT_COL_NAME, name);
        contentValues.put(WORKOUT_COL_SETS, sets);
        contentValues.put(WORKOUT_COL_REPS, reps);
        // Use this command to execute
        // If failed to insert it will return -1
        long result = db.insert(TABLE_WORKOUT, null, contentValues);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean foodInsertData(String name, String calories) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues aa = new ContentValues();
        aa.put(FOOD_COL_NAME, name);
        aa.put(FOOD_COL_CALORIES, calories);
        long result = db.insert(TABLE_FOOD, null, aa);
        db.close();
        if(result == -1) {
            return false;
        } else {
            System.out.println("True");
            return true;
        }
    }

    /**
     * Get the whole list of workouts from the WORKOUT TABLE
     * @return
     */
    public Cursor workoutGetAllData() {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_WORKOUT, null);
        return res;
    }

    /**
     *
     *
     * DAY TABLE
     *
     *
     */

    /**
     * Import method and called at start of MainActivity.class
     *
     * Checks the current day and compares with the most recent record's date.
     * If dates match:
     *  - Means that the day record was made on the same day, therefore nothing needs to happen
     *
     * I dates do not match:
     *  - We can assume that its another day so we need to create a new day record. Calls newDay() method
     */
    public void checkDay() {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();

        // Getting today's date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        // Getting the date from the latest record in the Date table
        // If the result is empty then create new Day record
        Cursor date = db.rawQuery("SELECT * FROM " + TABLE_DAY, null);
        if (date.getCount() <= 0) {
            newDay(df.format(c));
            return;
        }

        date = db.rawQuery("SELECT * FROM  DAY_TABLE ORDER BY ID_DAY DESC LIMIT 1;", null);
        date.moveToFirst();
        // If the date matches the date in the latest record
        if (date.getString(1).equals(df.format(c))) {
            return;
        }
        // if the date does not match then we can assume its because its the next day so we create a new record
        else {
            newDay(df.format(c));
            return;
        }
    }

    /**
     * Only called if its a new day.
     *
     * Creates new record for TABLE_DAY, this also means we have to create new TABLE_ROUTINE and TABLE_NUTRITION records.
     * @param date
     */
    private void newDay(String date) {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new record in the Routine table
        ContentValues routineValues = new ContentValues();
        long result = db.insert(TABLE_ROUTINE, null, routineValues);
        ContentValues nutritionValues = new ContentValues();
        result = db.insert(TABLE_NUTRITION, null, nutritionValues);
        ContentValues contentValues = new ContentValues();
        // We get the column name and then the value from the parameter
        routineCreateRecord();
        nutritionCreateRecord();
        Cursor nutrition = db.rawQuery("SELECT * FROM " + TABLE_NUTRITION, null);
        Cursor routine = db.rawQuery("SELECT * FROM " + TABLE_ROUTINE, null);
        nutrition.moveToLast();
        routine.moveToLast();
        contentValues.put(DAY_COL_NUTRITION, nutrition.getString(0));
        contentValues.put(DAY_COL_ROUTINE, routine.getString(0));
        contentValues.put(DAY_COL_DATE, date);
        // Use this command to execute
        // If failed to insert it will return -1
        result = db.insert(TABLE_DAY, null, contentValues);
        db.close();
    }

    /**
     * Simple method that returns the latest records date column. Used to within other methods
     * @return
     */
    public Cursor getDay() {
        // get the database to mess with
        SQLiteDatabase db = this.getWritableDatabase();
        // Getting the latest record from the Day Table
        Cursor res = db.rawQuery("SELECT * FROM  DAY_TABLE ORDER BY ID_DAY DESC LIMIT 1;", null);
        return res;
    }

    /**
     *
     *
     * ROUTINE TABLE
     *
     *
     */

    private void routineCreateRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROUTINE_COL_TEXT, "DATA");
        db.insert(TABLE_ROUTINE, null, contentValues);
    }

    /**
     * Returns the Cursor that contains all workout records that were assigned for today
     * @return
     */
    public Cursor routineGetTodaysWorkouts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        Cursor dayRes = getDay();
        dayRes.moveToNext();
        String routineId = dayRes.getString(3);
        // Getting records from TABLE_WBUNCH which match the ID of foreign key, from TABLE_ROUTINE, of the latest record from TABLE_DAY
        Cursor wBunchRes = db.rawQuery("SELECT * FROM " + TABLE_WBUNCH + " WHERE ID_ROUTINE = " + routineId, null);
        if (wBunchRes == null || wBunchRes.getCount() <= 0) {
            System.out.println("The Wbunch is empty");
            return res;
        } else {
            // Creates an ArrayList with all the Ids of workouts being used
            StringBuffer buffer = new StringBuffer();
            while (wBunchRes.moveToNext()) {
                buffer.append(wBunchRes.getString(1) +",");
            }
            buffer.setLength(buffer.length() - 1);

            //Cursor res = db.rawQuery("SELECT * FROM " + TABLE_WORKOUT + " WHERE ID_WORKOUT = 1", null);
            res = db.query(TABLE_WORKOUT, null, "ID_WORKOUT IN ("+buffer.toString()+")", null, null, null, null);
            return res;
        }


    }

    /**
     * Adds a record to todays Record
     * @return
     */
    public void routineAddTodayRecords(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dayRes = getDay();
        dayRes.moveToNext();
        // Getting the ID for routine
        String routineId = dayRes.getString(0);
        // Assigning the IDs
        ContentValues contentValues = new ContentValues();
        contentValues.put(WBUNCH_COL_ROUTINE, routineId);
        contentValues.put(WBUNCH_COL_WORKOUT, id);
        db.insert(TABLE_WBUNCH, null, contentValues);
    }

    /**
     * Deletes workout from todays routine
     * @param id
     */
    public Integer routineRemoveRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Getting todays routine id
        Cursor res = getDay();
        res.moveToNext();
        Integer result = db.delete(TABLE_WBUNCH, "ID_WORKOUT = (?) AND ID_ROUTINE = (?)", new String[]{Integer.toString(id), res.getString(3)});
        return result;
    }

    /**
     *
     *
     * NUTRITION TABLE
     *
     *
     */

    /**
     * Creates a new record for TABLE_NUTRITION, this is for TABLE_DAY's creation of a record
     */
    private void nutritionCreateRecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NUTRITION_COL_TEXT, "DATA");
        db.insert(TABLE_NUTRITION, null, contentValues);
    }

    /**
     * Returns the Cursor that contains all workout records that were assigned for today
     * @return
     */
    public Cursor nutritionGetTodaysFood() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = null;
        Cursor dayRes = getDay();
        dayRes.moveToNext();
        String nutritionId = dayRes.getString(2);
        // Getting records from TABLE_FBUNCH which match the ID of foreign key, from TABLE_NUTRITION, of the latest record from TABLE_DAY
        Cursor wFunchRes = db.rawQuery("SELECT * FROM " + TABLE_FBUNCH + " WHERE ID_NUTRITION = " + nutritionId, null);
        if (wFunchRes == null || wFunchRes.getCount() <= 0) {
            System.out.println("nothign here");
            return res;
        } else {
            // Creates an ArrayList with all the Ids of workouts being used
            StringBuffer buffer = new StringBuffer();
            while (wFunchRes.moveToNext()) {
                buffer.append(wFunchRes.getString(1) +",");
            }
            buffer.setLength(buffer.length() - 1);

            //Cursor res = db.rawQuery("SELECT * FROM " + TABLE_WORKOUT + " WHERE ID_WORKOUT = 1", null);
            res = db.query(TABLE_FOOD, null, "ID_FOOD IN ("+buffer.toString()+")", null, null, null, null);
            return res;
        }
    }

    /**
     * Adds a record to todays Record
     * @return
     */
    public void nutritionAddTodayRecords(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dayRes = getDay();
        dayRes.moveToNext();
        // Getting the ID for routine
        String nutritionId = dayRes.getString(2);
        // Assigning the IDs
        ContentValues contentValues = new ContentValues();
        contentValues.put(FBUNCH_COL_NUTRITION, nutritionId);
        contentValues.put(FBUNCH_COL_FOOD, id);
        db.insert(TABLE_FBUNCH, null, contentValues);
    }

    /**
     * Deletes workout from todays nutrition
     * @param id
     */
    public Integer nutritionRemoveRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Getting todays routine id
        Cursor res = getDay();
        res.moveToNext();
        Integer result = db.delete(TABLE_WBUNCH, "ID_WORKOUT = (?) AND ID_ROUTINE = (?)", new String[]{Integer.toString(id), res.getString(2)});
        return result;
    }
}
