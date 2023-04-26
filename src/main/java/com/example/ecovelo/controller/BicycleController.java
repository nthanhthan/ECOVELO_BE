package com.example.ecovelo.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.service.BicycleService;


import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bicycle")
public class BicycleController {
	private final BicycleService bicycleService;
	 @PostMapping("/checkQR")
	  public ResponseEntity<Boolean>checkQR(
			  @RequestBody String id
	  ) {
	    return ResponseEntity.ok(bicycleService.checkExistBicycleID(id));
	  }
}