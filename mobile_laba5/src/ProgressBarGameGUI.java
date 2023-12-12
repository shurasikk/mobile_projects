import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarGameGUI {
    private AtomicInteger progress1 = new AtomicInteger(0);
    private AtomicInteger progress2 = new AtomicInteger(0);
    private AtomicInteger progress3 = new AtomicInteger(0);

    private JProgressBar progressBar1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar3;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProgressBarGameGUI().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("ProgressBar Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        progressBar1 = new JProgressBar(0, 100);
        progressBar2 = new JProgressBar(0, 100);
        progressBar3 = new JProgressBar(0, 100);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(progressBar1);
        panel.add(progressBar2);
        panel.add(progressBar3);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        startGame();
    }

    private void startGame() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Participant("Participant 1", progress1, progressBar1));
        executorService.submit(new Participant("Participant 2", progress2, progressBar2));
        executorService.submit(new Participant("Participant 3", progress3, progressBar3));

        executorService.shutdown();
    }
}