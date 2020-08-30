package lesson_5;

import java.util.concurrent.BrokenBarrierException;

public class RaceDemo {

    public static final int CARS_COUNT = 10;
    public static final int TUNNEL_CAPACITY = 3;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(
                new Road(60, "1. Mulholland drive"),
                new Tunnel(80, TUNNEL_CAPACITY, "2. El tunel al infierno"),
                new Road(40, "3. Дорога в небо"));
        race.prepare(CARS_COUNT);
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        race.getRacersReadyCounter().await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        race.getRaceStartedCounter().await();
        race.getRaceFinishedCounter().await();
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");

        if (race.thereIsWinner()) {
            System.out.println("Y el ganador es ...");
            Thread.sleep(1500);
            System.out.println("3");
            Thread.sleep(1500);
            System.out.println("2");
            Thread.sleep(1500);
            System.out.println("1");
            Thread.sleep(3000);
            System.out.println("Bow before the " + race.getWinner());
        }
    }
}