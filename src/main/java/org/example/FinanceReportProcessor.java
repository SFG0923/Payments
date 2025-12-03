package org.example;

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


    private static String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }

        String[] parts = fullName.trim().split("\\s+");
        return parts.length > 0 ? parts[0] : "";
    }
}