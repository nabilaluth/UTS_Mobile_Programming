package com.mobileprogramming.utsmobpro1904436.helper;

import android.database.Cursor;

import com.mobileprogramming.utsmobpro1904436.db.DatabaseContract;
import com.mobileprogramming.utsmobpro1904436.entity.CoffeeDrinkNote;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<CoffeeDrinkNote> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<CoffeeDrinkNote> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.TITLE));
            String category = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.CATEGORY));
            String desc = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.DESC));
            String food = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.NoteColumns.FOOD));
            notesList.add(new CoffeeDrinkNote(id, title, category, desc, food));
        }

        return notesList;
    }
}
