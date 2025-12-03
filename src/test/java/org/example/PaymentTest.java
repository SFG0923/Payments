package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void testPaymentConstructorAndGetters() {
        Payment payment = new Payment("Иванов Иван Иванович", 15, 5, 2023, 15000);

        assertEquals("Иванов Иван Иванович", payment.getFullName());
        assertEquals(15, payment.getDay());
        assertEquals(5, payment.getMonth());
        assertEquals(2023, payment.getYear());
        assertEquals(15000, payment.getAmount());
    }

    @Test
    public void testGetAmountInRubles() {
        Payment payment1 = new Payment("Иванов Иван", 1, 1, 2023, 15000);
        assertEquals("150 руб. 00 коп.", payment1.getAmountInRubles());

        Payment payment2 = new Payment("Петров Петр", 1, 1, 2023, 12345);
        assertEquals("123 руб. 45 коп.", payment2.getAmountInRubles());
    }

    @Test
    public void testEqualsAndHashCode() {
        Payment payment1 = new Payment("Иванов Иван", 15, 5, 2023, 15000);
        Payment payment2 = new Payment("Иванов Иван", 15, 5, 2023, 15000);
        Payment payment3 = new Payment("Петров Петр", 15, 5, 2023, 15000);

        assertEquals(payment1, payment2);
        assertNotEquals(payment1, payment3);
        assertEquals(payment1.hashCode(), payment2.hashCode());
    }

    @Test
    public void testToString() {
        Payment payment = new Payment("Иванов Иван", 15, 5, 2023, 15000);
        String expected = "Плательщик: Иванов Иван, дата: 15.05.2023 сумма: 150 руб. 00 коп.";
        assertEquals(expected, payment.toString());
    }
}

class FinanceReportTest {

    @Test
    public void testFinanceReportConstructorAndGetters() {
        FinanceReport report = new FinanceReport("Сидоров Сидор", 20, 6, 2023);

        assertEquals("Сидоров Сидор", report.getReporterName());
        assertEquals(20, report.getReportDay());
        assertEquals(6, report.getReportMonth());
        assertEquals(2023, report.getReportYear());
        assertEquals(0, report.getPaymentsCount());
    }

    @Test
    public void testAddAndGetPayment() {
        FinanceReport report = new FinanceReport("Сидоров Сидор", 20, 6, 2023);

        Payment payment1 = new Payment("Иванов Иван", 15, 5, 2023, 15000);
        Payment payment2 = new Payment("Петров Петр", 16, 5, 2023, 20000);

        report.addPayment(payment1);
        report.addPayment(payment2);

        assertEquals(2, report.getPaymentsCount());
        assertEquals(payment1, report.getPayment(0));
        assertEquals(payment2, report.getPayment(1));
    }

    @Test
    public void testSetPayment() {
        FinanceReport report = new FinanceReport("Сидоров Сидор", 20, 6, 2023);

        Payment payment1 = new Payment("Иванов Иван", 15, 5, 2023, 15000);
        Payment payment2 = new Payment("Петров Петр", 16, 5, 2023, 20000);

        report.addPayment(payment1);
        report.setPayment(0, payment2);

        assertEquals(payment2, report.getPayment(0));
    }

    @Test
    public void testCopyConstructor() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000)
        };

        FinanceReport original = new FinanceReport("Сидоров Сидор", 20, 6, 2023, payments);
        FinanceReport copy = new FinanceReport(original);


        Payment modifiedPayment = new Payment("Сидоров Сидор", 17, 5, 2023, 25000);
        original.setPayment(0, modifiedPayment);


        assertNotEquals(modifiedPayment, copy.getPayment(0));
        assertEquals("Иванов Иван", copy.getPayment(0).getFullName());
    }

    @Test
    public void testToString() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000)
        };

        FinanceReport report = new FinanceReport("Сидоров Сидор", 20, 6, 2023, payments);

        String result = report.toString();
        assertTrue(result.contains("Автор: Сидоров Сидор"));
        assertTrue(result.contains("дата: 20.06.2023"));
        assertTrue(result.contains("Иванов Иван"));
        assertTrue(result.contains("Петров Петр"));
    }
}

class FinanceReportProcessorTest {

    @Test
    public void testGetPaymentsByLastName() {
        Payment[] payments = {
                new Payment("Иванов Иван Иванович", 15, 5, 2023, 15000),
                new Payment("Петров Петр Петрович", 16, 5, 2023, 20000),
                new Payment("Иванова Анна Ивановна", 17, 5, 2023, 18000),
                new Payment("Сидоров Сидор Сидорович", 18, 5, 2023, 22000)
        };

        FinanceReport report = new FinanceReport("Отчетчик", 20, 6, 2023, payments);

        FinanceReport result = FinanceReportProcessor.getPaymentsByLastName(report, 'И');

        assertEquals(2, result.getPaymentsCount());
        assertEquals("Иванов Иван Иванович", result.getPayment(0).getFullName());
        assertEquals("Иванова Анна Ивановна", result.getPayment(1).getFullName());
    }

    @Test
    public void testGetPaymentsByLastNameEmptyResult() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000)
        };

        FinanceReport report = new FinanceReport("Отчетчик", 20, 6, 2023, payments);

        FinanceReport result = FinanceReportProcessor.getPaymentsByLastName(report, 'С');

        assertEquals(0, result.getPaymentsCount());
    }

    @Test
    public void testGetPaymentsLessThanAmount() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000),
                new Payment("Сидоров Сидор", 17, 5, 2023, 18000),
                new Payment("Алексеев Алексей", 18, 5, 2023, 12000)
        };

        FinanceReport report = new FinanceReport("Отчетчик", 20, 6, 2023, payments);

        FinanceReport result = FinanceReportProcessor.getPaymentsLessThanAmount(report, 18000);

        assertEquals(2, result.getPaymentsCount());
        assertEquals("Иванов Иван", result.getPayment(0).getFullName());
        assertEquals("Алексеев Алексей", result.getPayment(1).getFullName());
    }

    @Test
    public void testGetPaymentsLessThanAmountAll() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000)
        };

        FinanceReport report = new FinanceReport("Отчетчик", 20, 6, 2023, payments);

        FinanceReport result = FinanceReportProcessor.getPaymentsLessThanAmount(report, 25000);

        assertEquals(2, result.getPaymentsCount());
    }

    @Test
    public void testGetPaymentsLessThanAmountNone() {
        Payment[] payments = {
                new Payment("Иванов Иван", 15, 5, 2023, 15000),
                new Payment("Петров Петр", 16, 5, 2023, 20000)
        };

        FinanceReport report = new FinanceReport("Отчетчик", 20, 6, 2023, payments);

        FinanceReport result = FinanceReportProcessor.getPaymentsLessThanAmount(report, 10000);

        assertEquals(0, result.getPaymentsCount());
    }

    @Test
    public void testNullReport() {
        FinanceReport result1 = FinanceReportProcessor.getPaymentsByLastName(null, 'И');
        assertNotNull(result1);
        assertEquals(0, result1.getPaymentsCount());

        FinanceReport result2 = FinanceReportProcessor.getPaymentsLessThanAmount(null, 10000);
        assertNotNull(result2);
        assertEquals(0, result2.getPaymentsCount());
    }
}