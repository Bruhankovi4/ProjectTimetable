package model;


public class CollisionException extends Exception implements Constants {
	
	private static final long serialVersionUID = 1L;

	private byte type;
	
	private Teacher teacher = null;
	private Lesson teacherLesson;		// lesson, which conflict with the our lesson by teacher
	private Auditory auditory = null;
	private Lesson auditoryLesson;		// lesson, which conflict with the our lesson by auditory
	private Lesson lesson;
	private Time time;

	public CollisionException(
			Teacher teacher, 
			Auditory auditory, 
			Lesson lesson, 
			byte type,
			Time time) {
		this.type = type;
		this.teacher = teacher;
		this.auditory = auditory;
		this.lesson = lesson;
		this.time = time;
		teacherLesson = teacher.getLessonAt(time);
		auditoryLesson = auditory.getLessonAt(time);
	}
	
	public CollisionException(Teacher teacher, Lesson lesson, Time time) {
		this.type = TEACHER;
		this.teacher = teacher;
		this.lesson = lesson;
		this.time = time;
		teacherLesson = teacher.getLessonAt(time);
	}
	
	public CollisionException(Auditory auditory, Lesson lesson, Time time) {
		this.type = AUDITORY;
		this.auditory = auditory;
		this.lesson = lesson;
		this.time = time;
		auditoryLesson = auditory.getLessonAt(time);
	}
	
//-- FOR CATCH{...} BLOCK: -------------------------------------------------------
	
//== generate collision: =========
	public void skip() {		// when you want to use it, you must update table (with painting collisions)
								// Collision must be fixed!
		allowCollision();
		addAs(SKIPED);
	}
	
	public void ignore() {		// when you want to use it, you must update table (with painting collisions)
								// Collision can be unfixed.
		allowCollision();
		addAs(IGNORED);
	}
	
	private void allowCollision() {
		if (teacher != null){
			teacher.addLesson(lesson);
			lesson.coerciveSetTeacher(teacher);
		}
		if (auditory != null){
			auditory.addLesson(lesson);
			lesson.coerciveSetAuditory(auditory);
		}
		lesson.coerciveSetTime(time);
	}
	
	private void addAs(byte mark) {
		Collision collision;
		if (type == TEACHER_AND_AUDITORY){
			collision = new Collision(teacher, teacherLesson, lesson, mark);
			lesson.addCollision(collision);
			teacherLesson.addCollision(collision);
			collision = new Collision(auditory, auditoryLesson, lesson, mark);
			lesson.addCollision(collision);
			auditoryLesson.addCollision(collision);
		}
		else{
			if (type == TEACHER){
				collision = new Collision(teacher, teacherLesson, lesson, mark);
				lesson.addCollision(collision);
				teacherLesson.addCollision(collision);
			}else {
				collision = new Collision(auditory, auditoryLesson, lesson, SKIPED);
				lesson.addCollision(collision);
				auditoryLesson.addCollision(collision);
			}
		}
	}
	
//== avoid collision: ============
	public void okey(){			// may be must be renamed
		/*do nothing*/
	}
	
	public void moveOld(){
		if (teacher != null){
			if (lesson.getTeacher() != null)				// collision will be created | without it
				lesson.getTeacher().deleteLesson(lesson);	// fixme (here i must search collision with teacher)
			teacher.addLesson(lesson);
			lesson.coerciveSetTeacher(teacher);
		}
		if (auditory != null){
			if (lesson.getAuditory() != null)				// collision will be created | without it
				lesson.getAuditory().deleteLesson(lesson);	// fixme (here i must search collision with auditory)
			auditory.addLesson(lesson);
			lesson.coerciveSetAuditory(auditory);
		}
	}
	
	
	public String toString() {
		if (type == TEACHER_AND_AUDITORY)
			return	teacher.toString() + ":" +
				"	" + lesson.toStringForException() +
				"	" + teacherLesson.toStringForException() +
					auditory.getTitle() + ":" +
				"	" + lesson.toStringForException() +
				"	" + auditoryLesson.toStringForException();
		if (type == TEACHER)
			return	teacher.toString() + ":" +
				"	" + lesson.toStringForException() +
				"	" + teacherLesson.toStringForException();
		// if (type == AUDITORY)
			return	auditory.getTitle() + ":" +
				"	" + lesson.toStringForException() +
				"	" + auditoryLesson.toStringForException();
	}
}
