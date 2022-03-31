package ee.exercise.userApp;

import ee.exercise.paydayTable.YearPaydayTable;

import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.stream.Collectors;

public class UserApp {

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.print("Insert year you want the table for: ");
            int year;
            try {
                year = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }

            String paydayTable = YearPaydayTable.generatePaydayDatesTable(year).stream()
                    .map(tableRow -> String.join(",", tableRow))
                    .collect(Collectors.joining("\n"));

            System.out.println(paydayTable);
            System.out.print("Do you want to save (yes/NO): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                writeTableToFile(paydayTable, year);
            }

            System.out.print("Do you want to get another table (YES/no): ");
            if (scanner.nextLine().equalsIgnoreCase("no")) {
                break;
            }
        }
    }

    /**
     * Write the payday dates table to a file named year.csv.
     * @param table - file content.
     * @param year - the content year.
     */
    private void writeTableToFile(String table, int year) {
        String filename = String.format("%d.csv", year);
        Path path = Paths.get("tables", filename);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(table);
        } catch (IOException e) {
            System.out.println("IOException:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
