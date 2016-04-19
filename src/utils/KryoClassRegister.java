package utils;

import com.esotericsoftware.kryo.Kryo;

/**
 * Created by Sebastian on 17.04.2016.
 */
public class KryoClassRegister {
    public static void registeringClasses(Kryo k) {
        //userId stuff
        k.register(UserIdRequest.class);
        k.register(UserId.class);

        //file stuff
        k.register(SendingFile.class);
        k.register(byte.class);
        k.register(byte[].class);
    }
}
