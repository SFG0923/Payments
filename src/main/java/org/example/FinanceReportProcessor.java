package org.example;

import java.util.ArrayList;
import java.util.List;

public class FinanceReportProcessor {


    public static FinanceReport getPaymentsByLastName(FinanceReport report, char firstLetter) {
        if (report == null) {
            return new FinanceReport("", 1, 1, 1900);
        }

        FinanceReport result = new FinanceReport(
                report.getReporterName(),
                report.getReportDay(),
                report.getReportMonth(),
                report.getReportYear()
        );

        for (int i = 0; i < report.getPaymentsCount(); i++) {
            Payment payment = report.getPayment(i);
            String fullName = payment.getFullName();


            String lastName = extractLastName(fullName);

            if (!lastName.isEmpty() && Character.toUpperCase(lastName.charAt(0)) == Character.toUpperCase(firstLetter)) {
                result.addPayment(new Payment(
                        payment.getFullName(),
                        payment.getDay(),
                        payment.getMonth(),
                        payment.getYear(),
                        payment.getAmount()
                ));
            }
        }

        return result;
    }


    public static FinanceReport getPaymentsLessThanAmount(FinanceReport report, int maxAmountInKopecks) {
        if (report == null) {
            return new FinanceReport("", 1, 1, 1900);
        }

        FinanceReport result = new FinanceReport(
                report.getReporterName(),
                report.getReportDay(),
                report.getReportMonth(),
                report.getReportYear()
        );

        for (int i = 0; i < report.getPaymentsCount(); i++) {
            Payment payment = report.getPayment(i);

            if (payment.getAmount() < maxAmountInKopecks) {
                result.addPayment(new Payment(
                        payment.getFullName(),
                        payment.getDay(),
                        payment.getMonth(),
                        payment.getYear(),
                        payment.getAmount()
                ));
            }
        }

        return result;
    }

    //Дополнительное задание
    public static double getTotalPaymentByDate(FinanceReport report, String dateStr) {
        if (report == null || dateStr == null || !dateStr.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            return 0;
        }

        String[] parts = dateStr.split("\\.");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (year < 100) {
            year += (year < 80) ? 2000 : 1900;
        }

        double totalAmount = 0;
        for (int i = 0; i < report.getPaymentsCount(); i++) {
            Payment p = report.getPayment(i);
            if (p.getDay() == day && p.getMonth() == month && p.getYear() == year) {
                totalAmount += p.getAmount();
            }
        }
        return totalAmount;
    }

    public static List<String> getMonthsWithNoPayments(FinanceReport report, int year) {
        String[] monthNames = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };

        boolean[] hasPayment = new boolean[12];

        if (report != null) {
            for (int i = 0; i < report.getPaymentsCount(); i++) {
                Payment p = report.getPayment(i);
                if (p.getYear() == year) {
                    int monthIdx = p.getMonth() - 1;
                    if (monthIdx >= 0 && monthIdx < 12) {
                        hasPayment[monthIdx] = true;
                    }
                }
            }
        }

        List<String> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (!hasPayment[i]) {
                result.add(monthNames[i]);
            }
        }
        return result;
    }




    private static String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }

        String[] parts = fullName.trim().split("\\s+");
        return parts.length > 0 ? parts[0] : "";
    }
}