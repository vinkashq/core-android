package vinkas.io;

/**
 * Created by Vinoth on 6-5-16.
 */
public abstract class Item extends Object implements DatabaseHaver {

    @Override
    public void onRead(String key, java.lang.Object value) {
        set(key, value);
    }

    private Database database;

    public Item(Database database, String childPath) {
        super(database.getFirebase().child(childPath));
        setDatabase(database);
    }

    @Override
    public Database getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

}
