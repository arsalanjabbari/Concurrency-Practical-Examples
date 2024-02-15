class SmokingProblemApp {

    public static void main(String[] args) {
        Table table = new Table();
        Agent Monitor = new Agent(table);
        //starts the agent Monitors
        Monitor.start();


        // creates 3 smoker thread from the monitor, so the threads can wake up the monitor
        for (int i = 0; i < 3; i++) {
            Smokers SmokersThread = new Smokers(table, i, "Smoker " + (i + 1), Monitor);
            SmokersThread.start();
        }
    }
}


