package com.application.game.repository;



import com.application.game.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {


    User findByMobileNo( String mobileNo);
}
