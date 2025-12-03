package org.example;

public class FinanceReportProcessor {

    // 1) Получение платежей всех людей, чья фамилия начинается на указанный символ
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

            // Извлекаем фамилию (первое слово в ФИО)
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

    // 2) Получение всех платежей, размер которых меньше заданной величины
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

    // Вспомогательный метод для извлечения фамилии
    private static String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }

        String[] parts = fullName.trim().split("\\s+");
        return parts.length > 0 ? parts[0] : "";
    }
}