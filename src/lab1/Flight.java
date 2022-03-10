package lab1;

public class Flight {
    private int number;
    private String target;
    private int length;
    private int price;
    private Time departure;
    private Time arrival;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Time getDeparture() {
        return departure;
    }

    public void setDeparture(Time departure) {
        this.departure = departure;
    }

    public Time getArrival() {
        return arrival;
    }

    public void setArrival(Time arrival) {
        this.arrival = arrival;
    }

    public int getFlightTime() {
        int result = 0;
        result += arrival.getHours() > departure.getHours()
                ? (arrival.getHours() - departure.getHours()) * 60
                : (24 - Math.abs(arrival.getHours() - departure.getHours())) * 60;
        result += (arrival.getMins() - departure.getMins());
        return result;
    }
}
