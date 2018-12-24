package edu.spbstu.taxi.entity;

public class CostCalculation {
    private float routeLength;
    private float waitingTime;
    private float totalCost;

    public CostCalculation(float waitingTime, float routeLength){
        this.waitingTime = waitingTime;
        this.routeLength = routeLength;
        setTotalCost();
    }
    public boolean setRouteLength(float routeLength) {
        if(routeLength > 0) {
            this.routeLength = routeLength;
            return true;
        }
        return false;
    }

    public boolean setWaitingTime(float waitingTime) {
        if (waitingTime > 0) {
            this.waitingTime = waitingTime;
            return true;
        }
        return false;
    }

    public void setTotalCost() {
        this.totalCost = routeLength * Tariff.costPerKm + waitingTime * Tariff.costPerMin;
    }

    public float getRouteLength() {
        return routeLength;
    }

    public float getWaitingTime() {
        return waitingTime;
    }

    public float getTotalCost() {
        return totalCost;
    }
}
