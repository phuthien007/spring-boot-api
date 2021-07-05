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

import springboot.Entity.ClassEntity;
import springboot.Entity.EventEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.Repository.ClassRepository;
import springboot.Repository.EventRepository;

@Service
public class EventService {


	@Autowired
	private EventRepository eventRep;
	@Autowired
	private ClassRepository clRep;
	
	
	// tìm tất cả bản ghi có phân trang
	
	
	@Cacheable(value = "event")
	public Page<EventEntity> getAll(PageRequest pageable) {
		return eventRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "event")
	public Page<EventEntity> getAll(Pageable pageable ,String keyword) {
		try {
			Long id = Long.parseLong(keyword);
			ClassEntity cl = clRep.findById(id).get();
//			System.out.println("By id: "+ id);
//			System.out.println(cl);
			return eventRep.findByNameContainingOrStatusContainingOrC(keyword,keyword, cl, pageable);

		} catch (Exception e) {
			// TODO: handle exc'erroreption
//			System.out.println("error "+e.getLocalizedMessage());
			return eventRep.findByNameContainingOrStatusContaining(keyword,keyword, pageable);

		}
	}

	// tìm kiếm theo id
	@CachePut(value = "event")
	public EventEntity findById(Long ID) {
		return eventRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("event Not Found By ID = " + ID));
	}

	// thêm mới 1 bản ghi
	public EventEntity addevent(EventEntity event) {
		ClassEntity cl = clRep.findById(event.getC().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						"class Not Found By ID = " + event.getC().getId() + " Cant add this event "));
		event.setC(cl);
		event.setCreateDate(new Date(System.currentTimeMillis()));
		return eventRep.save(event);
	}

	// cập nhật dữ liệu
	@CachePut(value = "event")
	public EventEntity updateevent(EventEntity event) {
		EventEntity t = eventRep.findById(event.getId())
				.orElseThrow(() -> new ResourceNotFoundException("event Not Found By ID = " + event.getId()));
		
		try {
			if( !event.getName().equals(t.getName()))
				t.setName(event.getName());
			if (event.getC().getId() != t.getC().getId()) {
				ClassEntity cl = clRep.findById(event.getC().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"class Not Found By ID = " + event.getC().getId() + " Cant update this event "));
				t.setC(cl);
			}
			if( !event.getStatus().equals(t.getStatus()))
				t.setStatus(event.getStatus());
			if(t.getHappenDate() != null && !event.getHappenDate().equals(t.getHappenDate()))
				t.setHappenDate(event.getHappenDate());
			return eventRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException(e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "event", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			EventEntity t = eventRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("event Not Found By ID = " + ID));
			eventRep.delete(t);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
}
