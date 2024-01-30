import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RollerCoaster {
    private final Lock lock = new ReentrantLock();
    private final Condition carFullCondition = lock.newCondition();
    private final Condition carEmptyCondition = lock.newCondition();

    private int passengersOnBoard = 0;

    public void startRide(int coasterCapacity) {
        lock.lock();
        try {
            while (passengersOnBoard < coasterCapacity) {
                carFullCondition.await();
            }

            System.out.println("Roller coaster is riding with " + coasterCapacity + " passengers.");
            simulateRide();

            passengersOnBoard = 0;
            System.out.println("Ride finished. Boarding starts again.");
            carEmptyCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void board(int passengerId, int coasterCapacity) {
        lock.lock();
        try {
            while (passengersOnBoard == coasterCapacity) {
                carEmptyCondition.await();
            }

            System.out.println("Passenger " + passengerId + " boards the roller coaster.");

            passengersOnBoard++;

            if (passengersOnBoard == coasterCapacity) {
                carFullCondition.signal();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    private void simulateRide() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}