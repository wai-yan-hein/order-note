/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.User;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface UserDal {

    User search(String user, String pass);

    User save(User user);

    DeleteResult remove(String name);

    List<User> findAll();

}
