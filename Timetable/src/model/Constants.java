package model;

import java.awt.Color;

public interface Constants {

	// CollisionException type
	byte TEACHER_AND_AUDITORY=0;
	byte TEACHER=1;
	byte AUDITORY=2;
	
	// Auditory type
	byte USUAL=3;
	byte COMPUTER=4;
	
	// Lesson type
	byte LACTION=5;
	byte PRACTICAL=6;
	
	// Week type
	byte FULL=7;
	byte UPPER=8;
	byte LOWER=9;
	byte ANY_FLASHER=10;
	
	// Collision type
	byte SKIPED=11;
	byte IGNORED=12;
	byte ACTIVE=13;	
	
	// Colors for interface
	Color darkBlue = new Color(0,10,35);
	Color DIM = new Color(170,170,200);
	Color SELECTED_DIM = new Color(230,230,245);
	
	// selection type
	byte SELECTED_FOR_ADDING=14;
	byte SELECTED_FOR_EDITING=15;
	byte SELECTED_FOR_SWITCHING=16;
}
