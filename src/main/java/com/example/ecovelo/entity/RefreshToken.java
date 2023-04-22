package com.example.ecovelo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String token;
//	trạng thái token nếu đã bị thu hồi = true
	private boolean revoked;
//  trạng thái token nếu hết hạn = true
	private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number")
    private AccountModel accountModel;	
}
