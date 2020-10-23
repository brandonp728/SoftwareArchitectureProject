public class TicketManager {

     public static void main(String[] args) {

    /**
     * Pseudo code
     * 
     * This is the sever for the program.
     * Must connect to tcp sockets.
     * 
     * Manager notifies tickets that a patch is available.
     * Devices apply for a ticket from the manager.
     * Manager holds devices in a queue.
     * When manager decides another device may update it pops off the queue and is sent a notification.
     * Manger polls the activly updating devices for their status. (this is for fault detection as well)
     * Once the status of the updating device is completed a new device may be queue.
     * 
     * status code
     *      Server -> Client
     *      waiting permission
     *      denied permision
     *      approved permission
     * 
     *      new patch available
     *      roll back
     * 
     *      Client -> Server
     *      inprogress (updating)
     *      successful update
     *      failed update
     */

     }
}
