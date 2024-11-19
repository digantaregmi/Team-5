package com.example.moodymovie.repository;

import com.example.moodymovie.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT u FROM User u WHERE u.email = ?1
            """)
    User findByEmailIgnoreCase(String email);
}
