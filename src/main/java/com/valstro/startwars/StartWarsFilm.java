package com.valstro.startwars;

import java.util.List;

public class StartWarsFilm {
    public final int page;
    public final int resultCount;
    public final String name;
    public final List<String> films;
    public final String error;

    public StartWarsFilm(int page, int resultCount, String name, List<String> films, String error) {
        this.page = page;
        this.resultCount = resultCount;
        this.name = name;
        this.films = films;
        this.error = error;
    }

    public String composeMessage() {
        return error == null ? "[" + page + "/" + resultCount + "] " + name + ": " + films : error;
    }

}


