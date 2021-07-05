package springboot.Service;

import java.util.Date;

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
import springboot.Repository.StudentRepository;

@Service
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
	public Page<StudentEntity> getAll(Pageable pageable ,String keyword) {
		return studentRep
		.findByAddressContainingOrFullnameContainingOrEmailContainingOrPhoneContainingOrNoteContaining(
				keyword, keyword, keyword, keyword, keyword, pageable);
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
			if (!student.getFullname().equals(t.getFullname()))
				t.setFullname(student.getFullname());
			if (!student.getAddress().equals(t.getAddress()))
				t.setAddress(student.getAddress());
			if (!student.getEmail().equals(t.getEmail()))
				t.setEmail(student.getEmail());
			if (t.getFacebook() != null && !student.getFacebook().equals(t.getFacebook()))
				t.setFacebook(student.getFacebook());
			if (t.getPhone() != null && !student.getPhone().equals(t.getPhone()))
				t.setPhone(student.getPhone());
			if (t.getBirthday() != null && student.getBirthday() != t.getBirthday() )
				t.setBirthday(student.getBirthday());
			if (t.getNote() != null && !student.getNote().equals(t.getNote()))
				t.setNote(student.getNote());
			return studentRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
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
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
	
	
}
