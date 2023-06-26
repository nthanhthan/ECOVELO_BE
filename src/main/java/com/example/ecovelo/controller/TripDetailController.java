package com.example.ecovelo.controller;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecovelo.request.TripDetailReq;
import com.example.ecovelo.response.DetailJourneyResponse;
import com.example.ecovelo.response.DetailTripResponse;
import com.example.ecovelo.service.TripDetailService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.ByteArrayOutputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trip")
public class TripDetailController {
	final private TripDetailService tripService;

	@PostMapping("/addTripDetail")
	public ResponseEntity<Void> createCoodinate(@RequestBody TripDetailReq request) {
		boolean isSuccess = tripService.createTripDetail(request);
		if (isSuccess) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GetMapping("/getListTrip")
	public ResponseEntity<List<DetailJourneyResponse>> getListTrip(
			@RequestHeader(name = "Authorization") String token) {
		final String jwt = token.substring(7);
		return ResponseEntity.ok(tripService.getListJourney(jwt));

	}

	@GetMapping("/exportTrip")
	public ResponseEntity<byte[]> exportToExcel() throws IOException {
		List<DetailTripResponse> listTrip = tripService.getAllTrip();
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Data");
			int rowIndex = 0;
			Row headerRow = sheet.createRow(rowIndex++);
			headerRow.createCell(0).setCellValue("ID");
			headerRow.createCell(1).setCellValue("Lat");
			headerRow.createCell(2).setCellValue("Lng");
			headerRow.createCell(3).setCellValue("IDRENT");

			for (DetailTripResponse d : listTrip) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(d.getId());
				row.createCell(1).setCellValue(d.getLat());
				row.createCell(2).setCellValue(d.getLng());
				row.createCell(3).setCellValue(d.getRentBicycleModel());
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workbook.write(outputStream);
			byte[] byteArray = outputStream.toByteArray();

			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.xlsx");
			headers.setContentType(
					MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			headers.setContentLength(byteArray.length);

			return new ResponseEntity<>(byteArray, headers, HttpStatus.OK);
		}
	}

}
