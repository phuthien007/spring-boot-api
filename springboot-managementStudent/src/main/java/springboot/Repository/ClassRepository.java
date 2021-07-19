package springboot.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import springboot.Entity.ClassEntity;
import springboot.Entity.CourseEntity;
import springboot.Entity.RegistrationEntity;
import springboot.Entity.TeacherEntity;
import springboot.FilterSpecification.GenericSpecification2;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long>,
        JpaSpecificationExecutor<ClassEntity> {

//    @Query(value = "SELECT d FROM ClassEntity d LEFT JOIN FETCH d.course course LEFT JOIN FETCH d.teacher teacher",
//            countQuery = "SELECT COUNT(d) FROM  ClassEntity d")
	@EntityGraph(attributePaths = {"teacher","course"})
    public Page<ClassEntity> findAll(Pageable pageable);

    //	@Query(value = "SELECT d FROM ClassEntity d ",
//		countQuery = "SELECT COUNT(d) FROM ClassEntity d"
//	)
    @EntityGraph(attributePaths = {"teacher", "course"})
    public Page<ClassEntity> findAll( Specification<ClassEntity> spec, Pageable pageable);

    public Page<ClassEntity> findByNameContainingOrStatusContainingOrTeacherOrCourse(
            String keyword1, String keyword2, TeacherEntity teacher, CourseEntity course, Pageable pageable);

    public Page<ClassEntity> findByNameContainingOrStatusContaining(
            String keyword1, String keyword2, Pageable pageable);

}
