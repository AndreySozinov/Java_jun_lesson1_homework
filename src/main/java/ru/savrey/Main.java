package ru.savrey;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 0.1. Посмотреть разные статьи на Хабр.ру про Stream API
 * 0.2. Посмотреть видеоролики на YouTube.com Тагира Валеева про Stream API
 *
 * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
 * 1.1 Найти максимальное
 * 1.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
 * 1.3 Найти количество чисел, квадрат которых меньше, чем 100_000
 *
 * 2. Создать класс Employee (Сотрудник) с полями: String name, int age, double salary, String department
 * 2.1 Создать список из 10-20 сотрудников
 * 2.2 Вывести список всех различных отделов (department) по списку сотрудников
 * 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
 * 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>> с отделами и сотрудниками внутри отдела
 * 2.5 * Из списка сотрудников с помощью стрима создать Map<String, Double> с отделами и средней зарплатой внутри отдела
 */
public class Main {
    public static void main(String[] args) {
        // 1.0 Создать список из 1_000 рандомных чисел от 1 до 1_000_000
        int MIN = 1;
        int MAX = 1_000_000;
        int AMOUNT = 1_000;
        List<Integer> randomNumbersList = IntStream
                .generate(() -> new Random().nextInt(MIN, MAX + 1))
                .limit(AMOUNT).boxed().toList();

        // 1.1 Найти максимальное
        Optional<Integer> max = randomNumbersList.stream().max(Integer::compare);
        if (max.isPresent()) System.out.printf("max = %d\n", max.get());
        else System.out.println("Error");

        // 1.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
        int BORDER = 500_000;
        int MULTIPLIER = 5;
        int SUBTRAHEND = 150;
        int sum = randomNumbersList
                .stream().filter(it -> it > BORDER)
                .mapToInt(it -> it * MULTIPLIER - SUBTRAHEND)
                .sum();
        System.out.printf("sum = %d\n", sum);

        // 1.3 Найти количество чисел, квадрат которых меньше, чем 100_000
        int LARGEST_SQUARE = 100_000;
        long amount = randomNumbersList.stream().filter(it -> it * it < LARGEST_SQUARE).count();
        System.out.printf("amount = %d\n", amount);

        // 2.1 Создать список из 10-20 сотрудников
        List<Employee> employees = Arrays.asList(
                new Employee("Петров П.П.", 22, 5000, "Разработка"),
                new Employee("Сидоров С.С.", 19, 8000, "Разработка"),
                new Employee("Кузнецов К.К.", 24, 17000, "Разработка"),
                new Employee("Смирнов С.С.", 25, 16500, "Разработка"),
                new Employee("Алексеев А.А.", 18, 10000, "Разработка"),
                new Employee("Иванов И.И.", 23, 9000, "Разработка"),
                new Employee("Васильев В.В.", 22, 14000, "Разработка"),
                new Employee("Емельянова Е.Е.", 25, 34000, "Маркетинг"),
                new Employee("Мирошниченко Н.В.", 22, 35000, "Маркетинг"),
                new Employee("Суханова С.Ф.", 26, 40500, "Маркетинг"),
                new Employee("Сажина С.В.", 23, 39000, "Маркетинг"),
                new Employee("Косова Е.Н.", 28, 42000, "Маркетинг"),
                new Employee("Кузнецова А.Н.", 19, 40000, "Маркетинг"),
                new Employee("Плотников А.М.", 42, 84000, "Менеджмент"),
                new Employee("Постовалов Р.А.", 42, 90000, "Менеджмент"),
                new Employee("Усольцев Ю.А.", 52, 81000, "Менеджмент"),
                new Employee("Григорьев Г.Г.", 40, 75000, "Менеджмент"),
                new Employee("Павлов П.П.", 68, 154000, "Администрация"),
                new Employee("Горбунов М.Ю.", 65, 160000, "Администрация"),
                new Employee("Вершинина А.А.", 66, 148000, "Администрация")
        );

        // 2.2 Вывести список всех различных отделов (department) по списку сотрудников
        List<String> departments = employees.stream()
                .map(it -> it.department)
                .collect(Collectors.toSet())
                .stream().toList();
        System.out.println(departments);

        // 2.3 Всем сотрудникам, чья зарплата меньше 10_000, повысить зарплату на 20%
        double MIN_SALARY = 10_000;
        employees.stream()
                .filter(it -> it.salary < MIN_SALARY)
                .map(it -> new Employee(it.name, it.age, it.salary * 1.2, it.department))
                .forEach(System.out::println);

        // 2.4 * Из списка сотрудников с помощью стрима создать Map<String, List<Employee>>
        // с отделами и сотрудниками внутри отдела
        Map<String, List<Employee>> depsEmployees = employees.stream()
                .map(it -> new AbstractMap.SimpleEntry<>(it.department, it))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
        System.out.println(depsEmployees);

        // 2.5 * Из списка сотрудников с помощью стрима создать Map<String, Double>
        // с отделами и средней зарплатой внутри отдела
        Map<String, Double> depsSalary = employees.stream()
                .map(it -> new AbstractMap.SimpleEntry<>(it.department, it.salary))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.averagingDouble(Map.Entry::getValue)));
        System.out.println(depsSalary);
    }
}