import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class CreateDataset {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Count: ");
        int count = scanner.nextInt();
        System.out.print("MaxCost: ");
        int maxCost = scanner.nextInt();
        System.out.print("MaxValue: ");
        int maxValue = scanner.nextInt();

        String fileName = "./datasets/m" + count + "w" + maxCost;

        BufferedWriter bWriter = new BufferedWriter(new FileWriter(fileName));
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            bWriter.write(random.nextInt(maxCost) + " " + random.nextInt(maxValue) + "\n");
        }
        bWriter.close();
    }
}
