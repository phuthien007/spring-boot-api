package springboot.FilterSpecification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericSpecification2<T> implements Specification<T> {
    private List<FilterInput> filters;
    private List<String> orderSorting;

    public GenericSpecification2() {
        this.filters = new ArrayList<>();
        this.orderSorting = new ArrayList<>();
    }

    public List<FilterInput> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterInput> filters) {
        this.filters = filters;
    }

    public List<String> getOrderSorting() {
        return orderSorting;
    }

    public void setOrderSorting(List<String> orderSorting) {
        this.orderSorting = orderSorting;
    }

    public void add(FilterInput input) {
        this.filters.add(input);
    }

    public void add(String input) {
        this.orderSorting.add(input);
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
        System.out.println("GenericSpecification 2");
        // separate data

        System.out.println("start Filter has length" + filters.size());
        for (FilterInput input : filters) {
            // filter data by specification
            String operation;

            if (input.getOperation() != null) {
                operation = input.getOperation().toString().toUpperCase();
            } else {
                operation = input.getOperationCustom().toUpperCase();
            }
//            System.out.println("start filter");

            switch (operation) {
                case "EQUALS":
                    System.out.println("OPERATION EQUAL " + input.getField() + " = " + input.getValue());
                    predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get(input.getField()), input.getValue())));
                    break;
                case "NOT_EQUALS":
                    System.out.println("OPERATION NOT_EQUAL " + input.getField() + " = " + input.getValue());
                    predicates.add(criteriaBuilder.notEqual(root.get(input.getField()),  input.getValue()));
                    break;
                case "LIKE":
                    System.out.println("OPERATION LIKE " + input.getField() + " = " + input.getValue());
                    predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue().toString().toUpperCase() + "%"),
                            criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue().toString().toLowerCase() + "%")));
                    break;
                case "GREATER_THAN":
                    System.out.println("OPERATION GREATER_THAN " + input.getField() + " = " + input.getValue());
                    if (root.get(input.getField()).getJavaType().equals("java.util.Date")) {
                        predicates.add(criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        input.getValue().toString())));
                    } else {
                        predicates.add(criteriaBuilder.greaterThan(root.get(input.getField()).as(Date.class), (Date) input.getValue()));
                    }
                    break;
                //                case GREATER_THAN_EQUAL:
                //                    predicates.add( criteriaBuilder.gte(root.get(input.getField()), input.getValue()) );
                //                    break;
                case "LESS_THAN":
                    System.out.println("OPERATION LESS_THAN " + input.getField() + " = " + input.getValue());
                    if (root.get(input.getField()).getJavaType().equals("java.util.Date")) {
                        predicates.add(criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(
                                        root.get(input.getField()).getJavaType(),
                                        input.getValue().toString())));
                    } else {
                        predicates.add(criteriaBuilder.lessThan(root.get(input.getField()).as(Date.class), (Date) input.getValue()));
                    }
                    break;
                //                case LESS_THAN_EQUAL:
                //                    predicates.add( criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()), input.getValue()) );
                //                    break;
            }
        }
//        query.orderBy( criteriaBuilder.asc(root.get("resultDate")) )
//
//        System.out.println(root.get("resultDate").getJavaType());
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


        return null;
    }

}
