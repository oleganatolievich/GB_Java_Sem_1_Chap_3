package lesson_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Race {
    private ArrayList<Stage> stages;
    private CyclicBarrier racersReadyCounter;
    private CyclicBarrier raceStartCounter;
    private CountDownLatch raceFinishedCounter;
    private AtomicBoolean thereIsWinner;
    private Car winner;

    public Race(Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        thereIsWinner = new AtomicBoolean(false);
    }

    public ArrayList<Stage> getStages() {
        return stages;
    }

    public CyclicBarrier getRacersReadyCounter() {
        return racersReadyCounter;
    }

    public CyclicBarrier getRaceStartedCounter() {
        return raceStartCounter;
    }

    public CountDownLatch getRaceFinishedCounter() {
        return raceFinishedCounter;
    }

    public Car getWinner() {
        return winner;
    }

    public void prepare(int racersAmount) {
        this.racersReadyCounter = new CyclicBarrier(racersAmount + 1);
        this.raceStartCounter = new CyclicBarrier(racersAmount + 1);
        this.raceFinishedCounter = new CountDownLatch(racersAmount);
    }

    public void declareVictoryIfWon(Car winner) {
        if (!thereIsWinner.getAndSet(true)) {
            this.winner = winner;
        }
    }

    public boolean thereIsWinner() {
        return thereIsWinner.get();
    }

}