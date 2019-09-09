package ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.model;

import ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.execption.CurrencyMenuException;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Provider {

    private String name;
    private String logo;
    private String localidad;
    private String addresMaps;
    private String description;
    private String webSite;
    private String eMail;
    private String phone;
    private String Disponibility;
    private String distanceDelivery;

    private ArrayList<Menu> menus = new ArrayList<Menu>();

    public Provider(String name, String logo, String localidad, String addresMaps, String description, String webSite,
                    String eMail, String phone, String disponibility, String distanceDelivery) {
        this.name = name;
        this.logo = logo;
        this.localidad = localidad;
        this.addresMaps = addresMaps;
        this.description = description;
        this.webSite = webSite;
        this.eMail = eMail;
        this.phone = phone;
        Disponibility = disponibility;
        this.distanceDelivery = distanceDelivery;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getAddresMaps() {
        return addresMaps;
    }

    public String getDescription() {
        return description;
    }

    public String getWebSite() {
        return webSite;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public String getDisponibility() {
        return Disponibility;
    }

    public String getDistanceDelivery() {
        return distanceDelivery;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }



    public void addMenu(Menu menu){

        if(getCantCurrentMenues() >= 20){
            throw new CurrencyMenuException("Superaste el maximo de menues vigentes") ;
        }else {
            menus.add(menu);
        }
    }

    private int getCantCurrentMenues() {

            int count = 0;
            for( Menu i : this.menus){

                if(i.getValidateEnd().isAfter(LocalDate.now())){
                    count++;
                }
            }
            return count;
    }
}
