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

    @Override
    public Predicate toPredicate(Root<ClassEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return super.toPredicate(root, query, criteriaBuilder);
    }
}
