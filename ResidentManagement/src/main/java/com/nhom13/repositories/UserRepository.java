/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhom13.repositories;

import com.nhom13.pojo.Admin;
import com.nhom13.pojo.User;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface UserRepository {
    void addResident (User u);
    void addAdmin(User u);
    Admin getAdminByUsername (String username);
    User getUserByUsername (String username); 
    boolean authUser (String username, String password);
    //confirm => thay doi mk va update avatar
    void updateUser(User u);
    //Thêm hoặc xóa token của fmc
    void updateToken (User u);
    List<User> listAllUser ();
    void deleteUser (int userId);//xoa hoan toan user
}
