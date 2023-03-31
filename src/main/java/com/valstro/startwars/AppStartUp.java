package com.valstro.startwars;

import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AppStartUp {
    private static final String DEFAULT_URI = "http://localhost:3000";
    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        var uri = args.length == 0 ? DEFAULT_URI : args[0];

        var client = new StartWarsClient(uri);
        var socket = client.startConnection();
        // give some time to establish the connection
        TimeUnit.SECONDS.sleep(3);

        final var scanner = new Scanner(System.in);

        final BlockingQueue<StartWarsFilm> queue = new LinkedBlockingQueue<>();
        final var state = new AtomicBoolean(true);

        final var service = new SearchEventService(socket, queue);

        final var handler = new Thread(new SearchEventHandler(state, queue));
        handler.start();

        while (true) {
            if (state.compareAndSet(true, false)) {
                System.out.println("\nWhich Star Wars character do you want to search ?");
                var name = scanner.nextLine();
                service.search(name);
            }
        }
    }
}