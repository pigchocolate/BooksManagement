package controller;

import domain.Record;
import domain.User;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.RecordService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 借阅记录查询
     * @param record 借阅记录的查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return
     */
    @RequestMapping("/searchRecords")
    public ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        //获取当前登录用户的信息
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        //获取查询到的借阅记录的分页结果
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        //指定跳转的视图名称
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("record");
        //将查询到的数据存放在ModelAndView的对象中
        modelAndView.addObject("pageResult", pageResult);
        //将查询的参数返回到页面，用于回显到查询的输入框
        modelAndView.addObject("search", record);
        //将当前页码返回到页面，用于分页插件的分页显示
        modelAndView.addObject("pageNum", pageNum);
        //将当前查询的控制器路径返回到页面，页码变化时继续向该路径发送请求
        modelAndView.addObject("gourl", request.getRequestURI());
        return modelAndView;
    }
}
