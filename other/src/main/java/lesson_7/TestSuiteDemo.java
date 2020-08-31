package lesson_7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestSuiteDemo {

    public static void main(String[] args) {
        System.out.println("Let the madness begin!");
        start(TestSubjectClass.class);
    }

    private static void start(Class<?> className) {
        Method initializer = null;
        Method finalizer = null;
        Method[] declaredMethods = className.getDeclaredMethods();
        TreeMap<Integer, HashSet<Method>> methodsByPriority = new TreeMap<>();

        for (Method curMethod: declaredMethods) {
            if (curMethod.getAnnotation(BeforeSuite.class) != null) initializer = curMethod;
            if (curMethod.getAnnotation(AfterSuite.class) != null) finalizer = curMethod;
            Test testAnno = curMethod.getAnnotation(Test.class);
            if (testAnno != null) addMethodToList(methodsByPriority, curMethod, testAnno.priority());
        }

        System.out.println("Methods sorted by priority:");
        if (initializer != null) System.out.printf("Beginner: %s%n", initializer.getName());
        methodsByPriority.forEach((priority, methodsList) -> {
            StringBuilder sb = new StringBuilder();
            methodsList.forEach((k) -> {
                if (sb.length() > 0) sb.append(", ");
                sb.append(k.getName());
            });
            String methodsEnumeration = sb.toString();
            if (!methodsEnumeration.isEmpty()) System.out.printf("%d: %s%n", priority, methodsEnumeration);
        });
        if (finalizer != null) System.out.printf("Finalizer: %s%n", finalizer.getName());
        Object runner = null;
        try {
            runner = className.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            System.out.printf("Что-то определенно пошло не туда: %s%n", e.getStackTrace());
            return;
        }
        System.out.println("Que empiece la fiesta:");
        if (initializer != null) {
            System.out.printf("Метод %s начинает шоу%n", initializer.getName());
            runMe(runner, initializer);
        }
        System.out.println("Que empiece la fiesta:");
        for (Map.Entry<Integer, HashSet<Method>> entry : methodsByPriority.entrySet()) {
            Integer priority = entry.getKey();
            HashSet<Method> methodsList = entry.getValue();
            for (Method curMethod : methodsList) {
                System.out.printf("%d. %s:%n", priority, curMethod.getName());
                runMe(runner, curMethod);
            }
        }
        if (finalizer != null) {
            System.out.printf("А заканчивает шоу метод %s%n", finalizer.getName());
            runMe(runner, finalizer);
        }
    }

    private static void addMethodToList(TreeMap<Integer, HashSet<Method>> methodsByPriority, Method curMethod, int priority) {
        HashSet<Method> methodsSet = methodsByPriority.getOrDefault(priority, new HashSet<Method>());
        methodsSet.add(curMethod);
        methodsByPriority.put(priority, methodsSet);
    }

    private static void runMe(Object runner, Method curMethod) {
        try {
            curMethod.invoke(runner);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.printf("Ayyy, que lindo eres! Tu excepcion es: %n%s", e.getStackTrace());
        }
    }

}