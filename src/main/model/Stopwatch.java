package model;

public class Stopwatch {

    // Stopwatch class contains start, end and elapsed time. They are used to calculate the elapsed on since
    // the application started. Later on, elapsed time will be used to implement real animal.
    // In other words, pets condition and action will change as time goes.


    private long startTime;
    private long endTime;
    private long elapsedTime;
    private long tickTime;

    public Stopwatch() {
        this.startTime = 0;
        this.endTime = 0;
        this.elapsedTime = 0;
        this.tickTime = 0;
    }

    //MODIFIES: this
    //EFFECTS: start the timer by recording current elapsed time from 1970 1/1 in milliseconds.
    public void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void endTimer() {
        this.endTime = System.currentTimeMillis();
    }


    //MODIFIES: this
    //EFFECTS: return the elapsed time in milliseconds from the start to now.
    public long elapsedTime() {
        this.elapsedTime = System.currentTimeMillis() - this.startTime;
        return this.elapsedTime;

    }

    //MODIFIES: this
    //EFFECTS: check if the game needs to update base on tick rate.
    //         In another word, if tick rate is 2, then the game updates every 2 seconds
    public boolean isTick(int tickRate) {
        if (elapsedTime() >= this.tickTime + tickRate * 1000) {
            this.tickTime = this.elapsedTime();
            return true;
        }
        return false;
    }
}
