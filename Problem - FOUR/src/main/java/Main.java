import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        // Randomize rideCapacity, numberOfPassengers, and maxRideCount
        int rideCapacity = random.nextInt(10) + 1; // Random capacity between 1 and 10
        int numberOfPassengers = random.nextInt(20) + 1; // Random number of passengers between 1 and 20
        int maxRideCount = random.nextInt(10) + 1; // Random max ride count between 1 and 10

        RollerCoaster rollerCoaster = new RollerCoaster();

        // Create passengers with random IDs
        for (int passengerId = 1; passengerId <= numberOfPassengers; passengerId++) {
            Passenger passenger = new Passenger(passengerId, rollerCoaster, rideCapacity);
            passenger.start();
        }

        // Start a random number of rides
        for (int rideNumber = 1; rideNumber <= maxRideCount; rideNumber++) {
            rollerCoaster.startRide(rideCapacity);
        }
    }
}
