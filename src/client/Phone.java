package client;

import org.json.JSONObject;

public final class Phone extends Device {

    private int x, y;

    public Phone() {
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
        Device phone = new Phone();
        
        phone.startConnection();
        phone.handleConnection();
    }
}
