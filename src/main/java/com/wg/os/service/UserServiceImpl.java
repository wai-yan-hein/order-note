/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.service;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.dal.UserDal;
import com.wg.os.document.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lenovo
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDal dal;

    @Override
    public User search(String user, String pass) {
        return dal.search(user, pass);
    }

    @Override
    public User save(User user) {
        return dal.save(user);
    }

    @Override
    public DeleteResult remove(String name) {
        return dal.remove(name);
    }

    @Override
    public List<User> findAll() {
        return dal.findAll();
    }

}
