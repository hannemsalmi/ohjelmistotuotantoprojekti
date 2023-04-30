package model;

import java.util.List;

/**
 * ListModel is a class that represents a model for storing shopping and
 * reminder lists. This class contains getter and setter methods for
 * accessing and modifying the lists.
 * @authors hannemsalmi, willeKoodaus, Katanpe, MinaSofi
 */
public class ListModel {
    private List<String> shoppingList;
    private List<String> reminderList;

    /**
     * Retrieves the shopping list.
     *
     * @return the shopping list, or null if it hasn't been set
     */
    public List<String> getShoppingList() {
        return shoppingList;
    }

    /**
     * Sets the shopping list.
     *
     * @param shoppingList the list of shopping items to be stored
     */
    public void setShoppingList(List<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    /**
     * Retrieves the reminder list.
     *
     * @return the reminder list, or null if it hasn't been set
     */
    public List<String> getReminderList() {
        return reminderList;
    }

    /**
     * Sets the reminder list.
     *
     * @param reminderList the list of reminder items to be stored
     */
    public void setReminderList(List<String> reminderList) {
        this.reminderList = reminderList;
    }
}
