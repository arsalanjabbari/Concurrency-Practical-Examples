class Agent extends Thread {
    private final Table table;

    public Agent(Table newTable) {
        this.table = newTable;
    }

    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(2000);
            } catch (Exception ignored) {}
            table.getRandom();
            // this tells the smoker threads to look at the table
            // this prints the greeting message and what items the monitor has
            System.out.println("\nAGENT:: Hey, Do You Want to Smoke ...\nWell The Problem is I Only Have " + table.getMonitorItems());
            // pause the agent while one Smoker thread is running
            pause();
        }
    }

    public synchronized void wake() {
        try {
            notify();
        } catch(Exception ignored){}
    }


    public synchronized void pause() {
        try {
            this.wait();
        } catch (Exception ignored) {}
    }
}