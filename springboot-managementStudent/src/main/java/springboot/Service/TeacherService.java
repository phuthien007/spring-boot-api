package springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import springboot.Entity.TeacherEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.Repository.TeacherRepository;

@Service
public class TeacherService {

	@Autowired
	private TeacherRepository teacherRep;

	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "teachers")
	public Page<TeacherEntity> getAll(PageRequest pageable) {
		return teacherRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	public Page<TeacherEntity> getAll(Pageable pageable ,String keyword) {
		return teacherRep
		.findByAddressContainingOrEmailContainingOrFullnameContainingOrGradeContainingOrPhoneContaining(
				keyword,keyword,keyword,keyword,keyword,pageable);
	}

	// tìm kiếm theo id
	@CachePut(value = "teachers")
	public TeacherEntity findById(Long ID) {
		return teacherRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public TeacherEntity addTeacher(TeacherEntity teacher) {
		if (teacherRep.existsByEmail(teacher.getEmail()) == true)
			throw new BadRequestException("Value email is exist. Please choose another email!");
//		if(teacher.getEmail() != null) {
//			TeacherEntity t = teacherRep.findByEmail(teacher.getEmail());
//			if ( t != null)
//				throw new BadRequestException("Value email is exist. Please choose another email!");
//		}	
		return teacherRep.save(teacher);
	}

	// cập nhật dữ liệu
	@CachePut(value = "teachers")
	public TeacherEntity updateTeacher(TeacherEntity teacher) {
		TeacherEntity t = teacherRep.findById(teacher.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + teacher.getId()));
		try {
			if (!teacher.getFullname().equals(t.getFullname()) )
				t.setFullname(teacher.getFullname());
			if (t.getAddress() != null && !teacher.getAddress().equals(t.getAddress()))
				t.setAddress(teacher.getAddress());
			if (t.getEmail() != null && !teacher.getEmail().equals(t.getEmail()))
				t.setEmail(teacher.getEmail());
			if (t.getGrade() != null &&  !teacher.getGrade().equals(t.getGrade()))
				t.setGrade(teacher.getGrade());
			if (t.getPhone() != null && !teacher.getPhone().equals(t.getPhone()))
				t.setPhone(teacher.getPhone());
			return teacherRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
		
	}

	// xóa bản ghi
	@CacheEvict(value = "teachers", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			TeacherEntity t = teacherRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("Teacher Not Found By ID = " + ID));
			teacherRep.delete(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

}
