package deco2800.thomas.entities;

public interface Tradeable {
    /**
     * NPCs that can trade with players ie. sell player items should implement
     * Tradeable interface. Allows various merchant NPCs to implement and use
     * their common methods to manipulate currency.
     */

    void getCurrencyValue();

    void setCurrency();

    void removeCurrency();

    void addCurrency();
}
