package springboot.Service;

import java.sql.Time;
import java.util.Date;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springboot.ApiController.TeacherController;
import springboot.Entity.RoleEntity;
import springboot.Entity.UserEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.Repository.RoleRepository;
import springboot.Repository.UserRepository;
import springboot.Config.Security.UserDetailsImpl;

@Service
public class UserService implements UserDetailsService  {

	private static final Logger log = LogManager.getLogger(TeacherController.class);

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleRepository roleRep;
	

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity user = userRep.findByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException("Could not find user by username: {}"+username);
		}
		return new UserDetailsImpl(user) ;
	}

//	public String generateForgotToken(String username) {
//		UserEntity user = userRep.findByUsername(username);
//		if(user != null)
//		{
//			String token = jwtUtil.createForgotPasswordToken(username); 
//			user.setForgotPassWordToken(token);
//			return token;
//		}
//		return null;
//	}
//	
	
	// reset password
	public void updateResetPasswordToken(String token, String email) {
		UserEntity user = userRep.findByEmail(email);
		if(user != null) {
			user.setForgotPassWordToken(token);
			userRep.save(user);
		}
		else {
			throw new BadRequestException("Could not find any user with email: " + email);
		}
	}
	
	public UserEntity getUserByPwToken(String resetPasswordToken) {
		return userRep.findByForgotPassWordToken(resetPasswordToken);
	}
	
	public void updatePassword(UserEntity user, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(password);
		user.setPassword(encodePassword);
		user.setForgotPassWordToken(null);
		userRep.save(user);
	}
	
	
	
	

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		return new User("admin","password", new ArrayList<>());
//	}

	// tìm tất cả bản ghi có phân trang
	@Cacheable(value = "users")
	public Page<UserEntity> getAll(PageRequest pageable) {
		return userRep.findAll(pageable);
	}

	// tìm tất cả bản ghi có phân trang và lọc dữ liệu theo keyword
	@Cacheable(value = "users")
	public Page<UserEntity> getAll(Pageable pageable, String keyword) {
		return userRep.findByUsernameOrEmailContainingOrFullnameContaining(
				keyword, keyword , keyword , pageable);
	}

	// tìm kiếm theo id
	@CachePut(value="users")
	public UserEntity findById(Long ID) {
		return userRep.findById(ID)
				.orElseThrow(() -> new ResourceNotFoundException("user Not Found By ID = " + ID));
	}
	
//	// tìm kiếm theo username
//		public UserEntity findByUsername(String username) {
//			return userRep.findByUsername(username)
//					.orElseThrow(() -> new ResourceNotFoundException("user Not Found By username = " + username));
//		}

	// thêm mới 1 bản ghi
	public UserEntity adduser(UserEntity user) {
		try {
			if( userRep.existsByEmail(user.getEmail()) == true) {
				throw new BadRequestException( user.getEmail() + " has exist");
			}
			if( userRep.existsByEmail(user.getUsername()) == true) {
				throw new BadRequestException( user.getUsername() + " has exist");
			}
			RoleEntity role = roleRep.findById(user.getRole().getId()).get();
			user.setRole(role);
			user.setRegisterDate(new Time(System.currentTimeMillis()));
			return userRep.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("[ IN SERVICE ADD A USER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			throw new BadRequestException("errorpost " + e.getLocalizedMessage());
		}
		
	}

	// cập nhật dữ liệu
	@CachePut(value = "users")
	public UserEntity updateuser(UserEntity user) {
		UserEntity t = userRep.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user Not Found By ID = " + user.getId()));
		try {
			if (user.getUsername() != null)
				t.setUsername(user.getUsername());
			if (user.getFullname() != null)
				t.setFullname(user.getFullname());
			if (user.getEmail() != null)
				t.setEmail(user.getEmail());
			if (user.getRole() != null)
				t.setRole(user.getRole());
			if (user.getBirthday() !=null)
				t.setBirthday(user.getBirthday());						
			if ( user.getLockoutDate() != null )
				t.setLastLoginDate(user.getLastLoginDate());
			if (user.getUsername() != null)
				t.setLoginFailedCount(user.getLoginFailedCount());
			
			return userRep.save(t);

		} catch (Exception e) {
			log.error("[ IN SERVICE UPDATE A USER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("errorput" + e.getMessage());
		}
	}

	// xóa bản ghi
	@CacheEvict(value = "users", allEntries = true)
	public Boolean deleteById(Long ID) {
		try {
			UserEntity t = userRep.findById(ID)
					.orElseThrow(() -> new ResourceNotFoundException("user Not Found By ID = " + ID));
			userRep.delete(t);
			return true;
		} catch (Exception e) {
			log.error("[ IN SERVICE DELETE A USER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}
}
