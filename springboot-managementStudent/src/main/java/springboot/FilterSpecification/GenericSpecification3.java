package springboot.FilterSpecification;

import org.springframework.data.jpa.domain.Specification;
import springboot.Exception.BadRequestException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericSpecification3<T> implements Specification<T> {


    private static List<FilterInput> filters;

    public GenericSpecification3() {
        this.filters = new ArrayList<>();
    }

    public static List<FilterInput> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterInput> filters) {
        this.filters = filters;
    }

    public void add(FilterInput input) {
        this.filters.add(input);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        System.out.println("GenericSpecification 3");
        // separate data

        System.out.println("start Filter has length" + filters.size());
        for (FilterInput input : filters) {
            Predicate predicate1 = null;
            Predicate predicate2 = null;
            System.out.println("valueMap: " + input.getValueMap());
            // filter data by specification
            switch (input.getOperation()) {
                case EQUALS:
                    System.out.println("OPERATION EQUALS ");
                    try {
                        System.out.println("length " + input.getValueMap().size());
                        System.out.println("mapp: " + input.getValueMap());
                        for (String index : input.getValueMap().keySet()) {
                            predicate1 = predicate2 = null;
                            String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root "+root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
                                if(  root.get(field).getJavaType().getName().equals("java.util.Date") ){
                                    predicate2 = (criteriaBuilder
                                            .equal((root.get(field)).as(Date.class),
                                                    (Date)castToRequiredType(root.get(field).getJavaType(), value)));

                                }
                                else{
//                                    qb.like(qb.function("unaccent",
//                                            String.class,qb.lower(from.get("name"))),
//                                            "%" + removeAccents(name) + "%");
                                    predicate2 = (criteriaBuilder
                                            .equal( criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get(field)) ) , VNCharacterUtils.unAccent(value.toLowerCase()) ));

                                }
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                            predicates.add(predicate1);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                    }

                    break;
                case NOT_EQUALS:
                    System.out.println("OPERATION NOT_EQUAL ");
                    try {
                        System.out.println("length " + input.getValueMap().size());
                        System.out.println("mapp: " + input.getValueMap());
                        for (String index : input.getValueMap().keySet()) {
                            predicate1 = predicate2 = null;
                            String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root "+root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
                                if( root.get(field).getJavaType().getName().equals("java.util.Date") ){
                                    predicate2 = (criteriaBuilder
                                            .notEqual((root.get(field)).as(Date.class),
                                                    (Date)castToRequiredType(root.get(field).getJavaType(), value)));

                                }
                                else{
                                    predicate2 = (criteriaBuilder
                                            .notEqual( criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get(field)) ) , VNCharacterUtils.unAccent(value.toLowerCase()) ));

                                }
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                            predicates.add(predicate1);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                    }
                    break;
                case LIKE:
                    System.out.println("OPERATION LIKE ");
                    try {
                        System.out.println("length " + input.getValueMap().size());
                        System.out.println("mapp: " + input.getValueMap());
                        for (String index : input.getValueMap().keySet()) {
                            predicate1 = predicate2 = null;
                            String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root "+root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
                                if( root.get(field).getJavaType().getName().equals("java.util.Date") ){
                                    System.out.println("run on if date " + (root.get(field)).as(String.class).getJavaType().getName()  );
                                    predicate2 = (criteriaBuilder
                                            .like((root.get(field)).as(String.class),
                                                     "%"+value.toString()+"%"));

                                }
                                else{
                                    predicate2 = (criteriaBuilder
                                            .like( criteriaBuilder.function("unaccent", String.class, criteriaBuilder.lower(root.get(field)) ) , "%"+VNCharacterUtils.unAccent(value.toLowerCase())+"%" ));

                                }
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                            predicates.add(predicate1);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                    }
                    break;
                case GREATER_THAN:
                    System.out.println("OPERATION GREATER_THAN ");
                    try {
                        System.out.println("length " + input.getValueMap().size());
                        System.out.println("mapp: " + input.getValueMap());
                        for (String index : input.getValueMap().keySet()) {
                            predicate1 = predicate2 = null;
                            String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root "+root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
                                if( root.get(field).getJavaType().getName().equals("java.util.Date") ){
                                    predicate2 = (criteriaBuilder
                                            .greaterThan((root.get(field)).as(Date.class),
                                                    (Date)castToRequiredType(root.get(field).getJavaType(), value)));

                                }
                                else{
                                    predicate2 = (criteriaBuilder
                                            .gt(root.get(field),
                                                    (Number) castToRequiredType(
                                                    root.get(input.getField()).getJavaType(),
                                                    input.getValue().toString())));

                                }
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                            predicates.add(predicate1);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                    }

                    break;
                //                case GREATER_THAN_EQUAL:
                //                    predicates.add( criteriaBuilder.gte(root.get(input.getField()), input.getValue()) );
                //                    break;
                case LESS_THAN:
                    System.out.println("OPERATION LESS_THAN ");
                    try {
                        System.out.println("length " + input.getValueMap().size());
                        System.out.println("mapp: " + input.getValueMap());
                        for (String index : input.getValueMap().keySet()) {
                            predicate1 = predicate2 = null;
                            String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root "+root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
                                if( root.get(field).getJavaType().getName().equals("java.util.Date") ){
                                    predicate2 = (criteriaBuilder
                                            .lessThan((root.get(field)).as(Date.class),
                                                    (Date)castToRequiredType(root.get(field).getJavaType(), value)));

                                }
                                else if(root.get(field).getJavaType().getName().equals("java.util.String")){
                                    predicate2 = (criteriaBuilder
                                            .lessThan((root.get(field)).as(String.class),
                                                    (String) castToRequiredType(root.get(field).getJavaType(), value)));

                                }
                                else{
                                    predicate2 = (criteriaBuilder
                                            .lt(root.get(field),
                                                    (Number) castToRequiredType(
                                                            root.get(input.getField()).getJavaType(),
                                                            input.getValue().toString())));

                                }
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                            predicates.add(predicate1);
                        }
                    } catch (Exception e) {
                        throw new BadRequestException(e.getMessage());
                    }

                    break;
                //                case LESS_THAN_EQUAL:
                //                    predicates.add( criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()), input.getValue()) );
                //                    break;
            }
        }
//        query.orderBy( criteriaBuilder.asc(root.get("resultDate")) )
//
//        System.out.println(root.get("startDate").getClass().getName());
//        System.out.println("length " + predicates.size());
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Object castToRequiredType(Class fieldType, String value) throws ParseException {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Float.class)) {
            return Float.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        else if(fieldType.isAssignableFrom(Date.class)){
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        }


        return null;
    }

}
