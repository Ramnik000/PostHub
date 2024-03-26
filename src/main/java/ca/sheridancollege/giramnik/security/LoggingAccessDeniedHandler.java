package ca.sheridancollege.giramnik.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler {
@Override
public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
throws IOException, ServletException {
String msg = "";
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
if (auth != null) {
msg = "Account: " + auth.getName() + " was trying to access protected resource: " +
request.getRequestURI();
}
HttpSession sess = request.getSession();
sess.setAttribute("msg", msg);
response.sendRedirect(request.getContextPath() + "/access-denied");
}


}
