package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateCalculationUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Period : 1w 1m 3m 6m 1y 2y 3y
     */

    /**
     * specify date
     */
    public static String calculateByGivenDate(String date, String period) throws ParseException {
        String result = "";
        switch (period){
            case "1w":
                result = subtractOneWeek(date);
                break;
            case "1m":
                result = subtractOneMonth(date);
                break;
            case "3m":
                result = subtractThreeMonths(date);
                break;
            case "6m":
                result = subtractSixMonths(date);
                break;
            case "1y":
                result = subtractOneYear(date);
                break;
            case "2y":
                result = subtractTwoYears(date);
                break;
            case "3y":
                result = subtractThreeYears(date);
                break;
            default:
                new Exception("Period Input Exception.");
        }
        return result;
    }

    /**
     * use current date
     */
    public static String calculateByCurrentDate(String period) throws ParseException {
        String result = "";
        switch (period){
            case "1w":
                result = subtractOneWeek();
                break;
            case "1m":
                result = subtractOneMonth();
                break;
            case "3m":
                result = subtractThreeMonths();
                break;
            case "6m":
                result = subtractSixMonths();
                break;
            case "1y":
                result = subtractOneYear();
                break;
            case "2y":
                result = subtractTwoYears();
                break;
            case "3y":
                result = subtractThreeYears();
                break;
            default:
                new Exception("Period Input Exception.");
        }
        return result;
    }

    public static String subtractOneWeek (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.DATE,-7);
        return format.format(calendar.getTime());
    }

    public static String subtractOneWeek () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-7);
        return format.format(calendar.getTime());
    }

    public static String subtractOneMonth (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.MONTH,-1);
        return format.format(calendar.getTime());
    }

    public static String subtractOneMonth () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-1);
        return format.format(calendar.getTime());
    }

    public static String subtractThreeMonths (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.MONTH,-3);
        return format.format(calendar.getTime());
    }

    public static String subtractThreeMonths () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-3);
        return format.format(calendar.getTime());
    }

    public static String subtractSixMonths (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.MONTH,-6);
        return format.format(calendar.getTime());
    }

    public static String subtractSixMonths () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-6);
        return format.format(calendar.getTime());
    }

    public static String subtractOneYear (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.YEAR,-1);
        return format.format(calendar.getTime());
    }

    public static String subtractOneYear () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR,-1);
        return format.format(calendar.getTime());
    }

    public static String subtractTwoYears (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.YEAR,-2);
        return format.format(calendar.getTime());
    }

    public static String subtractTwoYears () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR,-2);
        return format.format(calendar.getTime());
    }

    public static String subtractThreeYears (String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.add(Calendar.YEAR,-3);
        return format.format(calendar.getTime());
    }

    public static String subtractThreeYears () throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR,-3);
        return format.format(calendar.getTime());
    }

    public static String getCurrentFormatDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return format.format(calendar.getTime());
    }

}
