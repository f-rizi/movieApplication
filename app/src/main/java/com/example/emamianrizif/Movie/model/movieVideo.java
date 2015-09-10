package com.example.emamianrizif.Movie.model;

import org.json.JSONObject;

/**
 * Created by fatemeh on 27/03/15.
 */
public class movieVideo {

    public static final String ID_KEY = "id";
    public static final String KEY_KEY = "key";
    public static final String NAME_KEY = "name";
    public static final String SITE_KEY = "site";
    public static final String TYPE_KEY = "type";
    public static final String ISO_KEY_636 = "iso_639_1";
    public static final String SIZE_KEY = "size";

    private String id;
    private String key;
    private String name;
    private String site;
    private String type;
    private String iso_639_1;

    private int size;

    public movieVideo(JSONObject object) {
        try {
            id = object.isNull(ID_KEY) ?
                    null : object.getString(ID_KEY);

            key = object.isNull(KEY_KEY) ?
                    null : object.getString(KEY_KEY);

            name = object.isNull(NAME_KEY) ?
                    null : object.getString(NAME_KEY);

            site = object.isNull(SITE_KEY) ?
                    null : object.getString(SITE_KEY);

            type = object.isNull(TYPE_KEY) ?
                    null : object.getString(TYPE_KEY);

            iso_639_1 = object.isNull(ISO_KEY_636) ?
                    null : object.getString(ISO_KEY_636);

            size = object.getInt(SIZE_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getId() {
        return id;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }
}
