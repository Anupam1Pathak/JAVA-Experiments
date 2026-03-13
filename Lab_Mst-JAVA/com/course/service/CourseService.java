package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.CourseFullException;
import com.course.exception.CourseNotFoundException;

import java.io.*;
import java.util.ArrayList;

public class CourseService {

    ArrayList<Course> courseList = new ArrayList<>();

    public void addCourse(Course c) {
        courseList.add(c);
    }

    public void enrollStudent(int courseId, Student s)
            throws CourseFullException, CourseNotFoundException {

        Course foundCourse = null;

        for (Course c : courseList) {
            if (c.getCourseId() == courseId) {
                foundCourse = c;
                break;
            }
        }

        if (foundCourse == null) {
            throw new CourseNotFoundException("Course ID not found!");
        }

        if (foundCourse.getEnrolledStudents() >= foundCourse.getMaxSeats()) {
            throw new CourseFullException("Course is full!");
        }

        foundCourse.setEnrolledStudents(foundCourse.getEnrolledStudents() + 1);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("courses.txt", true))) {

            bw.write(foundCourse.getCourseId() + "," +
                    foundCourse.getCourseName() + "," +
                    s.getStudentId() + "," +
                    s.getStudentName());

            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }

        System.out.println("Student enrolled successfully!");
    }

    public void viewCourses() {

        try (BufferedReader br = new BufferedReader(new FileReader("courses.txt"))) {

            String line;

            System.out.println("\nEnrollment Records:");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                System.out.println("Course ID: " + data[0]);
                System.out.println("Course Name: " + data[1]);
                System.out.println("Student ID: " + data[2]);
                System.out.println("Student Name: " + data[3]);
                System.out.println("---------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
