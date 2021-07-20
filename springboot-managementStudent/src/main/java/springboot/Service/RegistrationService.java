package springboot.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
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
import springboot.Entity.ClassEntity;
import springboot.Entity.RegistrationEntity;
import springboot.Entity.StudentEntity;
import springboot.Entity.CompositeKey.ClassStudentIdKey;
import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification3;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.ClassRepository;
import springboot.Repository.RegistrationRepository;
import springboot.Repository.StudentRepository;

@Service
public class RegistrationService {
	private static final Logger log = LogManager.getLogger(RegistrationService.class);

	@Autowired
	private RegistrationRepository registrationRep;
	@Autowired
	private ClassRepository clRep;
	@Autowired
	private StudentRepository studentRep;
	
	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "registration")
	public Page<RegistrationEntity> getAll(PageRequest pageable) {
		return registrationRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "registration")
	public Page<RegistrationEntity> getAll(Pageable pageable ,
					   Map<OperationQuery, Map<String, Map<String, List<String>>>> keyword) {
		GenericSpecification3<RegistrationEntity> registrationSpec = new GenericSpecification3<>();
		// using filter generic 2
		for(OperationQuery operation : keyword.keySet()){
			System.out.println("querying" + operation);
			System.out.println("data "+ keyword.get(operation));

			registrationSpec.add(new FilterInput(operation.toString(), keyword.get(operation), operation));
		}
		return registrationRep.findAll(registrationSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "registration")
	public RegistrationEntity findById(ClassStudentIdKey ID) {
		return registrationRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("registration Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public RegistrationEntity addregistration(RegistrationEntity registration) {
		ClassEntity cl = clRep.findById(registration.getC().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"class Not Found By ID = " + registration.getC().getId() + " Cant add this registration "));
		registration.setC(cl);
		StudentEntity student = studentRep.findById(registration.getS().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"student Not Found By ID = " + registration.getS().getId() + " Cant add this registration "));
		registration.setS(student);
		registration.setCreateDate(new Date(System.currentTimeMillis()));
		registration.setId(new ClassStudentIdKey(cl.getId(), student.getId()));
//		System.out.println(registration.getId().getClassId() + " " + registration.getId().getStudentId());
		return registrationRep.save(registration);
	}

	// cập nhật dữ liệu
	@CachePut(value = "registration")
	public RegistrationEntity updateregistration(RegistrationEntity registration) {
		ClassStudentIdKey ID = new ClassStudentIdKey(registration.getC().getId(), registration.getS().getId());
		RegistrationEntity t = registrationRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("registration Not Found By ID = " + registration.getId()));
		try {
			if( registration.getStatus()!= null)
				t.setStatus(registration.getStatus());
			if(  registration.getRegisterDay()!= null )
				t.setRegisterDay(registration.getRegisterDay());
			return registrationRep.save(t);
		} catch (Exception e) {
			log.error("[ IN SERVICE UPDATE A REGISTRATION] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "registration")
	public Boolean deleteById(ClassStudentIdKey ID) {
		try {
			RegistrationEntity t = registrationRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("registration Not Found By ID = " + ID));
			registrationRep.delete(t);
			return true;
		} catch (Exception e) {
			log.error("[ IN SERVICE DELETE A REGISTRATION] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
}
