import java.util.*;

public class Test {
    public static void main(String[] args) {
//        int[] arr = {1, 3, 4, 5, 3};
//        int nu = arr[2];
//        int indexForDeleting = 4;
//        int numberFroAdding = 654;
//
//        if (indexForDeleting >= arr.length || indexForDeleting < 0) {
//            throw new ArrayIndexOutOfBoundsException("Entered incorrect index");
//        }
//
//        int[] newArr = new int[arr.length - 1];
//        for (int i = 0, j = 0; i < arr.length; i++) {
//            if (i != indexForDeleting) {
//                newArr[j++] = arr[i];
//            }
//        }
//        System.out.println(Arrays.toString(newArr));
//        int[] addedNumber = new int[newArr.length + 1];
//        int index = 0;
//        for (int addedArr: newArr){
//            addedNumber[index++] = addedArr;
//        }
//        addedNumber[newArr.length] = numberFroAdding;
//        System.out.println(Arrays.toString(addedNumber));
        List<String> stringList = new LinkedList<>();
        stringList.add("A");
        stringList.add("B");
        stringList.add("C");
        stringList.forEach(x -> System.out.println(x));


                // Создаем ArrayList и LinkedList
                List<Integer> arrayList = new Stack<>();
                List<Integer> linkedList = new LinkedList<>();

                // Замеряем время добавления элементов в ArrayList
                long startTime = System.nanoTime();
                for (int i = 0; i < 1000000; i++) {
                    arrayList.add(i);
                }
                long endTime = System.nanoTime();
                long durationArrayList = endTime - startTime;
                System.out.println("Time taken for adding elements in ArrayList: " + durationArrayList + " ns");

                startTime = System.nanoTime();
                for (int i = 0; i < 100000; i++) {
                    linkedList.add(i);
                }
                endTime = System.nanoTime();
                long durationLinkedList = endTime - startTime;
                System.out.println("Time taken for adding elements in LinkedList: " + durationLinkedList + " ns");


                System.out.println("ArrayList is faster: " + (durationArrayList < durationLinkedList));
                System.out.println("LinkedList is faster: " + (durationLinkedList < durationArrayList));
            }
        }