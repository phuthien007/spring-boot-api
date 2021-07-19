package springboot.Service;

import java.util.Date;
import java.util.Map;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.StudentEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.StudentRepository;

@Service
@Log4j2
public class StudentService {

	@Autowired
	private StudentRepository studentRep;

	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "students")
	public Page<StudentEntity> getAll(PageRequest pageable) {
		return studentRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "students")
	public Page<StudentEntity> getAll(Pageable pageable , Map<String, String> keyword) {
//		return studentRep
//		.findByAddressContainingOrFullnameContainingOrEmailContainingOrPhoneContainingOrNoteContaining(
//				keyword, keyword, keyword, keyword, keyword, pageable);
		GenericSpecification<StudentEntity> studentSpec = new GenericSpecification<>();
		for( String key : keyword.keySet()){
			studentSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}
		return studentRep.findAll(studentSpec, pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "students")
	public StudentEntity findById(Long ID) {
		return studentRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("Student Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public StudentEntity addStudent(StudentEntity student) {
		if (studentRep.existsByEmail(student.getEmail()) == true)
			throw new BadRequestException("Value email is exist. Please choose another email!");
//		if(Student.getEmail() != null) {
//			StudentEntity t = studentRep.findByEmail(Student.getEmail());
//			if ( t != null)
//				throw new BadRequestException("Value email is exist. Please choose another email!");
//		}
		student.setCreateDate(new Date(System.currentTimeMillis()));
		return studentRep.save(student);
	}

	// cập nhật dữ liệu
	@CachePut(value = "students")
	public StudentEntity updateStudent(StudentEntity student) {
		StudentEntity t = studentRep.findById(student.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Student Not Found By ID = " + student.getId()));
		try {
			if (student.getFullname()!= null)
				t.setFullname(student.getFullname());
			if (student.getAddress()!= null)
				t.setAddress(student.getAddress());
			if (student.getEmail()!= null)
				t.setEmail(student.getEmail());
			if (student.getFacebook()!= null )
				t.setFacebook(student.getFacebook());
			if (student.getPhone()!= null )
				t.setPhone(student.getPhone());
			if (student.getBirthday()!= null )
				t.setBirthday(student.getBirthday());
			if (student.getNote()!= null )
				t.setNote(student.getNote());
			return studentRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
//			log.error("[ IN SERVICE UPDATE A STUDENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException(e.getMessage());
		}
		
	}

	// xóa bản ghi
	@CacheEvict(value = "students", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			StudentEntity t = studentRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("Student Not Found By ID = " + ID));
			studentRep.delete(t);
			return true;
		} catch (Exception e) {
//			log.error("[ IN SERVICE DELETE A STUDENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
	
	
}
