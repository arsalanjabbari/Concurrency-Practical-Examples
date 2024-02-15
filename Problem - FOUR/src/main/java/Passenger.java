import java.util.Random;

public class Passenger extends Thread {
    private final int passengerId;
    private final RollerCoaster rollerCoaster;
    private final int coasterCapacity;

    public Passenger(int passengerId, RollerCoaster rollerCoaster, int coasterCapacity) {
        this.passengerId = passengerId;
        this.rollerCoaster = rollerCoaster;
        this.coasterCapacity = coasterCapacity;
    }

    @Override
    public void run() {
        while (true) {
            rollerCoaster.board(passengerId, coasterCapacity);
            simulateRide();
        }
    }

    private void simulateRide() {
        try {
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}