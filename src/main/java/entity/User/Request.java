package entity.User;

import com.mongodb.DBCollection;

public interface Request {

    void accept(DBCollection collection);

    void decline(DBCollection collection);
}
