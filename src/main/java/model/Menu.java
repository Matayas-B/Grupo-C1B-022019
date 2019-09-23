package model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public class Menu {

    private String name;
    private String description;
    private int deliveryValue;
    private Category category;
    private LocalDate validityDate;
    private LocalDate validateEnd;
    //Turnos/Horarios de entrega/Envio
    private String turn;
    private String deliveryTime;
    private String averageTime;
    private int price;
    private int cantMin = 1;
    private int priceCantMin = 0;
    private int cantMinTwo = 2;
    private int priceCantMinTwo = 0;
    private int priceCantMax;
    private int cantMaxPeerDay= 0;


    public Menu() { }

    public Menu(String name, String description, int deliveryValue, Category category, LocalDate validityDate, LocalDate validateEnd, String turn,
                String deliveryTime, String averageTime, int price, int cantMin, int priceCantMin, int cantMinTwo, int priceCantMinTwo,
                int priceCantMax, int cantMaxPeerDay) {
        this.name = name;
        this.description = description;
        this.deliveryValue = deliveryValue;
        this.category = category;
        this.validityDate = validityDate;
        this.validateEnd = validateEnd;
        this.turn = turn;
        this.deliveryTime = deliveryTime;
        this.averageTime = averageTime;
        this.price = price;
        this.cantMin = cantMin;
        this.priceCantMin = priceCantMin;
        this.cantMinTwo = cantMinTwo;
        this.priceCantMinTwo = priceCantMinTwo;
        this.priceCantMax = priceCantMax;
        this.cantMaxPeerDay = cantMaxPeerDay;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDeliveryValue() {
        return deliveryValue;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public LocalDate getValidateEnd() {
        return validateEnd;
    }

    public String getTurn() {
        return turn;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public int getPrice() {
        return price;
    }

    public int getCantMin() {
        return cantMin;
    }

    public int getPriceCantMin() {
        return priceCantMin;
    }

    public int getCantMinTwo() {
        return cantMinTwo;
    }

    public int getPriceCantMinTwo() {
        return priceCantMinTwo;
    }

    public int getPriceCantMax() {
        return priceCantMax;
    }

    public int getCantMaxPeerDay() {
        return cantMaxPeerDay;
    }


}
