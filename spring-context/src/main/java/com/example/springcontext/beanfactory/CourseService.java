package com.example.springcontext.beanfactory;

/**
 * @author Hodur
 * @date 2021/10/12
 */
public class CourseService {

    private CourseDaoImpl courseDao;

    public String findAll() {
        return courseDao.findAll();
    }
}
