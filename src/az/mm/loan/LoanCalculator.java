package az.mm.loan;

import java.util.Scanner;

/**
 * @author MammadovMB on 02.10.2021 11:59 PM
 */

public class LoanCalculator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Please enter credit amount: ");
        double creditAmount = sc.nextDouble();

        System.out.print("Please enter annual interest rate: ");
        double annualInterestRate = sc.nextDouble();

        System.out.print("Please enter period in month: ");
        int period = sc.nextInt();

        double monthlyAmount = calculateMonthlyAmount(creditAmount, annualInterestRate, period);

        System.out.printf("Your monthly amount is %.2f. If you want to pay a big amount please enter: ", monthlyAmount);
        double customAmount = sc.nextDouble();

        if (customAmount > monthlyAmount && customAmount < creditAmount)
            monthlyAmount = customAmount;

        displayPaymentScheduleTable(creditAmount, annualInterestRate, period, monthlyAmount);
    }

    private static void displayPaymentScheduleTable(double creditAmount, double annualInterestRate, int period, double monthlyAmount) {
        double remainingDebt = creditAmount;
        double rateIndex = rateIndex(annualInterestRate);

        header();

        double totalInterestAmount = 0, totalPrincipalAmount = 0, totalPaidAmount = 0;
        for (int i = 1; i <= period; i++) {
            if (remainingDebt <= 0) break;
            double monthlyInterestAmount = remainingDebt * rateIndex;
            double monthlyPrincipalAmount = monthlyAmount - monthlyInterestAmount;
            remainingDebt -= monthlyPrincipalAmount;

            System.out.printf("|%10d |  %20.2f |  %20.2f |  %20.2f |  %20.2f | \n",
                    i, monthlyAmount, monthlyPrincipalAmount, monthlyInterestAmount, remainingDebt);

            totalInterestAmount += monthlyInterestAmount;
            totalPrincipalAmount += monthlyPrincipalAmount;
            totalPaidAmount += monthlyAmount;

            if (remainingDebt + remainingDebt * rateIndex <= monthlyAmount)
                monthlyAmount = remainingDebt + remainingDebt * rateIndex;
        }

        footer(totalInterestAmount, totalPrincipalAmount, totalPaidAmount);
    }

    private static double calculateMonthlyAmount(double creditAmount, double annualInterestRate, int period) {
        double rateIndex = rateIndex(annualInterestRate);
        double annuityIndex = (rateIndex * Math.pow(1 + rateIndex, period)) / (Math.pow(1 + rateIndex, period) - 1);
        return creditAmount * annuityIndex;
    }

    private static double rateIndex(double annualInterestRate) {
        return annualInterestRate / (12 * 100);
    }

    private static void header() {
        line();
        System.out.printf("|%10s |  %20s |  %20s |  %20s |  %20s | \n",
                "Month", "Payment", "Principal", "Interest", "Remaining debt");
        line();
    }

    private static void footer(double totalInterestAmount, double totalPrincipalAmount, double totalPaidAmount) {
        line();
        System.out.printf("Total interest amount: %.2f \nTotal principal amount: %.2f \nTotal paid amount: %.2f \n",
                totalInterestAmount, totalPrincipalAmount, totalPaidAmount);
        line();
    }

    private static void line() {
        final int repeatedCount = 109;
        System.out.println("–".repeat(repeatedCount));
    }

}
