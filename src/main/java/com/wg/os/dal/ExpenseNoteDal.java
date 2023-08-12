/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.dal;

import com.mongodb.client.result.DeleteResult;
import com.wg.os.document.ExpNote;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public interface ExpenseNoteDal {

    List<ExpNote> findAll();

    ExpNote save(ExpNote exp);

    DeleteResult remove(String id);

    List<ExpNote> search(Date stDate, Date endDate);

}
