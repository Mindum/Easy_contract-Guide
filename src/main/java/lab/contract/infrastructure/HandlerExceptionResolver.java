package lab.contract.infrastructure;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerExceptionResolver {
    ModelAndView resolverException(HttpServletRequest request, HttpServletResponse response,Object handler, Exception ex);
}
