package deco2800.thomas.entities;

public class Currency {
    protected int currency;

    public Currency(int value){
        this.currency = value;
    }

    public int getCurrencyAmount(){
        return this.currency;
    }

    public void setCurrency(int value){
        this.currency = value;
    }

    public void removeCurrency(int value){
        this.currency -= value;
    }

    public void addCurrency(int value){
        this.currency += value;
    }
}
