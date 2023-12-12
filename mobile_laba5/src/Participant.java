import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

class Participant implements Runnable {
    private String name;
    private AtomicInteger progress;
    private JProgressBar progressBar;

    public Participant(String name, AtomicInteger progress, JProgressBar progressBar) {
        this.name = name;
        this.progress = progress;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        while (progress.get() < 100) {
            int increment = (int) (Math.random() * 10) + 1;
            progress.addAndGet(increment);

            SwingUtilities.invokeLater(() -> progressBar.setValue(progress.get()));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(name + " has finished!");
    }
}