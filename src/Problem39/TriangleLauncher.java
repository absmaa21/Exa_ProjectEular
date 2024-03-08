package Problem39;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TriangleLauncher {
    private void runEuler39() {
        ExecutorService pool = Executors.newFixedThreadPool(6, r -> {
            Thread t = new Thread(r);
            t.setPriority(Thread.MAX_PRIORITY);
            return t;
        });
        List<TriangleWorker> workers = new ArrayList<>();

        for(int i = 10; i <= 1000; i++) {
            workers.add(new TriangleWorker(i));
        }

        Set<Triangle> largestTriangles = new HashSet<>();

        try {
            List<Future<Set<Triangle>>> future = pool.invokeAll(workers);
            for(Future<Set<Triangle>> triangles : future) {
                Set<Triangle> t = triangles.get();
                if(t.size() < largestTriangles.size()) continue;
                largestTriangles = t;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        int perimeter = largestTriangles.stream().toList().get(0).getP();
        //System.out.printf("Perimeter: " + perimeter + " Size: " + largestTriangles.size() + "\n");
    }

    public static void main(String[] args) {
        int n = 1000;
        double sum = 0;

        for (int i = 0; i < n; i++) {
            double start = System.currentTimeMillis();
            new TriangleLauncher().runEuler39();
            double end = System.currentTimeMillis();
            sum += end - start;
        }

        System.out.printf("Execution time: %f milliseconds%n", (sum / n));
    }
}
