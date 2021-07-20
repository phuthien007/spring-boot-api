package springboot.ApiController;

import org.springframework.data.domain.Sort;
import springboot.Exception.BadRequestException;
import springboot.FilterSpecification.OperationQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UtilController {

    public static List<Sort.Order> listSort(List<String> sorting) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String input : sorting) {
            String field = input.substring(0, input.indexOf("|"));
            String direction = input.substring(input.indexOf("|") + 1);
            System.out.println("sort field " + input);
            try {
                orders.add(new Sort.Order(Sort.Direction.valueOf(direction.toUpperCase().trim()), field.trim()));
            } catch (Exception e) {
//                log.error("[IN GET ALL TEACHER] " + "has error " + e.getMessage());
                throw new BadRequestException(e.getMessage());
            }
        }
        return orders;
    }

    public static Map<String, Map<String, List<String>>> transformInput(String[] input) {
        Map<String, Map<String, List<String>>> andQuery = new HashMap<>();
        for (int i = 0; i < input.length; i++) {

            if (input[i].contains("//") && input[i].contains(":=")) {
                String[] queries = input[i].split("//");
                Map<String, List<String>> orQuery = new HashMap<>();
                for (String query : queries) {
                    if (query.contains(":=")) {
//                        System.out.println("key: " +query.substring(0, query.indexOf(":=")).trim().toLowerCase());
                        List<String> tmp = new ArrayList<>();
                        System.out.println("field : " + orQuery.get(query.substring(0, query.indexOf(":=")).trim()));
                        if (orQuery.get(query.substring(0, query.indexOf(":=")).trim()) != null) {
                            System.out.println("execute here if 1");
                            try {
                                tmp = orQuery.get(query.substring(0, query.indexOf(":=")).trim());
                                tmp.add(query.substring(query.indexOf(":=") + 2, query.length()).trim().toLowerCase());
                            } catch (Exception e) {
                                System.out.println("error " + e.getMessage());
                            }
                            orQuery.put(query.substring(0, query.indexOf(":=")).trim(), tmp);
                        } else {
                            System.out.println("execute here else 1");
                            tmp.add(query.substring(query.indexOf(":=") + 2, query.length()).trim().toLowerCase());
                            orQuery.put(query.substring(0, query.indexOf(":=")).trim(), tmp);
                        }
                    } else {
                        System.out.println("[IN TRANSFORM INPUT] has error 1 at " + System.currentTimeMillis());
                        throw new BadRequestException("input invalid, Please try again!");
                    }
                }
                andQuery.put(String.valueOf(i), orQuery);
            } else if (input[i].contains(":=")) {
                Map<String, List<String>> query = new HashMap<>();
                List<String> tmp = new ArrayList<>();
                tmp.add(input[i].substring(input[i].indexOf(":=") + 2, input[i].length()).trim());
                query.put(input[i].substring(0, input[i].indexOf(":=")).trim()
                        , tmp);
                andQuery.put(String.valueOf(i), query);
            } else {
                System.out.println("[IN TRANSFORM INPUT] has error 2 at " + System.currentTimeMillis());
                throw new BadRequestException("input invalid, Please try again!");
            }
        }
        System.out.println(andQuery);
        return andQuery;
    }


}
