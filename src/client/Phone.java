package client;

import org.json.JSONObject;

public final class Phone extends Device {

    private int x, y;

    public Phone() {
        super("Jamie");
        this.x = 0;
        this.y = 0;
    }

    @Override
    protected void performTask() {
        System.out.println("x + y = " + (this.x + this.y));
    }

    @Override
    protected boolean update(JSONObject json) {
        int newX, newY;
        try {
            newX = (Integer) json.get("x");
            newY = (Integer) json.get("y");
            this.x = newX;
            this.y = newY;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String [] args) {
        Device phone = new Phone("69 LOL", "0.1.5");
        // Connect to the server
        phone.connectDevice();
        // Apply for a ticket
        phone.applyForTicket();
        if(phone.isUpdateAvailable() && phone.getUpdateFile()) {
            // implement bspatch so we can install the update patch file
            phone.applyPatch();
        }
    }
}
