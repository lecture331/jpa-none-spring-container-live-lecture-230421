package com.example.entity;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CourseTest {

    // @PersistenceContext // @Autowired
    // EntityManager em; // Spring 이 생성해준 EntityManager 객체를 주입


    @Test
    @DisplayName("Course 생성")
    void createCourse() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("course");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin(); // @Transactional

        try {
            Course course = new Course("JPA", "Robbie", 222222.1); // 비영속, 순수 Java 객체
            em.persist(course);
            // 쓰기 지연 저장소에 insert 쿼리 : insert into course (cost, instructor, title) values(?, ?, ?);

            et.commit(); // flush 메서드가 자동으로 호출되면서 쓰기 지연 저장소의 쿼리를 DB에 요청
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback(); // rollback 호출
        } finally {
            em.clear();
        }

        emf.close();
    }

    @Test
    @DisplayName("Course 조회")
    void readCourse() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("course");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin(); // @Transactional

        try {
            Course course = em.find(Course.class, 1);
            System.out.println("course.getId() = " + course.getId());
            System.out.println("course.getTitle) = " + course.getTitle());
            System.out.println("course.getInstructor() = " + course.getInstructor());
            em.flush();

            Course course1 = em.find(Course.class, 1);
            System.out.println("course1.getId() = " + course1.getId());
            System.out.println("course1.getTitle) = " + course1.getTitle());
            System.out.println("course1.getInstructor() = " + course1.getInstructor());

            System.out.println("(course == course1) = " + (course == course1));

            Course course2 = em.find(Course.class, 2);
            System.out.println("course2.getId() = " + course2.getId());
            System.out.println("course2.getTitle) = " + course2.getTitle());
            System.out.println("course2.getInstructor() = " + course2.getInstructor());


            et.commit(); // flush 메서드가 자동으로 호출되면서 쓰기 지연 저장소의 쿼리를 DB에 요청
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback(); // rollback 호출
        } finally {
            em.clear();
        }

        emf.close();
    }

    @Test
    @DisplayName("Course 수정")
    void updateCourse() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("course");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin(); // @Transactional

        try {
            Course course = em.find(Course.class, 1);
            assertThat(em.contains(course)).isTrue(); // 영속
            // course.setTitle("Spring");
            // courseRepository.save(); // update(merge()) or insert(persist())

            // 준영속 detach()
//            em.detach(course); // 준영속
//            assertThat(em.contains(course)).isFalse();

            // merger() // 조회(영속화) 및 병합(Update) -> 반환
            // 변경할 데이터 객체
            Course mergedCourse = new Course("Spring AOP", "Sparta", 11.0); // ID
            mergedCourse.setId(course.getId());
            em.merge(mergedCourse); // 영속화 -> 병합(Update) -> 반환


            et.commit(); // flush 메서드가 자동으로 호출되면서 쓰기 지연 저장소의 쿼리를 DB에 요청
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback(); // rollback 호출
        } finally {
            em.clear();
        }

        emf.close();
    }

    @Test
    @DisplayName("Course 삭제")
    void deleteCourse() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("course");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin(); // @Transactional

        try {
            Course course = em.find(Course.class, 1); // 지워야할 데이터
            assertThat(em.contains(course)).isTrue(); // 영속

            em.remove(course);
            assertThat(em.contains(course)).isFalse();

            et.commit(); // flush 메서드가 자동으로 호출되면서 쓰기 지연 저장소의 쿼리를 DB에 요청
        } catch (Exception ex) {
            ex.printStackTrace();
            et.rollback(); // rollback 호출
        } finally {
            em.clear();
        }

        emf.close();
    }


}
