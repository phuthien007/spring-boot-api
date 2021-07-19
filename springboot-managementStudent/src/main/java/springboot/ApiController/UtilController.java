package springboot.ApiController;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import springboot.Exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;

@Log4j2
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



    }
