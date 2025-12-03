package org.example;

import java.util.Arrays;

public class FinanceReport {
    private Payment[] payments;
    private String reporterName;
    private int reportDay;
    private int reportMonth;
    private int reportYear;

    // Конструктор
    public FinanceReport(String reporterName, int reportDay, int reportMonth, int reportYear) {
        this.reporterName = reporterName;
        setReportDate(reportDay, reportMonth, reportYear);
        this.payments = new Payment[0];
    }

    // Конструктор с массивом платежей
    public FinanceReport(String reporterName, int reportDay, int reportMonth, int reportYear, Payment[] payments) {
        this.reporterName = reporterName;
        setReportDate(reportDay, reportMonth, reportYear);
        this.payments = payments != null ? payments : new Payment[0];
    }

    // Конструктор копирования (глубокая копия)
    public FinanceReport(FinanceReport other) {
        this.reporterName = other.reporterName;
        this.reportDay = other.reportDay;
        this.reportMonth = other.reportMonth;
        this.reportYear = other.reportYear;

        // Глубокая копия массива платежей
        this.payments = new Payment[other.payments.length];
        for (int i = 0; i < other.payments.length; i++) {
            Payment original = other.payments[i];
            this.payments[i] = new Payment(
                    original.getFullName(),
                    original.getDay(),
                    original.getMonth(),
                    original.getYear(),
                    original.getAmount()
            );
        }
    }

    // Геттеры
    public String getReporterName() {
        return reporterName;
    }

    public int getReportDay() {
        return reportDay;
    }

    public int getReportMonth() {
        return reportMonth;
    }

    public int getReportYear() {
        return reportYear;
    }

    public int getPaymentsCount() {
        return payments.length;
    }

    // Сеттеры
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public void setReportDate(int day, int month, int year) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("День должен быть от 1 до 31");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Месяц должен быть от 1 до 12");
        }
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Год должен быть от 1900 до 2100");
        }

        this.reportDay = day;
        this.reportMonth = month;
        this.reportYear = year;
    }

    // Доступ к платежу по индексу (чтение)
    public Payment getPayment(int index) {
        if (index < 0 || index >= payments.length) {
            throw new IndexOutOfBoundsException("Индекс вне границ массива");
        }
        return payments[index];
    }

    // Доступ к платежу по индексу (запись)
    public void setPayment(int index, Payment payment) {
        if (index < 0 || index >= payments.length) {
            throw new IndexOutOfBoundsException("Индекс вне границ массива");
        }
        payments[index] = payment;
    }

    // Добавление нового платежа
    public void addPayment(Payment payment) {
        Payment[] newPayments = Arrays.copyOf(payments, payments.length + 1);
        newPayments[payments.length] = payment;
        payments = newPayments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("[Автор: %s, дата: %02d.%02d.%d, Платежи: [\n",
                reporterName, reportDay, reportMonth, reportYear));

        for (int i = 0; i < payments.length; i++) {
            sb.append(" ").append(payments[i].toString());
            if (i < payments.length - 1) {
                sb.append(",\n");
            } else {
                sb.append("\n");
            }
        }
        sb.append("]]");

        return sb.toString();
    }
}