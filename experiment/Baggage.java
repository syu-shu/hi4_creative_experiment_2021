import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class Baggage {
    List<Integer> costs;
    List<Integer> values;

    public Baggage (String fileName) throws Exception {
        costs = new ArrayList<>();
        values = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(fileInputStream);
        for (int i = 0; scanner.hasNext(); i++) {
            costs.add(scanner.nextInt());
            values.add(scanner.nextInt());
        }
    }

    public Baggage (int baggageCount, int maxCost, int maxValue) {
        Random random = new Random();
        costs = new ArrayList<>();
        values = new ArrayList<>();
        for (int i = 0; i < baggageCount; i++) {
            costs.add(random.nextInt(maxCost) + 1);
            values.add(random.nextInt(maxValue) + 1);
        }
    }

    public int getSize() {
        return costs.size();
    }
}
