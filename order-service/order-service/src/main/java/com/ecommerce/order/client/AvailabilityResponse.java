package com.ecommerce.order.client;

public class AvailabilityResponse{

    private boolean available;

    public AvailabilityResponse(){}

    public AvailabilityResponse(boolean available){
        this.available=available;
    }

    public void setAvailable(boolean available){
        this.available=available;
    }

    public boolean isAvailable(){
        return available;
    }
}
