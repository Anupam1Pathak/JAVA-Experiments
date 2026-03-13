package com.course.main;

import com.course.model.Course;
import com.course.model.Student;
import com.course.service.CourseService;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        CourseService service = new CourseService();

        service.addCourse(new Course(101, "Java Programming", 2));
        service.addCourse(new Course(102, "Python Programming", 3));

        try {

            System.out.print("Enter Student ID: ");
            int sid = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            Student s = new Student(sid, name);

            System.out.print("Enter Course ID to enroll: ");
            int cid = sc.nextInt();

            service.enrollStudent(cid, s);

        } catch (CourseFullException e) {
            System.out.println(e.getMessage());
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }

        service.viewCourses();

        sc.close();
    }
}