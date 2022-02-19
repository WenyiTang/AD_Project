package com.example.adproject.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultJson {
	private float[] mealTrack = new float[2];
	private float[] feeling = new float[4];


}
