package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import springboot.Entity.CourseEntity;
import springboot.Entity.PlanEntity;
import springboot.Entity.TeacherEntity;
import springboot.FilterSpecification.GenericSpecification;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<PlanEntity, Long>,
        JpaSpecificationExecutor<PlanEntity> {

//	@Query(value = "SELECT plan FROM PlanEntity plan LEFT JOIN FETCH   plan.course course"
//
//    )
//	public List<PlanEntity> findAll();

    @Query(value = "SELECT plan FROM PlanEntity plan LEFT JOIN FETCH   plan.course course",
        countQuery = "SELECT count(plan) FROM PlanEntity plan")
    public Page<PlanEntity> findAll(Pageable pageable);

    //
////	@Query(value = "SELECT plan FROM PlanEntity plan LEFT JOIN  plan.course  course")
//    @Query(value = "SELECT plan FROM PlanEntity plan ")
//    @EntityGraph(attributePaths = "course")
//    @Query(value = "SELECT plan FROM PlanEntity plan LEFT JOIN FETCH   plan.course course",
//            countQuery = "SELECT count(plan) FROM PlanEntity plan"
//    )

    @EntityGraph(attributePaths = "course")
    public Page<PlanEntity> findAll(Specification<PlanEntity> spec, Pageable pageable);


    public Page<PlanEntity> findByNameContainingOrCourse(
            String keyword1, CourseEntity course, Pageable pageable);

    public Page<PlanEntity> findByNameContaining(String keyword1, Pageable pageable);

}
