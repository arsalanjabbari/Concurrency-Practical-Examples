import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unchecked")
// i put the supress warning in because of the converting between glases in the array
class Table {

    //Variables for storing ITEMSs(Weed,Blunt,Lighter)
    private ArrayList allItems  = new ArrayList<>();
    private ArrayList MonitorItems = new ArrayList<>();

    public Table()
    {
        //adds items
        allItems.add("Tobacco");
        allItems.add("Paper");
        allItems.add("Matches");
    }

    //gets two random item from the list
    public void getRandom()
    {
        Random random = new Random();
        // clears monitor array
        MonitorItems.clear();
        //copies what in the arry list to another arra

        ArrayList copyAllElements = (ArrayList) allItems .clone();
        //picks an item and adds it to the monitors items
        int ITEMS1 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS1));

        copyAllElements.remove(ITEMS1);
        //picks an item and adds it to the monitors items
        int ITEMS2 = random.nextInt(copyAllElements.size());
        MonitorItems.add(copyAllElements.get(ITEMS2));
    }
    //to check if the table is empty so you dont get any erros with arrays
    public boolean isEmpty()
    {
        return (MonitorItems.size() == 0);
    }
    //gets the items and notifies the other threads
    public synchronized String getMonitorItems()
    {
        notifyAll();
        return MonitorItems.toString();
    }
    //this gets the item and pairs it with the correct thread
    public synchronized String getSmokerItems(int x)
    {
        try {
            this.wait();
        } catch (Exception ignored) {}
        return (String) allItems .get(x);
    }
    //checking if the smoker has the same items as the monitor
    public boolean hasIngredient(String ITEMSName)
    {
        return (MonitorItems.contains(ITEMSName));
    }

    public synchronized void pause()
    {
        try {
            this.wait();
        } catch (Exception ignored) {}
    }
}