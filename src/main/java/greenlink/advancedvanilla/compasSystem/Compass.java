package greenlink.advancedvanilla.compasSystem;

public class Compass {
    private int destinationX;
    private int destinationZ;
    private String world;

    public Compass(int destinationX, int destinationZ, String world) {
        this.destinationX = destinationX;
        this.destinationZ = destinationZ;
        this.world = world;
    }

    public Compass(int destinationX, int destinationZ, String world, boolean active) {
        this.destinationX = destinationX;
        this.destinationZ = destinationZ;
        this.world = world;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationZ() {
        return destinationZ;
    }

    public String getWorld() {
        return world;
    }
}
