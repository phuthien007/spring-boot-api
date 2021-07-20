package springboot.Service;

import java.util.Date;
import java.util.Map;

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
import springboot.Entity.EventEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.FilterSpecification.FilterInput;
import springboot.FilterSpecification.GenericSpecification1;
import springboot.FilterSpecification.OperationQuery;
import springboot.Repository.ClassRepository;
import springboot.Repository.EventRepository;

@Service
public class EventService {


	@Autowired
	private EventRepository eventRep;
	@Autowired
	private ClassRepository clRep;
	private static final Logger log = LogManager.getLogger(TeacherController.class);


	// tìm tất cả bản ghi có phân trang
	
	
	@Cacheable(value = "event")
	public Page<EventEntity> getAll(PageRequest pageable) {
		return eventRep.findAll(pageable);
	}
	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "event")
	public Page<EventEntity> getAll(Pageable pageable , Map<String, String> keyword) {
//		try {
//			Long id = Long.parseLong(keyword);
//			ClassEntity cl = clRep.findById(id).get();
////			System.out.println("By id: "+ id);
////			System.out.println(cl);
//			return eventRep.findByNameContainingOrStatusContainingOrC(keyword,keyword, cl, pageable);
//
//		} catch (Exception e) {
//			// TODO: handle exc'erroreption
////			System.out.println("error "+e.getLocalizedMessage());
//			return eventRep.findByNameContainingOrStatusContaining(keyword,keyword, pageable);
		GenericSpecification1<EventEntity> eventSpec = new GenericSpecification1<>();
		for(String key : keyword.keySet()){
			eventSpec.add(new FilterInput(key, keyword.get(key), OperationQuery.LIKE));
		}

		return  eventRep.findAll(eventSpec, pageable);

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
			if( event.getName() != null)
				t.setName(event.getName());
			if (event.getC() != null && event.getC().getId() != null) {
				ClassEntity cl = clRep.findById(event.getC().getId())
						.orElseThrow(() -> new ResourceNotFoundException(
								"class Not Found By ID = " + event.getC().getId() + " Cant update this event "));
				t.setC(cl);
			}
			if( event.getStatus() != null)
				t.setStatus(event.getStatus());
			if(event.getHappenDate() != null)
				t.setHappenDate(event.getHappenDate());
			return eventRep.save(t);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN SERVICE UPDATE A EVENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

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
			log.error("[ IN SERVICE DELETE A EVENT] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
}
