package com.marketplace.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.marketplace.api.JwtUtil;
import com.marketplace.api.UserPrincipal;
import com.marketplace.domain.ApplicationException;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.dao.UserDao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String header = request.getHeader("Authorization");
			String accessToken = resolveToken(header);

			if (accessToken != null) {
				var jwt = jwtUtil.verifyAccessToken(accessToken);
				var user = userDao.findByPhone(jwt.getSubject());

				if (user == null) {
					throw new UsernameNotFoundException("User not found");
				}
				
				if (user.isDisabled()) {
					throw new ApplicationException("Account disabled");
				}

				var principal = new UserPrincipal(user);

				var authorities = AuthorityUtils.createAuthorityList("ROLE_" + user.getRole().name());

				if (user.getRole() == User.Role.OWNER) {
					var permissions = User.Permission.values();
					for (var up : permissions) {
						authorities.add(new SimpleGrantedAuthority(up.name()));
					}
				}
				
				var auth = new JwtAuthenticationToken(principal, authorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			responseUnauthorized(response, e);
			return;
		}

		filterChain.doFilter(request, response);

	}

	private String resolveToken(String header) {
		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7);
		}

		return null;
	}

	private void responseUnauthorized(HttpServletResponse response, Exception e) throws IOException {
		SecurityContextHolder.clearContext();

		var out = response.getWriter();
//		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		out.print(e.getMessage());
		out.flush();
	}

}
