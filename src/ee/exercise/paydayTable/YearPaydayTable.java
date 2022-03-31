package ee.exercise.paydayTable;

import ee.exercise.paydayInfo.PaydayInfoFinder;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class YearPaydayTable {

    /**
     * Generate a table of payday reminder dates and payday dates.
     *
     * Every table row is a list in the form of (month, reminder date, payday date).
     * @param year - given year.
     * @return list of lists where inner lists are table rows.
     */
    public static List<List<String>> generatePaydayDatesTable(int year) {
        List<List<String>> table = new ArrayList<>();
        table.add(List.of("Month", "Reminder date", "Payday date"));  // Add table header.

        for (Month month : Month.values()) {
            PaydayInfoFinder paydayInfoFinder = new PaydayInfoFinder(year, month);
            List<String> tableRow = new ArrayList<>(3);

            // Month name;
            tableRow.add(month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH));

            // Reminder date.
            tableRow.add(paydayInfoFinder.getReminderDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            // Payday date.
            tableRow.add(paydayInfoFinder.getRealPaydayDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            table.add(tableRow);
        }
        return table;
    }
}
