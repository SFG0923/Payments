package org.example;
import java.util.Objects;

public class Payment {
    private String fullName;
    private int day;
    private int month;
    private int year;
    private int amount;


    public Payment(String fullName, int day, int month, int year, int amount) {
        this.fullName = fullName;
        setDate(day, month, year);
        this.amount = amount;
    }

    // Геттеры
    public String getFullName() {
        return fullName;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getAmount() {
        return amount;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDate(int day, int month, int year) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("День должен быть от 1 до 31");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Месяц должен быть от 1 до 12");
        }
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Год должен быть от 1900 до 2100");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountInRubles() {
        int rubles = amount / 100;
        int kopecks = amount % 100;
        return String.format("%d руб. %02d коп.", rubles, kopecks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return day == payment.day &&
                month == payment.month &&
                year == payment.year &&
                amount == payment.amount &&
                Objects.equals(fullName, payment.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, day, month, year, amount);
    }

    @Override
    public String toString() {
        return String.format("Плательщик: %s, дата: %02d.%02d.%d сумма: %s",
                fullName, day, month, year, getAmountInRubles());
    }
}