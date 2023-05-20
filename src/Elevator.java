import java.util.HashMap;
public class Elevator {
    private Direction dir;
    private int ID;
    private int curFloor, targetFloor;
    private int capacity;
    private boolean isGoing;
    private HashMap<Integer, Integer> passengers = new HashMap<>();

    public Elevator(int ID, int cap) {
        this.ID = ID;
        capacity = cap;
        isGoing = false;
        targetFloor = 0;
        curFloor = 1;
        dir = Direction.UP;
        isGoing = false;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        if (curFloor < 0 || curFloor > Constants.MAX_FLOOR){
            throw new IndexOutOfBoundsException("Current floor cannot be greater than MAX_FLOOR(20) and lower than 0");
        }
        this.curFloor = curFloor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity < 1) {
            throw new IndexOutOfBoundsException("Capacity cannot be lower than 1");
        }
        this.capacity = capacity;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        if (targetFloor < 0 || targetFloor > Constants.MAX_FLOOR) {
            throw new IndexOutOfBoundsException("Target floor cannot be greater than MAX_FLOOR(20) and lower than 0");
        }
        this.targetFloor = targetFloor;
    }

    public HashMap<Integer, Integer> getPassengers() {
        return passengers;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public int getID() {
        return ID;
    }

    public void makeActive() {
        this.isGoing = true;
    }

    public void makePassive() {
        this.isGoing = false;
    }

    public void changeFloor(int delta) {
        curFloor += delta;
    }
}
