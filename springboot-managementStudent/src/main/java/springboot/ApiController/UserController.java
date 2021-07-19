package springboot.ApiController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import net.bytebuddy.utility.RandomString;
import springboot.Entity.UserEntity;
import springboot.Exception.BadRequestException;
import springboot.Mail.MailService;
import springboot.Model.DTO.AuthRequest;
import springboot.Model.DTO.UserDTO;
import springboot.Model.Mapper.UserMapper;
import springboot.Repository.UserRepository;
import springboot.Service.UserService;
import springboot.Config.Security.util.JwtUtil;

@RestController
@RequestMapping(path = "/api/")
public class  UserController {

    private static final Logger log = LogManager.getLogger(TeacherController.class);

    @Autowired
    private UserService userSer;
    @Autowired
    private UserRepository userRep;

    @Autowired
    private UserMapper UserConverter;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // lấy tất cả các bản ghi
    @GetMapping("user")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    @Transactional(timeout = 1000, rollbackFor = BadRequestException.class)
    public ResponseEntity<?> getAllusers(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                         @RequestParam(name = "keyword", required = false) String keyword) {
        Page<UserEntity> users = null;
        if (keyword != null)
            users = userSer.getAll(PageRequest.of(page, 20), keyword);
//			.stream().map(user -> UserConverter.toDTO(user)).collect(Collectors.toList());
//				.stream().map(user -> UserConverter.toDTO(user)).collect(Collectors.toList());
        else users = userSer.getAll(PageRequest.of(page, 20));
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalElements", String.valueOf(users.getTotalElements()));
        headers.add("page", String.valueOf(users.getNumber()));
        headers.add("elementOfPages", String.valueOf(users.getNumberOfElements()));
        headers.add("numberOfPages", String.valueOf(users.getTotalPages()));
        return ResponseEntity.ok().headers(headers)
                .body(users.toList().stream().map(user -> UserConverter.toDTO(user)).collect(Collectors.toList()));
    }

    // lấy bản ghi theo id
    @GetMapping("user/{id}")
    public UserDTO getuserById(@PathVariable(value = "id") Long userId) {
        return UserConverter.toDTO(userSer.findById(userId));
    }

    @PostMapping("public/user/forgot-password")
    @ResponseStatus(code = HttpStatus.OK)
    @Async
    public String forgotPassword(@RequestParam String email, HttpServletRequest request) {
        StringBuffer path = request.getRequestURL();
        String token = RandomString.make(45);
        System.out.println(token);
        try {
            userSer.updateResetPasswordToken(token, email);
            UserEntity user = userSer.getUserByPwToken(token);
            Future<Boolean> resultSendMail = mailService.sendEmail(path, email, token, user.getUsername());
            return resultSendMail.get(10, TimeUnit.SECONDS).toString();
        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN FORGOT PASSWORD] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

            throw new BadRequestException("error pass " + e.getMessage() + " Bad request");
        }
    }

    @PostMapping("public/user/reset-password")
    public String forgotPassword(@RequestParam(name = "token") String tokenReset, @RequestBody String password) {
        try {
            UserEntity user = userSer.getUserByPwToken(tokenReset);
            userSer.updatePassword(user, password);
            return "User has username : " + user.getUsername() + " is updated Successfully";
        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN RESET PASSWORD] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

            throw new BadRequestException(e.getMessage());
        }

    }


    // thêm mới bản ghi
    @PostMapping("sign-up")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDTO signUp(@RequestBody UserEntity user) {
        if (user.getFullname() == null || user.getUsername() == null || user.getEmail() == null
                || user.getRole().getId() == null)
            throw new BadRequestException("value is missing");
//		System.out.println(user.getPassword());
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserConverter.toDTO(userSer.adduser(user));
    }

    //	// đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest user) throws Exception {
//		System.out.println("login user");
//		UserDetails user = userSer.loadUserByUsername(request.getUsername());
//		
//		if( user != null || passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//			throw new AuthenticationException("username or password  not correct!");
//		}
        try {
            try {
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            } catch (Exception e) {
                // TODO: handle exception
                log.error("[ IN LOGIN USER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

                UserEntity u = userRep.findByUsername(user.getUsername());
                if (u != null) {
                    u.setLoginFailedCount(u.getLoginFailedCount() + 1);
                    userRep.save(u);
                }
                throw new Exception("Incorrect username or password", e);
            }
            final UserDetails userDetails = userSer.loadUserByUsername(user.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername());
            Map<String, String> token = new HashMap<String, String>();
            token.put("token", jwt);
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            // TODO: handle exception
            log.error("[ IN LOGIN USER] has error: " + e.getMessage() + " " + new Date(System.currentTimeMillis()));

            throw new BadRequestException("Something went wrong");
        }

    }

    // sửa bản ghi
    @PutMapping("user")
    public UserDTO updateuserById(@RequestBody @Validated UserDTO user) {
        if (user.getId() == null)
            throw new BadRequestException("Value id is missing");
        return UserConverter.toDTO(userSer.updateuser(UserConverter.toEntity(user)));
    }

    // xóa bản ghi
    @DeleteMapping("user/{id}")
    public Map<String, Boolean> deleteuserById(@PathVariable(value = "id") Long userId) {
        Map<String, Boolean> response = new HashMap<String, Boolean>();
        response.put("status", userSer.deleteById(userId));
        return response;
    }

}
