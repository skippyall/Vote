package de.skipall.vote.core.user;

import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import de.skipall.vote.core.misc.EnvironmentVars;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserStorage {
    private static File file = new File(EnvironmentVars.dataFolder, "users.json");
    private static long idCounter=0;
    private static Gson gson = new Gson();
    private static Map<AuthID, long> names = new HashMap<AuthID, long>();

    private static Map<Long,User> users = new HashMap<>();

    public static void load() {
        if(!file.exists()){
            return;
        }

        try (FileReader reader = new FileReader(file)){
            JsonReader jreader = gson.newJsonReader(reader);
            JsonObject json = Streams.parse(jreader).getAsJsonObject();
            JsonArray users = json.get("users").getAsJsonArray();
            for (JsonElement user:users) {
                JsonObject userObj = user.getAsJsonObject();
                long id = userObj.get("id").getAsJsonPrimitive().getAsLong();
                UserStorage.users.put(id, new User(userObj.get("isBot").getAsBoolean(), userObj.get("canEditOther").getAsBoolean(), id, userObj.get("name").getAsString()));
            }
            idCounter = json.get("idCounter").getAsLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void save() {
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            JsonWriter jwriter = gson.newJsonWriter(writer);
            JsonObject json = new JsonObject();
            // Save Users
            JsonArray users = new JsonArray();
            UserStorage.users.forEach((id, user) -> {
                JsonObject gUser = new JsonObject();
                gUser.addProperty("isBot", user.isBot);
                gUser.addProperty("canEditOther", user.canEditOther);
                gUser.addProperty("id", user.id);
                gUser.addProperty("name", user.name);
            });

            json.add("users", users);
            json.add("idCounter", new JsonPrimitive(idCounter));
            Streams.write(json,jwriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static User createUser(boolean isBot, boolean canEditOther, String name) {
        User user = new User(isBot, canEditOther, newId(), name);
        users.put(user.getId(),user);
        return user;
    }

    public static User getUser(long id) {
        return users.get(id);
    }

    public static User getUser(AuthID id) {
        return getUser(names.get(id));
    }

    private static long newId(){
        idCounter++;
        return idCounter;
    }
}
