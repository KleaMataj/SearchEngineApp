package com.index.app.Util;

import org.apache.lucene.store.RAMDirectory;

public class AppState {

    private static RAMDirectory index ;

    public AppState() {
        index = new RAMDirectory();
    }

    public static RAMDirectory getIndex() {
        return index;
    }

    public static void setIndex(RAMDirectory index) {
        AppState.index = index;
    }
}
