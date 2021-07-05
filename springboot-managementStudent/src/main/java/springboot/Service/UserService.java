package springboot.Service;

import java.io.UnsupportedEncodingException;
import java.sql.Time;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springboot.Entity.RoleEntity;
import springboot.Entity.UserEntity;
import springboot.Exception.BadRequestException;
import springboot.Exception.ResourceNotFoundException;
import springboot.Repository.RoleRepository;
import springboot.Repository.UserRepository;
import springboot.security.UserDetailsImpl;

@Service
public class UserService implements UserDetailsService  {

	@Autowired
	private UserRepository userRep;

	@Autowired
	private RoleRepository roleRep;
	
	@Autowired
	private JavaMailSender mailSender;
	
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
	
	
	
	
	// send email
	public  void sendEmail(String email, String token) throws UnsupportedEncodingException, MessagingException {
		MimeMessage msg =  mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		helper.setFrom("phailamsaonana@gmail.com", "Tran Thien Phu");
		helper.setTo(email);
		String subject = "Here's enter this token ";
		String content = " <p> Hello , </p> "
				+ "<p>You have requested to reset your password.</p>" 
				+ "<p>Here's token you must send to server: </p>"
				+ "<p><b>"+ token + "</b></p>"
				+ "Ignore this email if you do remember your password, or you have not made request!";
		helper.setSubject(subject);
		helper.setText(content, true);
		mailSender.send(msg);
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
			throw new BadRequestException("errorpost " + e.getLocalizedMessage());
		}
		
	}

	// cập nhật dữ liệu
	@CachePut(value = "users")
	public UserEntity updateuser(UserEntity user) {
		UserEntity t = userRep.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user Not Found By ID = " + user.getId()));
		try {
			if (!user.getUsername().equals(t.getUsername()))
				t.setUsername(user.getUsername());
			if (!user.getFullname().equals(t.getFullname()))
				t.setFullname(user.getFullname());
			if (user.getEmail().equals(t.getEmail()))
				t.setEmail(user.getEmail());
			if (user.getRole().equals(t.getRole()))
				t.setRole(user.getRole());
			
			if ( (t.getBirthday() != null && user.getBirthday() != t.getBirthday())
					|| user.getBirthday() !=null)
				t.setBirthday(user.getBirthday());						
			if ( (t.getLockoutDate() !=null && user.getLockoutDate() != (t.getLockoutDate()) )
					|| user.getLockoutDate() != null )
				t.setLastLoginDate(user.getLastLoginDate());
			if (user.getLoginFailedCount() != t.getLoginFailedCount())
				t.setLoginFailedCount(user.getLoginFailedCount());
			
			return userRep.save(t);

		} catch (Exception e) {
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
			// TODO: handle exception
			throw new BadRequestException("Some thing went wrong!. You cant do it!!!");
		}
	}

	
}
