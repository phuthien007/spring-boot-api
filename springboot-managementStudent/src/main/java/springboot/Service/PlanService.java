package springboot.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.ApiController.TeacherController;
import springboot.Entity.CourseEntity;
import springboot.Entity.PlanEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification1;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.CourseRepository;
import springboot.Repository.PlanRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PlanService {

	@Autowired
	private PlanRepository planRep;
	@Autowired
	private CourseRepository courseRep;
	private static final Logger log = LogManager.getLogger(TeacherController.class);


	// tìm tất cả bản ghi có phân trang
	@Cacheable(cacheNames = "plans")
	public Page<PlanEntity> getAll(PageRequest pageable) {
		return planRep.findAll(pageable);
	}

	@Cacheable(cacheNames = "plans")
	public List<PlanEntity> getAll() {
		return planRep.findAll();
	}


	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(cacheNames = "plans")
	public Page<PlanEntity> getAll(Pageable pageable, Map<String, String> keyword) {
//		try {
//			Long id = Long.parseLong(keyword);
//			CourseEntity course = courseRep.findById(id).get();
//			return planRep.findByNameContainingOrCourse(keyword, course, pageable);
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return planRep.findByNameContaining(keyword, pageable);
//
//		}
		GenericSpecification1<PlanEntity> planSpec = new GenericSpecification1<>();
		for(String key : keyword.keySet()){
			planSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}
		return  planRep.findAll(planSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(cacheNames = "plans")
	public PlanEntity findById(Long ID) {
		return planRep.findById(ID).orElseThrow(() -> new ResourceNotFoundException("Plan Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public PlanEntity addplan(PlanEntity plan) {
		CourseEntity course = courseRep.findById(plan.getCourse().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"Course Not Found By ID = " + plan.getCourse().getId() + " Cant add this plan "));
		plan.setCourse(course);
		return planRep.save(plan);
	}

	// cập nhật dữ liệu
	@CachePut(cacheNames = "plans")
	public PlanEntity updateplan(PlanEntity plan) {
		PlanEntity t = planRep.findById(plan.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Plan Not Found By ID = " + plan.getId()));
		try {
			if (plan.getName()!= null)
				t.setName(plan.getName());
			if (plan.getCourse()!= null && plan.getCourse().getId() != null) {
				CourseEntity course = courseRep.findById(plan.getCourse().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"Course Not Found By ID = " + plan.getCourse().getId() + " Cant update this plan "));
				t.setCourse(course);
			}
				
			return planRep.save(t);
		} catch (Exception e) {
			log.error("[ IN SERVICE UPDATE A PLAN] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
		
	}

	// xóa bản ghi
	@CacheEvict(cacheNames = "plans", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			PlanEntity t = planRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("Plan Not Found By ID = " + ID));
			planRep.delete(t);
			return true;
		} catch (Exception e) {
			log.error("[ IN SERVICE DELETE A PLAN] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
