-- Database: "TimeTable"

-- DROP DATABASE "TimeTable";

CREATE DATABASE "TimeTable"
  WITH OWNER = leonov
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Ukrainian_Ukraine.1251'
       LC_CTYPE = 'Ukrainian_Ukraine.1251'
       CONNECTION LIMIT = -1;
       
--=====================================================--



CREATE TABLE Subjects (
	id_subject serial,
	title varchar(50) NOT NULL,
	primary key (id_subject)
);


--BEGIN>>>_______Specialty_|_Year_|_Group___:
CREATE TABLE Specialties (
	id_specialty serial,
	title varchar(50) NOT NULL,
	primary key (id_specialty)
);
  --|
  CREATE TABLE Years (
	  id_year serial,
	  ref_specialty smallint references Specialties (id_specialty) NOT NULL,
	  "year" smallint NOT NULL,
	  primary key (id_year),
	  UNIQUE(ref_specialty, "year")
  );
  --ALTER TABLE Years ADD CONSTRAINT yearsUniq UNIQUE(ref_specialty, "year");
--|
CREATE TABLE Groups (
	id_group serial,
	ref_year smallint references Years (id_year) NOT NULL,
	group_Number smallint NOT NULL,
	crew_size smallint,
	primary key (id_group),
	UNIQUE(ref_year, groupNumber)
);
--ALTER TABLE Groups ADD CONSTRAINT groupsUniq UNIQUE(ref_year, group_Number);
--__________________________<<<END.



    --|	   Subject <-M----N-> Year
    CREATE TABLE SubjectYear(
	    id_SY serial,
	    ref_year smallint references Years (id_year) NOT NULL,
	    ref_subject smallint references Subjects (id_subject) NOT NULL,
	    primary key (id_SY),
	    UNIQUE(ref_year, ref_subject)
    );
--ALTER TABLE SubjectYear ADD CONSTRAINT subjectYearUniq UNIQUE(ref_year, ref_subject);



 --BEGIN>>>_______AcademicDegree_|_Teacher_|_Cathedra_|_TeacherCathedra___:
CREATE TABLE AcademicDegrees (
	id_degree serial,
	title varchar(50) NOT NULL,
	primary key (id_degree)
);

CREATE TABLE Teachers (
	id_teacher serial,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	father_name varchar(20) NOT NULL,
	academic_degree smallint references AcademicDegrees (id_degree), -- for expansion
	phone_number varchar(15),					 --
	mobile_number varchar(15),					 -- 
	email varchar(50),						 -- 
	primary key (id_teacher)
);

CREATE TABLE Cathedras (
	id_cathedra serial,
	title varchar(80) NOT NULL,
--	ref_faculty smallint references Faculties (id_faculty),		-- for expansion
	primary key (id_cathedra)
);

    --|	   Teacher <-M----N-> Cathedra
    CREATE TABLE TeacherCathedra (
	    ref_teacher smallint references Teachers (id_teacher) NOT NULL,
	    ref_cathedra smallint references Cathedras (id_cathedra) NOT NULL,
	    loading smallint,						-- for expansion 
	    primary key (ref_teacher, ref_cathedra)
    );
--__________________________<<<END.



	CREATE TABLE SubjectYearCathedra (
		ref_SY smallint references SubjectYear (id_SY) NOT NULL,
		ref_Cathedra smallint references Cathedras (id_cathedra) NOT NULL,
	--	semester smallint NOT NULL,					-- 1 or 2
		primary key (ref_SY, ref_Cathedra)
	);
	-- ALTER TABLE SubjectYearCathedra RENAME COLUMN id_SYCS TO id_SYC;
	-- DROP TABLE SubjectYearCathedra;


	  /*  CREATE TABLE SubjectYearSemester (
		    id_SYS serial,
		    ref_SYC smallint references SubjectYearCathedra (id_SYC) NOT NULL,
		    semester smallint NOT NULL,					-- 1 or 2
		    primary key (id_SYS)
	    );*/


	    CREATE TABLE LessonType (
		    id_type serial,
		    title varchar(20) NOT NULL,
		    primary key (id_type)
	    );
	 -- ALTER TABLE SubjectType RENAME TO LessonType;
	

	    CREATE TABLE AuditoryType (
		    id_type serial,
		    title varchar(20) NOT NULL,
		    primary key (id_type)
	    )


		CREATE TABLE SubjectYearSemesterType (
			id_SYST serial,
		--	ref_SYS smallint references SubjectYearSemester (id_SYS) NOT NULL,
			ref_type smallint references LessonType (id_type) NOT NULL,
			ref_audType smallint references AuditoryType (id_type),
			ref_SY smallint references SubjectYear (id_SY) NOT NULL,
			subgroups_count smallint NOT NULL,
			hours_count smallint NOT NULL,		-- in 2 weeks
			semester smallint NOT NULL,
			primary key (id_SYST),
			UNIQUE(ref_type, ref_SY, semester)
		);
	--	ALTER TABLE SubjectYearSemesterType ADD CONSTRAINT subjectYearSemesterTyoeUniq UNIQUE(ref_type, ref_SY, semester);

	--	ALTER TABLE SubjectYearSemesterType ADD COLUMN ref_type smallint references LessonType (id_type);
	--	ALTER TABLE SubjectYearSemesterType ADD COLUMN semester smallint NOT NULL;
	--	ALTER TABLE SubjectYearSemesterType ADD COLUMN ref_audType smallint references AuditoryType (id_type);
	--	ALTER TABLE SubjectYearSemesterType ADD COLUMN ref_SY smallint references SubjectYear (id_SY) NOT NULL;
	--	ALTER TABLE SubjectYearSemesterType DROP COLUMN ref_type;
	--	ALTER TABLE SubjectYearSemesterType DROP COLUMN ref_SYC;

		CREATE TABLE Auditory (
		    id_auditory serial,
		    ref_type smallint references AuditoryType (id_type),
		    title varchar(20) NOT NULL,
		    primary key (id_auditory)
		)

		
			CREATE TABLE ConcreteLessons (
				id_lesson serial,
				ref_SYST smallint references SubjectYearSemesterType (id_SYST) NOT NULL,
				ref_teacher smallint references Teachers (id_teacher) NOT NULL,
				ref_aud smallint references Auditory (id_auditory),
				subgroupNumber smallint NOT NULL,
				hourNumber smallint NOT NULL,		-- recovered
						--^^^ if in 2 weeks are many lessons with this type
				week_day smallint NOT NULL,
				lessonNumber smallint NOT NULL,
				weekType smallint NOT NULL,
						--^^^ STANDART-1/UPPER-2/LOWER-3
				primary key (id_lesson),
				UNIQUE(ref_SYST, subgroupNumber, hourNumber)
			);
		--	ALTER TABLE ConcreteLessons ADD COLUMN hourNumber smallint NOT NULL;
		--	ALTER TABLE ConcreteLessons DROP COLUMN hourNumber;
		--	ALTER TABLE ConcreteLessons ADD CONSTRAINT concreteLessonUniq UNIQUE(ref_SYST, subgroupNumber, hourNumber);

	CREATE TABLE GroupLesson (
		ref_lesson smallint references ConcreteLessons (id_lesson),
		ref_group  smallint references Groups (id_group),
		primary key (ref_lesson, ref_group),
		UNIQUE(ref_lesson, ref_group)
	);
--	ALTER TABLE GroupLesson ADD CONSTRAINT uniq UNIQUE(ref_lesson, ref_group);