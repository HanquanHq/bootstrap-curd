package cn.hanquan.bootstrapcurd.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，判断是否登录
 * 待完善：登录后跳转回原有界面
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");
        if (user == null) {
            request.setAttribute("msg","请先登录~");
            request.getRequestDispatcher("/login").forward(request,response);//获取转发器
            return false;
        } else {
            return true;
        }
    }
}
