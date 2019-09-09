package ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


public class Menu {

    private String name;
    private String description;
    private double deliveryValue;
    private String category;
    private LocalDate validityDate;
    private LocalDate validateEnd;
    //Turnos/Horarios de entrega/Envio
    private String turn;
    private String deliveryTime;
    private String averageTime;
    private double price;
    private double cantMin = 1;
    private double priceCantMin = 0;
    private double cantMinTwo = 2;
    private double priceCantMinTwo = 0;
    private double priceCantMax;
    private double cantMaxPeerDay= 0;


    public Menu() { }

    public Menu(String name, String description, double deliveryValue, String category, LocalDate validityDate, LocalDate validateEnd, String turn,
                String deliveryTime, String averageTime, double price, double cantMin, double priceCantMin, double cantMinTwo, double priceCantMinTwo,
                double priceCantMax, double cantMaxPeerDay) {
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

    public double getDeliveryValue() {
        return deliveryValue;
    }

    public String getCategory() {
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

    public double getPrice() {
        return price;
    }

    public double getCantMin() {
        return cantMin;
    }

    public double getPriceCantMin() {
        return priceCantMin;
    }

    public double getCantMinTwo() {
        return cantMinTwo;
    }

    public double getPriceCantMinTwo() {
        return priceCantMinTwo;
    }

    public double getPriceCantMax() {
        return priceCantMax;
    }

    public double getCantMaxPeerDay() {
        return cantMaxPeerDay;
    }
}
