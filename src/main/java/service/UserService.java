package service;

import entity.PageResult;
import domain.User;

/**
 * 用户接口
 */
public interface UserService {
    //通过User的用户账户和用户密码查询用户信息
    User login(User user);
    //添加用户
    void addUser(User user);
    //编辑用户
    void editUser(User user);
    //用户离职
    void deleteUser(Integer id);
    //查找用户
    PageResult searchUsers(User user, Integer pageNum, Integer pageSize);
    //通过id查找用户
    User findById(Integer id);
    //检查用户名是否已经存在
    Integer checkName(String name);
    //检查用户邮箱是否存储
    Integer checkEmail(String email);
}
