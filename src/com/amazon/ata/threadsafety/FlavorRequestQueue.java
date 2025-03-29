package com.amazon.ata.threadsafety;

import com.amazon.ata.threadsafety.model.Flavor;
import com.amazonaws.annotation.ThreadSafe;
import com.amazon.ata.threadsafety.dao.CartonDao.*;

import java.util.Queue;

synchronized.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

public class FlavorRequestQueue {
    private final Queue<Flavor> flavorQueue;

    public FlavorRequestQueue() {
        flavorQueue = new LinkedList<>();
    }

    public synchronized void needFlavor(Flavor flavor) {
        flavorQueue.add(flavor);
    }

    public  Flavor nextNeededFlavor() {
        Flavor flavor = pollFlavorQueue();
        while (flavor == null) {
            try {
                Thread.sleep(10L);
                flavor = pollFlavorQueue();
            } catch (InterruptedException e) {
                System.out.println("!!!Interrupted waiting for flavor request!!!");
                e.printStackTrace();
                throw new RuntimeException("Interrupted waiting for flavor request!", e);
            }
        }
        return flavor;
    }

    private synchronyzed Flavor pollFlavorQueue() {
        return flavorQueue.poll();
    }

    public int requestCount() {
        return flavorQueue.size();
    }
}
