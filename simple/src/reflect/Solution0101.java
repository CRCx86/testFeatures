package reflect;

import java.lang.reflect.Method;

public class Solution0101 {
    public static void main(String[] args) {
        Refl refl = new Refl();

        Class clazz = refl.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {

            System.out.println(String.format("Имя: %s, возвращаемый тип: %s", method.getName(), method.getReturnType().getName()));

            Class<?>[] parameterTypes = method.getParameterTypes();
            System.out.println(String.format("Типы параметров: "));
            for (Class<?> parameterType : parameterTypes) {
                System.out.println(String.format(" %s", parameterType));
            }
        }

        try {
            Class c = Class.forName("reflect.Refl");
            Object o = c.newInstance();
            Refl r = (Refl) o;
            r.printField();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
}

class Refl {

    private Integer id;

    private String name;

    public void printField() {
        System.out.println(String.format("%s %s", id, name));
    }

    public void printField(String additional) {
        System.out.println(String.format("%s %s %s", id, name, additional));
    }
}
