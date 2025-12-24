package com.learnpath.config;

import com.learnpath.entity.Course;
import com.learnpath.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final CourseRepository courseRepository;
    
    @Override
    public void run(String... args) {
        if (courseRepository.count() == 0) {
            log.info("Initializing course data...");
            
            List<Course> courses = Arrays.asList(

                Course.builder()
                    .id("java")
                    .title("Java Programming")
                    .description("Learn Java from basics to advanced concepts")
                    .gradient("from-orange-500 to-red-500")
                    .externalUrl("https://spurious-lens-384.notion.site/Java-2cd6ecc87b1180688ceefe303b8e034e")
                    .build(),

                    Course.builder()
                            .id("react")
                            .title("React")
                            .description("Master React concepts with hands-on practice")
                            .gradient("from-blue-500 to-cyan-500")
                            .externalUrl("https://spurious-lens-384.notion.site/React-2d36ecc87b118081af3af068419b798c")
                            .build(),

                    Course.builder()
                            .id("angular")
                            .title("Angular")
                            .description("Angular concepts with hands-on practice")
                            .gradient("from-blue-500 to-cyan-500")
                            .externalUrl("https://spurious-lens-384.notion.site/Angular-115dc601593341f496213da864407883")
                            .build(),
                    Course.builder()
                            .id("git")
                            .title("GIT")
                            .description("GIT concepts with hands-on practice")
                            .gradient("from-blue-500 to-cyan-500")
                            .externalUrl("https://spurious-lens-384.notion.site/GIT-2d06ecc87b1180169a11f9a007c80bae")
                            .build()
                    );
            
            courseRepository.saveAll(courses);
            log.info("Initialized {} courses", courses.size());
        }
    }
}
