import java.util.*;

public class House {
    private ArrayDeque<PassengerHandler> queue = new ArrayDeque<>();
    private Map<Integer, ArrayDeque<PassengerHandler>> active = new HashMap<>();

    // process passenger comes in an elevator
    private void EnterPassenger(Elevator el) {
        int curFloor = el.getCurFloor();
        if (!active.containsKey(curFloor) || active.get(curFloor).isEmpty()) {
            return;
        }
        Direction elDir = el.getDir();
        int elTFloor = el.getTargetFloor();

        Deque<PassengerHandler> unprocessed = new ArrayDeque<>();

        for (PassengerHandler passenger: active.get(curFloor)) {
            int pasTFloor = passenger.getTargetFloor();
            Direction pasDir = passenger.getDir();
            if (curFloor == elTFloor || pasDir == elDir) {
                el.makeActive();
                el.setDir(pasDir);
                el.getPassengers().put(passenger.getId(), pasTFloor);

                if (pasTFloor > elTFloor) {
                    el.setTargetFloor(pasTFloor);
                }

                if (pasTFloor < elTFloor) {
                    el.setTargetFloor(pasTFloor);
                }

                System.out.printf("Passenger %d comes in %d and goes to %d\n", passenger.getId(),
                        passenger.getCurFloor(), pasTFloor);
                unprocessed.add(passenger);
            }
        }

        queue.removeAll(unprocessed);
        active.get(curFloor).removeAll(unprocessed);
    }

    // process passenger leaves an elevator
    private void LeavePassenger(Elevator el) {
        Deque<Integer> removeID = new ArrayDeque<>();

        for (Map.Entry<Integer, Integer> entry: el.getPassengers().entrySet()) {
            if (entry.getValue() == el.getCurFloor()) {
                System.out.printf("Passenger %d leave on the floor %d\n", entry.getKey(), entry.getValue());
                removeID.add(entry.getKey());
            }
        }

        for (int ID :removeID) {
            el.getPassengers().remove(ID);
        }
    }

    // add new call to elevator in a queue
    public synchronized void addInQueue(PassengerHandler handler) {
        if (!active.containsKey(handler.getCurFloor())) {
            active.put(handler.getCurFloor(), new ArrayDeque<>());
        }
        active.get(handler.getCurFloor()).add(handler);
        queue.add(handler);
        System.out.printf("Passenger %d wants to travel from %d to %d floor\n", handler.getId(),
                handler.getCurFloor(), handler.getTargetFloor());
    }

    // go through the floors until target floor or max/min floor and
    // get passengers going in the same direction
    public synchronized void visitFloor(Elevator el) {
        System.out.printf("Elevator %d is on %d floor\n", el.getID(),  el.getCurFloor());
        LeavePassenger(el);
        EnterPassenger(el);
        if (queue.isEmpty() && el.getPassengers().isEmpty()) {
            System.out.println("No passengers now");
            el.makeActive();
            return;
        }

        if (queue.isEmpty()) {
            el.makePassive();
        }

        if (!el.isGoing()) {
            for (PassengerHandler ph : queue) {
                if (!ph.isGoing()) {
                    ph.setGoing(true);
                    el.setTargetFloor(ph.getCurFloor());
//                    el.setDir(ph.getCurFloor() > el.getCurFloor() ? Direction.UP : Direction.DOWN );
                    if (ph.getCurFloor() > el.getCurFloor()) el.setDir(Direction.UP);
                    else el.setDir(Direction.DOWN);
                    el.makeActive();
                    break;
                }
            }
        }

        if (!el.isGoing()) return;

        if (el.getDir() == Direction.UP && (el.getCurFloor() == el.getTargetFloor() || el.getCurFloor() == Constants.MAX_FLOOR)) {
            el.setDir(Direction.DOWN);
            el.makePassive();
        } else if (el.getDir() == Direction.DOWN && (el.getCurFloor() == el.getTargetFloor() || el.getCurFloor() == 0)) {
            el.setDir(Direction.UP);
            el.makePassive();
        }
        if (el.getDir() == Direction.UP) {
            el.changeFloor(1);
        }
        else {
            el.changeFloor(-1);
        }
    }
}
