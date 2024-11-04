package Space;

public class GeneralSatellite extends Spacecraft {
    private double altitude;
    private double dataTransmitted;
    private double maxFuel;
    
    public GeneralSatellite(double maxFuel) {
        super((int)maxFuel); 
        this.altitude = 0;
        this.maxFuel = maxFuel;
        this.dataTransmitted = 0;
    }
    
    public GeneralSatellite(double maxFuel, int communicationRange) {
        this(maxFuel);
    }
    
    @Override
    public void launch() throws InsufficientFuelException {
        if (fuel < 500) {
            throw new InsufficientFuelException("Not enough fuel for launch");
        }
        fuel -= 500;
        altitude += 1000;
        System.out.println("Satellite launching at altitude: " + altitude);
    }
    
    @Override
    public void land() throws InsufficientFuelException {
        if (altitude == 0) {
            throw new IllegalStateException("Satellite is already landed!");
        }
        if (fuel < 300) {
            throw new InsufficientFuelException("Not enough fuel for landing");
        }
        fuel -= 300;
        altitude = 0;
        System.out.println("Satellite landed successfully");
    }
    
    @Override
    public void refuel(int amount) {
        super.refuel(amount);  // Use parent's validation logic
    }
    
    public void transmitData() {
        if (altitude == 0) {
            throw new IllegalStateException("Cannot transmit data while landed!");
        }
        dataTransmitted += Math.random() * 50 + 10;
        System.out.println("Transmitting data... Current total: " + dataTransmitted);
    }
    
    public void sendSignal() {
        if (altitude == 0) {
            throw new IllegalStateException("Cannot send signals while landed!");
        }
    }
    
    // Getters
    public double getAltitude() { return altitude; }
    public double getCurrentFuel() { return fuel; }
    public double getMaxFuel() { return maxFuel; }
    public double getDataTransmitted() { return dataTransmitted; }
}