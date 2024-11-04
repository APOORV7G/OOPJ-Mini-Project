package Space;

public abstract class Spacecraft {
    protected int fuel;
    
    public Spacecraft(int fuel) {
        this.fuel = fuel;
    }
    
    public abstract void launch() throws InsufficientFuelException;
    
    public void land() throws InsufficientFuelException {
        if (fuel < 50) {
            throw new InsufficientFuelException("Not enough fuel to land safely!");
        }
        fuel -= 50;
        System.out.println("Spacecraft is landing.");
    }
    
    public void refuel(int fuelAmount) {
        if (fuelAmount < 0) {
            throw new IllegalArgumentException("Fuel amount cannot be negative");
        }
        if(fuelAmount+fuel>10000){
            throw new IllegalArgumentException("Fuel amount cannot be more than 10000");
        }
        fuel += fuelAmount;
        System.out.println("Spacecraft refueled. Current fuel: " + fuel);
    }
}