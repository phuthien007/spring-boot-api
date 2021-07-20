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

public class GenericSpecification1<T> implements Specification<T> {

    private List<FilterInput> filters;

    public GenericSpecification1() {
        this.filters = new ArrayList<>();
    }

    public void add(FilterInput input) {
        filters.add(input);
    }



    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        for (FilterInput input : filters) {

           String keyword = input.getValue().toString();

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