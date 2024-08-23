package br.com.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.task_manager.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
    UserModel findByEmail(String email);
}
