public class PassengerHandler {
    private int targetFloor, curFloor, id;
    private Direction dir;
    private boolean isGoing = false;

    public PassengerHandler(int id, int current, int target) {
        this.id = id;
        targetFloor = target;
        curFloor = current;
        dir = target < current ? Direction.DOWN : Direction.UP;
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

    public int getCurFloor() {
        return curFloor;
    }

    public void setCurFloor(int curFloor) {
        if (curFloor < 0 || curFloor > Constants.MAX_FLOOR) {
            throw new IndexOutOfBoundsException("Current floor cannot be greater than MAX_FLOOR(20) and lower than 0");
        }
        this.curFloor = curFloor;
    }

    public boolean isGoing() {
        return isGoing;
    }

    public void setGoing(boolean going) {
        isGoing = going;
    }

    public Direction getDir() {
        return dir;
    }

    public int getId() {
        return id;
    }
}
