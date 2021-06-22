package demidova;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT = 0;
    private static boolean isWin;
    private Race race;
    private int speed;
    private String name;
    private int count;
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch latch;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier, CountDownLatch latch) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cyclicBarrier;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
            cyclicBarrier.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            checkWinner(this);
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized void checkWinner(Car c) {
        if (!isWin) {
            System.out.println(c.name + " -----ПОБЕДИЛ!!!");
            isWin = true;
        }
    }
}
