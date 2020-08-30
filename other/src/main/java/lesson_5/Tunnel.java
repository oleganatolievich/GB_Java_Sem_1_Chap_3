package lesson_5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private Semaphore tunnelController;

    public Tunnel(int length, int tunnelCapacity, String description) {
        this.length = length;
        this.description = description;
        this.tunnelController = new Semaphore(tunnelCapacity);
    }

    public Tunnel(int length, int tunnelCapacity) {
        this(length, tunnelCapacity, "Тоннель " + length + " метров");
    }

    @Override
    public void go(Car c) {
        boolean gotPermission = false;
        try {
            try {
                System.out.println(c.getName() + " хочет проехать: " + description);
                while (!tunnelController.tryAcquire()) {
                    System.out.println(c.getName() + " ждет разрешения проехать: " + description);
                    Thread.sleep(1000);
                }
                gotPermission = true;
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (gotPermission) tunnelController.release();
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}