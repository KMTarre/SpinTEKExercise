package ee.exercise.paydayInfo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PaydayInfoFinder {

    private static final int PAYDAY_DATE = 10;

    private final List<LocalDate> holidays = new ArrayList<>();

    private final LocalDate defaultPaydayDate;
    private final LocalDate realPaydayDate;
    private final LocalDate reminderDate;

    public PaydayInfoFinder(int year, Month month) {

        setHolidays(year);

        defaultPaydayDate = LocalDate.of(year, month, PAYDAY_DATE);
        realPaydayDate = findRealPaydayDate();
        reminderDate = findReminderDate();
    }

    /**
     * Set all holidays except pentecost and Easter sunday of the given year into the holidays list.
     *
     * Don't need to include pentecost and Easter sunday because they are always on sunday.
     * That then doesn't affect the dateIsWorkday check.
     * @param year - year of the holidays.
     */
    private void setHolidays(int year) {
        LocalDate newYear = LocalDate.of(year, Month.JANUARY, 1);
        LocalDate independenceDay = LocalDate.of(year, Month.FEBRUARY, 24);
        LocalDate goodFriday = calculateGoodFridayDate(year);
        LocalDate laborDay = LocalDate.of(year, Month.MAY, 1);
        LocalDate victoryDay = LocalDate.of(year, Month.JUNE, 23);
        LocalDate midsummerDay = LocalDate.of(year, Month.JUNE, 23);
        LocalDate independenceRestorationDay = LocalDate.of(year, Month.AUGUST, 20);
        LocalDate christmasEve = LocalDate.of(year, Month.DECEMBER, 24);
        LocalDate christmasDay = LocalDate.of(year, Month.DECEMBER, 25);
        LocalDate boxingDay = LocalDate.of(year, Month.DECEMBER, 26);

        holidays.addAll(Arrays.asList(
                newYear, independenceDay, goodFriday, laborDay, victoryDay, midsummerDay,
                independenceRestorationDay, christmasEve, christmasDay, boxingDay));
    }

    /**
     * Find the real payday date and return it.
     *
     * Real payday date must be a workday (not weekend or holiday).
     */
    private LocalDate findRealPaydayDate() {
        LocalDate currentDateChecked = defaultPaydayDate;

        while (!dateIsWorkday(currentDateChecked)) {
            currentDateChecked = currentDateChecked.minusDays(1);
        }
        return currentDateChecked;
    }

    /**
     * Find the reminder date and set it as reminderDate value.
     *
     * Reminder date must be a workday 3 workdays before the real payday date.
     * @return LocalDate of the reminder found.
     */
    private LocalDate findReminderDate() {
        LocalDate currentDateChecked = realPaydayDate;
        int workdaysFromPayday = 0;

        while (workdaysFromPayday < 3) {
            currentDateChecked = currentDateChecked.minusDays(1);
            if (dateIsWorkday(currentDateChecked)) {
                workdaysFromPayday++;
            }
        }
        return currentDateChecked;
    }

    /**
     * Check if date is a workday by checking it is not weekend or any holiday.
     * @param date - date to be checked.
     * @return if given date is a workday or not.
     */
    private boolean dateIsWorkday(LocalDate date) {
        if (!(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)) {
            for (LocalDate holiday : holidays) {
                if (holiday.isEqual(date)) {
                    return false;  // Weekday but on holiday.
                }
            }
            return true;  // Date is not on the weekend or holiday.
        } else {
            return false;  // Date is on the weekend.
        }
    }

    /**
     * Calculate good friday date for the given year
     * @param year - given year.
     * @return LocalDate of good friday date.
     */
    private static LocalDate calculateGoodFridayDate(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int i = c / 4;
        int k = c % 4;
        int g = (8 * b + 13) / 25;
        int h = ((19 * a) + b - d - g + 15) % 30;
        int l = ((2 * e) + (2 * i) - k + 32 - h) % 7;
        int m = (a + (11*h) + (19*l)) / 433;
        int days_to_good_friday = h + l - (7 * m) - 2;
        int month = (days_to_good_friday + 90) / 25;
        int day = (days_to_good_friday + (33 * month) + 19) % 32;

        return LocalDate.of(year, month,day);
    }

    public LocalDate getRealPaydayDate() {
        return realPaydayDate;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }
}
