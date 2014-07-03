package swap.app.calsync;

import android.content.pm.FeatureInfo;
import android.provider.BaseColumns;

public final class DBHelperContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DBHelperContract() {}

    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
        FeedEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
        FeedEntry.COLUMN_MONTH + INTEGER_TYPE + COMMA_SEP +
        FeedEntry.COLUMN_DAY + INTEGER_TYPE +
        // Any other options for the CREATE command
        " )";

    public static final String SQL_DROP_TABLE = "DROP TABLE " + FeedEntry.TABLE_NAME + ";";
    
    public static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    
    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Birthday";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MONTH = "month";
        public static final String COLUMN_DAY = "day";
    }
}
