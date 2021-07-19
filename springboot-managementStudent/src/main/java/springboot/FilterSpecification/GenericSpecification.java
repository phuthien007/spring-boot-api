package springboot.FilterSpecification;

import org.springframework.data.jpa.domain.Specification;
import springboot.Entity.TeacherEntity;
import springboot.FilterSpecification.FilterInput;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class GenericSpecification<T> implements Specification<T> {

    private List<FilterInput> filters;

    public GenericSpecification() {
        this.filters = new ArrayList<>();
    }

    public void add(FilterInput input) {
        filters.add(input);
    }

    private Object CastToTypeInput(Class<?> javaType, String value) {
        if (javaType == Long.class) {
            return Long.parseLong(value);
        }
        if (javaType == Integer.class) {
            return Integer.parseInt(value);
        }
        if (javaType == Double.class) {
            return Double.parseDouble(value);
        }
        if (javaType == Float.class) {
            return Float.parseFloat(value);
        }
        return null;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        for (FilterInput input : filters) {

            // separate data
            String value = String.valueOf(input.getValue());
            String keyword = null;
            if(value.startsWith("|") ){
                System.out.println("case 1");
                if( value.substring(1).equalsIgnoreCase("asc") ){
                    System.out.println("Sort asc  " + root.get(input.getField()) );
                    orders.add(criteriaBuilder.asc(root.get(input.getField())));
                }
                else if(value.substring(1).equalsIgnoreCase("desc")){
                    System.out.println("Sort desc  " + root.get(input.getField()) );
                    orders.add(criteriaBuilder.desc(root.get(input.getField())));
                }
            }
            else if(value.contains("|") == true){
                System.out.println("case 2 " );
                String sort = value.substring(value.indexOf("|")+1, value.length());
                keyword = value.substring(0, value.indexOf("|") );
                if( sort.equalsIgnoreCase("asc") ){
                    System.out.println("Sort asc  " + root.get(input.getField()) );
                    orders.add(criteriaBuilder.asc(root.get(input.getField())));
                }
                else if(sort.equalsIgnoreCase("desc")) {
                    orders.add(criteriaBuilder.desc(root.get(input.getField())));
                    System.out.println("Sort desc  " + root.get(input.getField()) );
                }
            }
            else{
                System.out.println("case 3");
                keyword = value;
            }
            // end separate data

            // filter data by specification
            if(keyword != null)
                switch (input.getOperation()) {
                    case EQUALS:
                        System.out.println("OPERATION EQUAL " + input.getField() + " = " + keyword);
                        predicates.add(criteriaBuilder.equal(root.get(input.getField()), keyword));
                        break;
                    case NOT_EQUALS:
                        System.out.println("OPERATION NOT_EQUAL " + input.getField() + " = " + keyword);
                        predicates.add(criteriaBuilder.notEqual(root.get(input.getField()), keyword));
                        break;
                    case LIKE:
                        System.out.println("OPERATION LIKE " + input.getField() + " = " + keyword);
                        predicates.add(criteriaBuilder.like(root.get(input.getField()), "%" + keyword + "%"));
                        break;
                    case GREATER_THAN:
                        System.out.println("OPERATION GREATER_THAN " + input.getField() + " = " + keyword);
                        predicates.add(criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        keyword)));
                        break;
                    //                case GREATER_THAN_EQUAL:
                    //                    predicates.add( criteriaBuilder.gte(root.get(input.getField()), input.getValue()) );
                    //                    break;
                    case LESS_THAN:
                        System.out.println("OPERATION LESS_THAN " + input.getField() + " = " + keyword);
                        predicates.add(criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        keyword)));
                        break;
                    //                case LESS_THAN_EQUAL:
                    //                    predicates.add( criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()), input.getValue()) );
                    //                    break;
                }
        }
        if (!orders.isEmpty())
            query.orderBy(orders);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        else if (fieldType.isAssignableFrom(Date.class)) {
            System.out.println("convert Date");
            System.out.println("convert Date " + new SimpleDateFormat(value));
            return new SimpleDateFormat(value);
        }


        return null;
    }


}