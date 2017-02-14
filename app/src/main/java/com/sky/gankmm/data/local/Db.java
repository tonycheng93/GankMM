package com.sky.gankmm.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.sky.gankmm.data.model.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tonycheng on 2017/2/14.
 */

public class Db {

    public Db() {

    }

    public abstract static class GankTable {

        public static final String TABLE_NAME = "gank_result";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_DESC = "desc";

        public static final String COLUMN_IMAGES = "images";

        public static final String COLUMN_PUBLISH_AT = "publishAt";

        public static final String COLUMN_TYPE = "type";

        public static final String COLUMN_URL = "url";

        public static final String COLUMN_WHO = "who";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_DESC + " TEXT NOT NULL, " +
                        COLUMN_IMAGES + " TEXT, " +
                        COLUMN_PUBLISH_AT + " INTEGER NOT NULL, " +
                        COLUMN_TYPE + " TEXT NOT NULL, " +
                        COLUMN_URL + " TEXT NOT NULL, " +
                        COLUMN_WHO + " TEXT NOT NULL" +
                        ");";

        public static ContentValues toContentValues(List<Result> results) {
            ContentValues values = new ContentValues();
            for (Result result : results) {
                values.put(COLUMN_ID, result.id());
                values.put(COLUMN_DESC, result.desc());
                if (result.images() != null) {
                    StringBuilder builder = new StringBuilder();

                    for (int i = 0; i < result.images().size(); i++) {
                        builder.append(result.images().get(i)).append("&");
                    }
                    values.put(COLUMN_IMAGES, builder.toString());
                }
                values.put(COLUMN_PUBLISH_AT, result.publishAt().getTime());
                values.put(COLUMN_TYPE, result.type());
                values.put(COLUMN_URL, result.url());
                values.put(COLUMN_WHO, result.who());
                return values;
            }
            return null;
        }

        public static Result parseCursor(Cursor cursor) {
            String imagesString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGES));
            List<String> images = null;
            if (!imagesString.isEmpty()) {
                images = new ArrayList<>();
                String[] split = imagesString.split("&");
                for (int i = 0; i < split.length; i++) {
                    images.add(split[i]);
                }
            }

            long publishAt = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PUBLISH_AT));

            return Result.builder()
                    .setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)))
                    .setDesc(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)))
                    .setImages(images)
                    .setPublishAt(new Date(publishAt))
                    .setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)))
                    .setUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)))
                    .setWho(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WHO)))
                    .build();
        }
    }
}
