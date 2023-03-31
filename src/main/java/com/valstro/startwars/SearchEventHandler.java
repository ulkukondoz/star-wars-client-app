package com.valstro.startwars;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchEventHandler implements Runnable {
    private static final Logger LOG = LogManager.getLogger(SearchEventHandler.class);
    private final AtomicBoolean state;
    private final BlockingQueue<StartWarsFilm> queue;

    public SearchEventHandler(AtomicBoolean state, BlockingQueue<StartWarsFilm> queue) {
        this.state = state;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                var starWarsFilm = queue.take();
                System.out.println(starWarsFilm.composeMessage());

                if (starWarsFilm.page == starWarsFilm.resultCount) {
                    state.set(true);
                    LOG.debug("Changing state back to true");
                }
            } catch (InterruptedException e) {
                LOG.error("Error while consuming message " + e.getMessage());
                state.set(true);
            }
        }
    }
}
