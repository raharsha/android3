package com.example.haswath.popularmovies.dummy;

import com.example.haswath.popularmovies.MovieData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<MovieData> ITEMS = new ArrayList<MovieData>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, MovieData> ITEM_MAP = new HashMap<String, MovieData>();

    private static void addItem(MovieData item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


}
