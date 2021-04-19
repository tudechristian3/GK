package com.goodkredit.myapplication.database.egame;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.egame.EGameProducts;

import java.util.ArrayList;
import java.util.List;

public class EGameProductsDBHelper {

    public static String TABLE_NAME = "EGameProducts";

    public static String ID = "ID";
    public static String ProductCode = "ProductCode";
    public static String Amount = "Amount";
    public static String Description = "Description";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            ProductCode + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Amount + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Description + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert (EGameProducts data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(ProductCode, data.getProductCode());
        contentValues.put(Amount, data.getAmount());
        contentValues.put(Description, data.getDescription());

        return contentValues;
    }

//    public static ContentValues insertString (String data){
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(ID, data.getID());
//        contentValues.put(ProductCode, data.getProductCode());
//        contentValues.put(Amount, data.getAmount());
//        contentValues.put(Description, data.getDescription());
//    }

    public static List<EGameProducts> getEGameProducts(Cursor cursor){
        List<EGameProducts> egameproducts = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String productcode = cursor.getString(cursor.getColumnIndex(ProductCode));
                double amount = cursor.getDouble(cursor.getColumnIndex(Amount));
                String description = cursor.getString(cursor.getColumnIndex(Description));

                egameproducts.add(new EGameProducts(
                        id,
                        productcode,
                        amount,
                        description
                ));
            }
        }

        return egameproducts;
    }
}
