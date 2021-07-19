package springboot.Config.Security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import springboot.Exception.BadRequestException;
import springboot.Service.UserService;
import springboot.Config.Security.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil; 
	
	@Autowired
	private UserService userSer;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String username = null;
			String token = null;
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				username = jwtUtil.extractUsername(token);
			}
			System.out.println("Username: " + username);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

				UserDetails user = userSer.loadUserByUsername(username);
				System.out.println("Authorization " + user.getAuthorities());
				if (user != null && jwtUtil.validateToken(token, user)) {
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
							user, user.getUsername(), user.getAuthorities());

					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}
}
