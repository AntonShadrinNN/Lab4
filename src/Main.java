import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        House h = new House();
        ScheduledExecutorService s = Executors.newScheduledThreadPool(2);

        // Thread to generate random calls to elevator
        s.scheduleAtFixedRate(new Runnable() {
            int ID = 0;
            @Override
            public void run() {
                int targetFloor;
                int curFloor = ThreadLocalRandom.current().nextInt(0, Constants.MAX_FLOOR + 1);
                do {
                    targetFloor = ThreadLocalRandom.current().nextInt(0, Constants.MAX_FLOOR + 1);
                } while (curFloor == targetFloor);
                ID++;
                h.addInQueue(new PassengerHandler(ID, curFloor, targetFloor));
            }
        }, 0, 3, TimeUnit.SECONDS);

        // Thread to deliver passengers
        s.scheduleAtFixedRate(new Runnable() {
            Elevator el1 = new Elevator(1, Constants.MAX_CAPACITY);
            Elevator el2 = new Elevator(2, Constants.MAX_CAPACITY);
            @Override
            public void run() {
                h.visitFloor(el1);
                h.visitFloor(el2);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}