package com.MyRealTrainer.service;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class UtilService {
   
	public double approximateNumber(Double number) { //Approxiamate a Double to 2 decimal places
		double approximatedNumber=(double)Math.round(number * 100d) / 100d;
		return approximatedNumber;
	}

	// Adding or resting time to a date: days, hours or any other type 
    // Example: utilService.addFecha(fechaSalida, Calendar.MINUTE, minutosSumar);
	public Date addDate(Date initialDate, int dateType, int amountToAdd ) { 
		   Calendar calendar = Calendar.getInstance();
		      calendar.setTime(initialDate); 
		      calendar.add(dateType, amountToAdd);
		      return calendar.getTime();
	   }
	
    
}
