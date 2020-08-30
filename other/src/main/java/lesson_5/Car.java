package lesson_5;

import java.util.ArrayList;

public class Car implements Runnable {

    private static int CARS_COUNT;

    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            race.getRacersReadyCounter().await();
            race.getRaceStartedCounter().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Stage> stages = race.getStages();
        for (int i = 0; i < stages.size(); i++) {
            stages.get(i).go(this);
        }
        race.declareVictoryIfWon(this);
        race.getRaceFinishedCounter().countDown();
    }

    @Override
    public String toString() {
        return String.format("%s, the speed is: %d km/h", getName(), getSpeed());
    }

}