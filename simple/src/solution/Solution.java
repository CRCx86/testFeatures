package solution;

import java.util.HashMap;

public class Solution {

    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap();
        map.put("han", "solo");
        map.put("ban", "afflek");

        map.merge("han", "ne han", (oldValue, newValue) -> {
            return newValue + ": " + oldValue;
        });

        System.out.println(map.get("han"));
    }

}
