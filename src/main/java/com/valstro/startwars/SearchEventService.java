package com.valstro.startwars;

import io.socket.client.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static java.util.Collections.singletonMap;

public class SearchEventService {
    private static final Logger LOG = LogManager.getLogger(SearchEventService.class);
    private final Socket socket;
    private final BlockingQueue<StartWarsFilm> queue;

    public SearchEventService(Socket socket, BlockingQueue<StartWarsFilm> queue) {
        this.socket = socket;
        this.queue = queue;
    }

    public final void search(String pattern) {
        socket.off("search");
        socket.emit("search", new JSONObject(singletonMap("query", pattern)))
                .on("search", args -> {
                    try {
                        var startWarsFilm = parse((JSONObject) args[0], pattern);
                        queue.add(startWarsFilm);
                    } catch (Exception e) {
                        LOG.error("Error while processing message " + args[0]);
                    }
                });
    }

    private static StartWarsFilm parse(JSONObject json, String pattern) throws JSONException {
        var page = json.getInt("page");
        var resultCount = json.getInt("resultCount");
        var name = json.has("error") ? pattern : json.getString("name");
        List<String> films = json.has("error") ? List.of() : Arrays.stream(json.getString("films").split(",")).toList();
        var error = json.has("error") ? json.getString("error") : null;
        return new StartWarsFilm(page, resultCount, name, films, error);
    }
}
