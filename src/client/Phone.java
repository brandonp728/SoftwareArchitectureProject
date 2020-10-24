package client;

public final class Phone extends Device {

    @Override
    protected void performTask() {

    }

    @Override
    protected boolean update() {
        return false;
    }

    public static void main(String [] args) {
        Device phone = new Phone();
        phone.startConnection();
        phone.handleConnection();
    }
}
