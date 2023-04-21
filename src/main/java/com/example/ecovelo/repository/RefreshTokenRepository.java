package com.example.ecovelo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecovelo.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
	@Query(value = """
		      select t from RefreshToken t inner join AccountModel a\s
		      on t.accountModel.phoneNumber = a.phoneNumber\s
		      where a.phoneNumber = :phoneNumber\s
		      """)
	List<RefreshToken> findAllValidTokenByUser(String phoneNumber);

	Optional<RefreshToken> findByToken(String token);
}
