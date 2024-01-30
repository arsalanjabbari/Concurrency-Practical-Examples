class Smokers extends Thread {

    //initializes all the variables
    private Table table;
    private final int itemNumber;
    private final Agent monitor;

    public Smokers(Table newTable, int newITEM, String NAME, Agent newMonitor) {
        //initializing all the variables
        itemNumber = newITEM;
        this.table = newTable;
        setName(NAME);
        this.monitor = newMonitor;
    }

    @Override
    public void run()
    {
//while true, basically run this script forever
        while(true)
        {
            //gets the item from the table and puts it into items
            String items = table.getSmokerItems(itemNumber);
            // if the table doesn't have an ingredient and the table is not empty
            if (!table.hasIngredient(items) && !table.isEmpty())
            {
                // print out the thread with the missing item
                System.out.println("-----------------------------------");
                System.out.println("Hey I am " + getName() + " and i have the " + items + " your missing.\n");
                System.out.println("-----------------------------------");
                try {
                    //tell the thread to smoke
                    smoke();
                    // prints that the thread is going to let another read do the same operation
                    System.out.println(getName() + " is going to let someone else smoke.");

                    // the thread tells the monitor to continue
                    monitor.wake();
                } catch (Exception ignored) {}
            }
        }
    }

    public synchronized void smoke() throws Exception
    {
//this prints what each thread is doing when it matches up witht hte monitor
        System.out.println(getName() + " rolls up.");
        Thread.sleep(100);
        System.out.println(getName() + " starts blowing smoke clouds");
        Thread.sleep(100);

    }

}