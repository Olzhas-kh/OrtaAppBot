package com.example.OrtaApp;

public class IftarInfo {

    String nameOfIftar = "";
    String amountOfPeople = "";
    String pricePerPerson = "";
    String forBoys = "";
    String place = "";
    String description = "";

    public IftarInfo(String nameOfIftarUser,
            String amountOfPeopleUser,
            String pricePerPersonUser,
            String forBoysUser,
            String placeUser,
            String descriptionUser) {

        nameOfIftar = nameOfIftarUser;
        amountOfPeople = amountOfPeopleUser;
        pricePerPerson = pricePerPersonUser;
        forBoys = forBoysUser;
        place = placeUser;
        description = descriptionUser;

    }

    public String getNameOfIftar() {
        return nameOfIftar;
    }

    public String getAmountOfPeople() {
        return amountOfPeople;
    }

    public String getPricePerPerson() {
        return pricePerPerson;
    }

    public String isForBoys() {
        return forBoys;
    }

    public String getPlace() {
        return place;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "IftarInfo [nameOfIftar=" + nameOfIftar + ", amountOfPeople=" + amountOfPeople + ", pricePerPerson="
                + pricePerPerson + ", forBoys=" + forBoys + ", place=" + place + ", description=" + description + "]";
    }

}
