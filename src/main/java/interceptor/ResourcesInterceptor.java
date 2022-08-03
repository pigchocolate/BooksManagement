package interceptor;

import domain.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 拦截器，对用户访问进行拦截控制，未登录跳回登录界面
 */
public class ResourcesInterceptor extends HandlerInterceptorAdapter {

    //任意角色都能访问的路径
    private List<String> ignoreUrl;

    public ResourcesInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //获取请求路径
        String uri = request.getRequestURI();
        //用户登录的相关请求，放行
        if (uri.contains("login")){
            return true;
        }
        //如果用户是已登录状态，判断访问的资源是否有权限
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        if (user != null) {
            //如果是管理员，放行
            if ("ADMIN".equals(user.getRole())) {
                return true;
            }
            //如果是普通用户
            else {
                for (String url : ignoreUrl) {
                    //访问的资源不是管理员权限的资源换，放心
                    if (uri.contains(url)) {
                        return true;
                    }
                }
            }
        }
        //其他情况都直接跳转到登录页面
        request.setAttribute("msg", "您还没登录，请先登录！");
        request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
        return false;
    }
}
