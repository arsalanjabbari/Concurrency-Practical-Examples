import java.util.concurrent.*;


class Main {
    static Semaphore barber;
    static Semaphore customer;
    static Semaphore accessSeats;
    static int seats = 2;
    static int N = 5;
    // accessSeats: only one sit / get up at once
    // seats: no. of seats in the waiting room
    // N: no .of customers

    // The barber is providing 24x7 service:
    // 1. Sleeps until a customer wakes him up
    // 2. He picks first customer in room (b.release)
    // 3. One seat is freed up (seats++)
    // 4. Cuts customer's hair (sleep 1s)
    // ... Goes back to sleep
    static void barber() {
        new Thread(() -> {
            try {
                while(true) {
                    log("B: sleeping");
                    customer.acquire();
                    log("B: got customer");
                    accessSeats.acquire();
                    barber.release();
                    seats++;
                    accessSeats.release();
                    log("B: cutting hair");
                    Thread.sleep(1000);
                    log("B: cutting done");
                }
            }
            catch(InterruptedException ignored) {}
        }).start();
    }

    // Each customer requires a haircut:
    // 1. Occupies a seat, if available
    // 2. Wakes up the barber (c.release)
    // 3. Waits for barber to indicate his turn
    // ... His hair is cut.
    static void customer(int i) {
        new Thread(() -> {
            try {
                log(i+": checking seats");
                accessSeats.acquire();
                if(seats<=0) {
                    log(i+": no seats!");
                    accessSeats.release();
                    return;
                }
                seats--;
                customer.release();
                accessSeats.release();
                log(i+": sat, seats="+seats);
                barber.acquire();
                log(i+": having hair cut");
            }
            catch(InterruptedException ignored) {}
        }).start();
    }

    // 1. Barber is sleeping
    // 2. There are no customers
    // 3. No one is accessing seats
    // 4. Barber is started (sleeping)
    // 5. After random intervals, customers arrive
    public static void main(String[] args) {
        log("Starting barber (B) with "+
                seats+" seats"+" and "+N+" customers ...");
        barber = new Semaphore(0);
        customer = new Semaphore(0);
        accessSeats = new Semaphore(1);
        barber();
        for(int i=0; i<N; i++) {
            sleep(1000 * Math.random());
            customer(i);
        }
    }

    static void sleep(double t) {
        try { Thread.sleep((long)t); }
        catch (InterruptedException ignored) {}
    }

    static void log(String x) {
        System.out.println(x);
    }
}