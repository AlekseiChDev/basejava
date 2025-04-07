package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toList;

public class MainCalculation {
    public static void main(String[] args) {
        System.out.println("minValue {1, 2, 3, 3, 2, 3}");
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println("minValue {9, 8}");
        System.out.println(minValue(new int[]{9,8}));

        System.out.println("oddOrEven {1, 2, 3, 3, 2, 3}");
        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        System.out.println("oddOrEven {9, 8}");
        System.out.println(oddOrEven(Arrays.asList(9,8)));
        System.out.println("oddOrEven {2, 4}");
        System.out.println(oddOrEven(Arrays.asList(2,4)));
        System.out.println("oddOrEven {1, 3}");
        System.out.println(oddOrEven(Arrays.asList(1,3)));
        System.out.println("oddOrEven {1, 3, 5}");
        System.out.println(oddOrEven(Arrays.asList(1,3,5)));
    }
    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, digit) -> acc * 10 + digit);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(partitioningBy(x -> x % 2 == 0, toList()));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
