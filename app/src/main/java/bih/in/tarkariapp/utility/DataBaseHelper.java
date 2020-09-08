package bih.in.tarkariapp.utility;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import bih.in.tarkariapp.entity.DeliveryVendorUserDetail;
import bih.in.tarkariapp.entity.UserDetail;

/**
 * Helper to the database, manages versions and creation
 */
public class DataBaseHelper extends SQLiteOpenHelper
{
    //private static String DB_PATH = "";
    private static String DB_PATH = "/data/data/app.bih.in.nic.epacsmgmt/databases/";
    private static String DB_NAME = "PACSDB1";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    SQLiteDatabase db;

    public DataBaseHelper(Context context)
    {

        super(context, DB_NAME, null, 2);
        if (android.os.Build.VERSION.SDK_INT >= 4.2)
        {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException
    {

        boolean dbExist = checkDataBase();

        if (dbExist)
        {
            // do nothing - database already exist
        }
        else
        {
            this.getReadableDatabase();

            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DB_PATH + DB_NAME;
           //this.getReadableDatabase();
            checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);

        }
        catch (SQLiteException e)
        {
        }

        if (checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException
    {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException
    {
        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close()
    {
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public void AlterTable(String tableName, String columnName)
    {
        db = this.getReadableDatabase();

        try
        {
            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed",tableName +"-"+ columnName);
        }
    }

    public boolean isColumnExists (String table, String column)
    {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name))
                {
                    return true;
                }
            }
        }
        cursor.close();
        return false;
    }

    public long getUserCount()
    {

        long x = 0;
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserLogin", null);

            x = cur.getCount();

            cur.close();
            db.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return x;
    }

    public UserDetail getUserDetails(String userId, String pass)
    {

        UserDetail userInfo = null;

        try
        {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{userId.trim(), pass};

            Cursor cur = db.rawQuery("Select * from UserDetail WHERE UserID=? and UserPassword=?",params);
            int x = cur.getCount();

            while (cur.moveToNext())
            {
                userInfo = new UserDetail();
//                userInfo.setUserId(cur.getString(cur.getColumnIndex("UserID")));
//                userInfo.setUserId(cur.getString(cur.getColumnIndex("UserName")));
//                userInfo.setPassword(cur.getString(cur.getColumnIndex("UserPassword")));
//
////                userInfo.setPassword(cur.getString(cur.getColumnIndex("MobileNo")));
////                userInfo.setPassword(cur.getString(cur.getColumnIndex("Email")));
//
//                userInfo.setRoleId(cur.getString(cur.getColumnIndex("RoleId")));
//                userInfo.setRoleName(cur.getString(cur.getColumnIndex("Role")));
//                //userInfo.setAuthenticated(true);
//                userInfo.setDistCode(cur.getString(cur.getColumnIndex("DistCode")));
//                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
//                userInfo.setDivisionCode(cur.getString(cur.getColumnIndex("DivisionCode")));
//                userInfo.setDivisionName(cur.getString(cur.getColumnIndex("DivisionName")));
//                userInfo.setZoneCode(cur.getString(cur.getColumnIndex("ZoneCode")));
//                userInfo.setZoneName(cur.getString(cur.getColumnIndex("ZoneName")));
            }

            cur.close();
            db.close();

        }
        catch (Exception e)
        {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;
    }

    public long deleteSchemeRecord()
    {

        long c = -1;

        try
        {

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from SurfaceSchemeDetail");

            db.close();

        }
        catch (Exception e)
        {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long insertUserDetails(UserDetail result)
    {
        long c = 0;
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("RegistrationNO", result.getRegistrationNO());
            values.put("UserID", result.getUserID());
            values.put("UserName", result.getUserName());
            values.put("Role", result.getRole());
            values.put("DistCode", result.getDistCode());
            values.put("BlockCode", result.getBlockCode());
            values.put("ApplicantMob", result.getApplicantMob());

            String[] whereArgs = new String[]{result.getUserID()};

            c = db.update("UserDetailFarmers", values, "RegistrationNO=? ", whereArgs);

            if (!(c > 0))
            {
                //c = db.insert("UserDetail", null, values);
                c = db.insertWithOnConflict("UserDetailFarmers", null, values,SQLiteDatabase.CONFLICT_REPLACE);
            }

            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;
    }



    public long insertVendorDetails(DeliveryVendorUserDetail result)
    {
        long c = 0;
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("id", result.getId());
            values.put("username", result.getName());
            values.put("reg_no", result.getRegistrationno());
            values.put("mobile", result.getMobilenumber());
            values.put("dob", result.getDOB());
            values.put("entereddate", result.getEntereddate());


            String[] whereArgs = new String[]{result.getRegistrationno()};

            c = db.update("UserdetailsVendor", values, "reg_no=? ", whereArgs);

            if (!(c > 0))
            {
                //c = db.insert("UserDetail", null, values);
                c = db.insertWithOnConflict("UserdetailsVendor", null, values,SQLiteDatabase.CONFLICT_REPLACE);
            }

            db.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;
    }
    public String getNameFor(String tblName, String whereColumnName, String returnColumnValue, String thisID) {
        String thisValue = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("select * from " + tblName + " WHERE " + whereColumnName + "='" + thisID.trim() + "'", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                thisValue = cur.getString(cur.getColumnIndex(returnColumnValue));
            }
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisValue.trim();
    }

}