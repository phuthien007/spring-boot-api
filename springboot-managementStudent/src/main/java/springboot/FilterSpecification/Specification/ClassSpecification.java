package springboot.FilterSpecification.Specification;

import org.springframework.beans.factory.annotation.Autowired;
import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification3;
import springboot.Model.MetaModel.ClassMeta;
import springboot.Service.CourseService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ClassSpecification extends GenericSpecification3<ClassEntity> {

    @Autowired
    private CourseService s;

    @Override
    public Predicate toPredicate(Root<ClassEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        System.out.println("GenericSpecification Class");
        for (FilterInput input : ClassSpecification.getFilters()) {
            Predicate predicate1 = null;
            Predicate predicate2 = null;
            System.out.println("valueMap: " + input.getValueMap());
            // filter data by specification
            switch (input.getOperation()) {
                case EQUALS:
                    System.out.println("OPERATION EQUALS ");
                    System.out.println("length " + input.getValueMap().size());
                    System.out.println("mapp: " + input.getValueMap());
                    for (String index : input.getValueMap().keySet()) {
                        predicate1 = predicate2 = null;
                        String field = input.getValueMap().get(index).keySet().toArray()[0].toString();
                        System.out.println(field);
                        if (field.equals(ClassMeta.COURSE)) {
                            List<String> values = input.getValueMap().get(index).get(field);
                            System.out.println("field " + field + " value " + values);
                            System.out.println("root " + root.get(field).getJavaType().getName());
                            for (String value : values) {
                                System.out.println("value " + value);
//                                CourseEntity e = s.findById(Long.parseLong(value));
//                                System.out.println(s.findById(Long.parseLong(value.trim())));
                                predicate2 = (criteriaBuilder
                                        .equal(root.get(ClassMeta.ID), value));
                                if (predicate1 != null)
                                    predicate1 = criteriaBuilder.or(predicate1, predicate2);
                                else
                                    predicate1 = predicate2;
                            }
                        }
                    }
                    predicates.add(predicate1);
                    break;
            }
        }
//        super.toPredicate(root, query, criteriaBuilder);
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
