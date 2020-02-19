package com.company;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.*;
import static java.lang.System.console;

public class Main {
    public static void main(String[] args) {
        //Task1();
        //Task2();
        //Task3();
        //Task4();
        //Task5();
    }

    public static void Task1() {
        System.out.println("Enter your name motherfucker: ");

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        System.out.println("Dratuti " + name + "!");
    }

    public static void Task2() {
        System.out.println("Enter the arguments motherfucker");
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.nextLine().split(" ");
        for (int i = 0; i < args.length; i++) {
            System.out.print(args[(args.length - 1) - i] + " ");
        }
    }

    public static void Task3() {
        try {
            System.out.println("Enter the number motherfucker");
            Scanner scanner = new Scanner(System.in);
            int cnt = parseInt(scanner.nextLine());
            for (int i = 0; i < cnt; i++)
                System.out.println(new Random().nextInt());

            for (int i = 0; i < cnt; i++)
                System.out.print(new Random().nextInt() + "");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void Task4() {
        try {
            System.out.println("Enter the numbers motherfucker");
            Scanner scanner = new Scanner(System.in);
            String[] args = scanner.nextLine().split(" ");
            int sum = 0;
            for (int i = 0; i < args.length; i++) {
                sum = sum + parseInt(args[i]);
            }
            System.out.println(sum);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void Task5() {
        while (true) {
            System.out.println("Enter the number of month motherfucker: ");
            try {
                Scanner scanner = new Scanner(System.in);
                int num = parseInt(scanner.nextLine());
                if (num > 0 && num < 13) {
                    //System.out.println(new DateFormatSymbols().getMonths()[num - 1]);
                    System.out.println(Month.of(num));
                } else
                    System.out.println("Hey motherfucker, your number is shit, TRY AGAIN!!!");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
