package com.cayden.collect.memo.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by cuiran on 16/5/12.
 */
public final class TasksPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TasksPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COMPLETED = "completed";
    }
}
