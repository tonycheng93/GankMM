package com.sky.gankmm.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.sky.gankmm.data.model.Result;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by tonycheng on 2017/2/14.
 */

public class Db {

    public Db() {

    }

    abstract static class GankTable {

        static final String TABLE_NAME = "gank_result";

        static final String COLUMN_ID = "_id";

        static final String COLUMN_DESC = "desc";

        static final String COLUMN_IMAGES = "images";

        static final String COLUMN_PUBLISHED_AT = "publishedAt";

        static final String COLUMN_TYPE = "type";

        static final String COLUMN_URL = "url";

        static final String COLUMN_WHO = "who";

        static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_DESC + " TEXT NOT NULL, " +
                        COLUMN_IMAGES + " TEXT, " +
                        COLUMN_PUBLISHED_AT + " INTEGER NOT NULL, " +
                        COLUMN_TYPE + " TEXT NOT NULL, " +
                        COLUMN_URL + " TEXT NOT NULL, " +
                        COLUMN_WHO + " TEXT" +
                        ");";

        static ContentValues toContentValues(Result result) {
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, result.id());
            values.put(COLUMN_DESC, result.desc());
            if (result.images() != null) {
                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < result.images().size(); i++) {
                    builder.append(result.images().get(i)).append("&");
                }
                values.put(COLUMN_IMAGES, builder.toString());
            }
            values.put(COLUMN_PUBLISHED_AT, result.publishedAt());
            values.put(COLUMN_TYPE, result.type());
            values.put(COLUMN_URL, result.url());
            values.put(COLUMN_WHO, result.who());
            return values;

        }

        static Result parseCursor(Cursor cursor) {
            String imagesString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGES));
            List<String> images = null;
            if (!TextUtils.isEmpty(imagesString)) {
                images = new ArrayList<>();
                String[] split = imagesString.split("&");
                for (int i = 0; i < split.length; i++) {
                    images.add(split[i]);
                    Timber.i("images = " + images.get(i));
                }
            }

            return Result.builder()
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setDesc(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)))
                    .setImages(images)
                    .setPublishedAt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHED_AT)))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)))
                    .setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)))
                    .setWho(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WHO)))
                    .build();
        }
    }
}
