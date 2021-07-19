package springboot.ApiController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import springboot.Entity.RoleEntity;
import springboot.Exception.BadRequestException;
import springboot.Model.DTO.RoleDTO;
import springboot.Model.Mapper.RoleMapper;
import springboot.Repository.RoleRepository;

@RestController
@RequestMapping(path = "/api/")
public class RoleController {

	@Autowired
	RoleRepository roleRep;

	@Autowired
	private RoleMapper RoleConverter;

	// lấy tất cả các bản ghi
	@GetMapping("public/role")
	@ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
	@Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
	public ResponseEntity<?> getAllroles(@RequestParam(name = "page", defaultValue = "0", required = false) int page) {
		Page<RoleEntity> roles =roleRep.findAll(PageRequest.of(page, 20));
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalElements", String.valueOf(roles.getTotalElements()));
		headers.add("page", String.valueOf(roles.getNumber()));
		headers.add("elementOfPages", String.valueOf(roles.getNumberOfElements()));
		headers.add("numberOfPages", String.valueOf(roles.getTotalPages()));
//		Log.info("IN getAllusers : size : {}",roles.getTotalElements()  );	
		return ResponseEntity.ok().headers(headers).body(
					roles.toList().stream().map(role -> RoleConverter.toDTO(role)).collect(Collectors.toList()) );
	}

	// lấy bản ghi theo id
	@GetMapping("public/role/{id}")
	public RoleDTO getroleById(@PathVariable(value = "id") Long roleId) {
		return RoleConverter.toDTO(roleRep.findById(roleId)
				.orElseThrow( () -> new BadRequestException("Value id is missing")) ) ;
	}

	// thêm mới bản ghi
	@PostMapping("public/role")
	@ResponseStatus(value = HttpStatus.CREATED)
	public RoleDTO addrole(@RequestBody @Validated RoleDTO role) {
		if (role.getRoleName() == null)
			throw new BadRequestException("Value fullname is missing");
		RoleEntity t = RoleConverter.toEntity(role);
		return RoleConverter.toDTO(roleRep.save(t));
	}

	// sửa bản ghi
	@PutMapping("public/role")
	public RoleDTO updateroleById(@RequestBody @Validated RoleDTO role) {
		if (role.getId() == null)
			throw new BadRequestException("Value id is missing");
		RoleEntity e = roleRep.findById(role.getId())
				.orElseThrow( ()-> new BadRequestException("Value id is missing") );
		e.setRoleName(role.getRoleName());
		e.setDescription(role.getDescription());
		return RoleConverter.toDTO( roleRep.save(e));
	}

	// xóa bản ghi
	@DeleteMapping("role/{id}")
	@ResponseStatus(code = HttpStatus.GONE)
	public Map<String, Boolean> deleteroleById(@PathVariable(value = "id") Long roleId) {
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		try {
			roleRep.deleteById(roleId);
			response.put("status", true);
		} catch (Exception e) {
			// TODO: handle exception
			response.put("status", false);
		}
		
		return response;
	}

}
