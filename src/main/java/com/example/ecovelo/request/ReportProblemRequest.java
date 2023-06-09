package com.example.ecovelo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReportProblemRequest {
	String description;
	String idBicycle;
	String urlImage;
	int idProblem;
	String token;
}
