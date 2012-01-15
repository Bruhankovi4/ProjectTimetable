
--
-- Name: academicdegrees; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE academicdegrees (
    id_degree integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.academicdegrees OWNER TO postgres;

--
-- Name: academicdegrees_id_degree_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE academicdegrees_id_degree_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.academicdegrees_id_degree_seq OWNER TO postgres;

--
-- Name: academicdegrees_id_degree_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE academicdegrees_id_degree_seq OWNED BY academicdegrees.id_degree;


--
-- Name: academicdegrees_id_degree_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('academicdegrees_id_degree_seq', 1, false);


--
-- Name: auditory; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE auditory (
    id_auditory integer NOT NULL,
    ref_type smallint,
    title character varying(20) NOT NULL
);


ALTER TABLE public.auditory OWNER TO postgres;

--
-- Name: auditory_id_auditory_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE auditory_id_auditory_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auditory_id_auditory_seq OWNER TO postgres;

--
-- Name: auditory_id_auditory_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE auditory_id_auditory_seq OWNED BY auditory.id_auditory;


--
-- Name: auditory_id_auditory_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('auditory_id_auditory_seq', 1, false);


--
-- Name: auditorytype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE auditorytype (
    id_type integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.auditorytype OWNER TO postgres;

--
-- Name: auditorytype_id_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE auditorytype_id_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auditorytype_id_type_seq OWNER TO postgres;

--
-- Name: auditorytype_id_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE auditorytype_id_type_seq OWNED BY auditorytype.id_type;


--
-- Name: auditorytype_id_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('auditorytype_id_type_seq', 9, true);


--
-- Name: cathedras; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cathedras (
    id_cathedra integer NOT NULL,
    title character varying(80) NOT NULL
);


ALTER TABLE public.cathedras OWNER TO postgres;

--
-- Name: cathedras_id_cathedra_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cathedras_id_cathedra_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cathedras_id_cathedra_seq OWNER TO postgres;

--
-- Name: cathedras_id_cathedra_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cathedras_id_cathedra_seq OWNED BY cathedras.id_cathedra;


--
-- Name: cathedras_id_cathedra_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cathedras_id_cathedra_seq', 4, true);


--
-- Name: concretelessons; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE concretelessons (
    id_lesson integer NOT NULL,
    ref_syst smallint NOT NULL,
    ref_teacher smallint NOT NULL,
    subgroupnumber smallint NOT NULL,
    week_day smallint NOT NULL,
    lessonnumber smallint NOT NULL,
    weektype smallint NOT NULL,
    ref_aud smallint,
    hournumber smallint NOT NULL
);


ALTER TABLE public.concretelessons OWNER TO postgres;

--
-- Name: concretelessons_id_lesson_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE concretelessons_id_lesson_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.concretelessons_id_lesson_seq OWNER TO postgres;

--
-- Name: concretelessons_id_lesson_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE concretelessons_id_lesson_seq OWNED BY concretelessons.id_lesson;


--
-- Name: concretelessons_id_lesson_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('concretelessons_id_lesson_seq', 1, false);


--
-- Name: grouplesson; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE grouplesson (
    ref_lesson smallint NOT NULL,
    ref_group smallint NOT NULL
);


ALTER TABLE public.grouplesson OWNER TO postgres;

--
-- Name: groups; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE groups (
    id_group integer NOT NULL,
    ref_year smallint NOT NULL,
    crew_size smallint,
    group_number smallint
);


ALTER TABLE public.groups OWNER TO postgres;

--
-- Name: groups_id_group_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE groups_id_group_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.groups_id_group_seq OWNER TO postgres;

--
-- Name: groups_id_group_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE groups_id_group_seq OWNED BY groups.id_group;


--
-- Name: groups_id_group_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('groups_id_group_seq', 62, true);


--
-- Name: lessontype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE lessontype (
    id_type integer NOT NULL,
    title character varying(20) NOT NULL
);


ALTER TABLE public.lessontype OWNER TO postgres;

--
-- Name: lessontype_id_type_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE lessontype_id_type_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lessontype_id_type_seq OWNER TO postgres;

--
-- Name: lessontype_id_type_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE lessontype_id_type_seq OWNED BY lessontype.id_type;


--
-- Name: lessontype_id_type_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lessontype_id_type_seq', 2, true);


--
-- Name: specialties; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE specialties (
    id_specialty integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.specialties OWNER TO postgres;

--
-- Name: specialties_id_specialty_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE specialties_id_specialty_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.specialties_id_specialty_seq OWNER TO postgres;

--
-- Name: specialties_id_specialty_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE specialties_id_specialty_seq OWNED BY specialties.id_specialty;


--
-- Name: specialties_id_specialty_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('specialties_id_specialty_seq', 4, true);


--
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subjects (
    id_subject integer NOT NULL,
    title character varying(50) NOT NULL
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- Name: subjects_id_subject_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subjects_id_subject_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subjects_id_subject_seq OWNER TO postgres;

--
-- Name: subjects_id_subject_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE subjects_id_subject_seq OWNED BY subjects.id_subject;


--
-- Name: subjects_id_subject_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subjects_id_subject_seq', 10, true);


--
-- Name: subjectyear; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subjectyear (
    id_sy integer NOT NULL,
    ref_year smallint NOT NULL,
    ref_subject smallint NOT NULL
);


ALTER TABLE public.subjectyear OWNER TO postgres;

--
-- Name: subjectyear_id_sy_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subjectyear_id_sy_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subjectyear_id_sy_seq OWNER TO postgres;

--
-- Name: subjectyear_id_sy_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE subjectyear_id_sy_seq OWNED BY subjectyear.id_sy;


--
-- Name: subjectyear_id_sy_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subjectyear_id_sy_seq', 49, true);


--
-- Name: subjectyearcathedra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subjectyearcathedra (
    ref_sy smallint NOT NULL,
    ref_cathedra smallint NOT NULL
);


ALTER TABLE public.subjectyearcathedra OWNER TO postgres;

--
-- Name: subjectyearsemestertype; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subjectyearsemestertype (
    id_syst integer NOT NULL,
    subgroups_count smallint NOT NULL,
    hours_count smallint NOT NULL,
    ref_audtype smallint,
    ref_type smallint,
    ref_sy smallint NOT NULL,
    semester smallint NOT NULL
);


ALTER TABLE public.subjectyearsemestertype OWNER TO postgres;

--
-- Name: subjectyearsemestertype_id_syst_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE subjectyearsemestertype_id_syst_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subjectyearsemestertype_id_syst_seq OWNER TO postgres;

--
-- Name: subjectyearsemestertype_id_syst_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE subjectyearsemestertype_id_syst_seq OWNED BY subjectyearsemestertype.id_syst;


--
-- Name: subjectyearsemestertype_id_syst_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('subjectyearsemestertype_id_syst_seq', 100, true);


--
-- Name: teachercathedra; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE teachercathedra (
    ref_teacher smallint NOT NULL,
    ref_cathedra smallint NOT NULL,
    loading smallint
);


ALTER TABLE public.teachercathedra OWNER TO postgres;

--
-- Name: teachers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE teachers (
    id_teacher integer NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    father_name character varying(20) NOT NULL,
    academic_degree smallint,
    phone_number character varying(15),
    mobile_number character varying(15),
    email character varying(50)
);


ALTER TABLE public.teachers OWNER TO postgres;

--
-- Name: teachers_id_teacher_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE teachers_id_teacher_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.teachers_id_teacher_seq OWNER TO postgres;

--
-- Name: teachers_id_teacher_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE teachers_id_teacher_seq OWNED BY teachers.id_teacher;


--
-- Name: teachers_id_teacher_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('teachers_id_teacher_seq', 10, true);


--
-- Name: years; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE years (
    id_year integer NOT NULL,
    ref_specialty smallint NOT NULL,
    year smallint NOT NULL
);


ALTER TABLE public.years OWNER TO postgres;

--
-- Name: years_id_year_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE years_id_year_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.years_id_year_seq OWNER TO postgres;

--
-- Name: years_id_year_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE years_id_year_seq OWNED BY years.id_year;


--
-- Name: years_id_year_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('years_id_year_seq', 40, true);


--
-- Name: id_degree; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE academicdegrees ALTER COLUMN id_degree SET DEFAULT nextval('academicdegrees_id_degree_seq'::regclass);


--
-- Name: id_auditory; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE auditory ALTER COLUMN id_auditory SET DEFAULT nextval('auditory_id_auditory_seq'::regclass);


--
-- Name: id_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE auditorytype ALTER COLUMN id_type SET DEFAULT nextval('auditorytype_id_type_seq'::regclass);


--
-- Name: id_cathedra; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE cathedras ALTER COLUMN id_cathedra SET DEFAULT nextval('cathedras_id_cathedra_seq'::regclass);


--
-- Name: id_lesson; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE concretelessons ALTER COLUMN id_lesson SET DEFAULT nextval('concretelessons_id_lesson_seq'::regclass);


--
-- Name: id_group; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE groups ALTER COLUMN id_group SET DEFAULT nextval('groups_id_group_seq'::regclass);


--
-- Name: id_type; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE lessontype ALTER COLUMN id_type SET DEFAULT nextval('lessontype_id_type_seq'::regclass);


--
-- Name: id_specialty; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE specialties ALTER COLUMN id_specialty SET DEFAULT nextval('specialties_id_specialty_seq'::regclass);


--
-- Name: id_subject; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE subjects ALTER COLUMN id_subject SET DEFAULT nextval('subjects_id_subject_seq'::regclass);


--
-- Name: id_sy; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE subjectyear ALTER COLUMN id_sy SET DEFAULT nextval('subjectyear_id_sy_seq'::regclass);


--
-- Name: id_syst; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE subjectyearsemestertype ALTER COLUMN id_syst SET DEFAULT nextval('subjectyearsemestertype_id_syst_seq'::regclass);


--
-- Name: id_teacher; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE teachers ALTER COLUMN id_teacher SET DEFAULT nextval('teachers_id_teacher_seq'::regclass);


--
-- Name: id_year; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE years ALTER COLUMN id_year SET DEFAULT nextval('years_id_year_seq'::regclass);


--
-- Data for Name: academicdegrees; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: auditory; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: auditorytype; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO auditorytype VALUES (2, 'Компьютерный класс');
INSERT INTO auditorytype VALUES (3, 'Малая лекционная аудитория');
INSERT INTO auditorytype VALUES (4, 'Лекционная аудитория');
INSERT INTO auditorytype VALUES (5, 'Большая лекционная аудитория');


--
-- Data for Name: cathedras; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cathedras VALUES (1, 'Теоретической и прикладной информатики');
INSERT INTO cathedras VALUES (2, 'Математического анализа');
INSERT INTO cathedras VALUES (3, 'Дифференциальных уравнений');
INSERT INTO cathedras VALUES (4, 'Теории функций');


--
-- Data for Name: concretelessons; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: grouplesson; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO groups VALUES (23, 21, NULL, 1);
INSERT INTO groups VALUES (24, 21, NULL, 2);
INSERT INTO groups VALUES (25, 22, NULL, 1);
INSERT INTO groups VALUES (26, 23, NULL, 1);
INSERT INTO groups VALUES (27, 24, NULL, 1);
INSERT INTO groups VALUES (28, 24, NULL, 2);
INSERT INTO groups VALUES (29, 24, NULL, 3);
INSERT INTO groups VALUES (30, 25, NULL, 1);
INSERT INTO groups VALUES (31, 25, NULL, 2);
INSERT INTO groups VALUES (32, 25, NULL, 3);
INSERT INTO groups VALUES (33, 26, NULL, 1);
INSERT INTO groups VALUES (34, 27, NULL, 1);
INSERT INTO groups VALUES (35, 27, NULL, 2);
INSERT INTO groups VALUES (36, 27, NULL, 3);
INSERT INTO groups VALUES (37, 28, NULL, 1);
INSERT INTO groups VALUES (38, 28, NULL, 2);
INSERT INTO groups VALUES (39, 29, NULL, 1);
INSERT INTO groups VALUES (40, 29, NULL, 2);
INSERT INTO groups VALUES (41, 30, NULL, 1);
INSERT INTO groups VALUES (42, 30, NULL, 2);
INSERT INTO groups VALUES (43, 31, NULL, 1);
INSERT INTO groups VALUES (44, 31, NULL, 2);
INSERT INTO groups VALUES (45, 32, NULL, 1);
INSERT INTO groups VALUES (46, 32, NULL, 2);
INSERT INTO groups VALUES (47, 33, NULL, 1);
INSERT INTO groups VALUES (48, 34, NULL, 1);
INSERT INTO groups VALUES (49, 35, NULL, 1);
INSERT INTO groups VALUES (50, 36, NULL, 1);
INSERT INTO groups VALUES (51, 36, NULL, 2);
INSERT INTO groups VALUES (52, 36, NULL, 3);
INSERT INTO groups VALUES (53, 37, NULL, 1);
INSERT INTO groups VALUES (54, 37, NULL, 2);
INSERT INTO groups VALUES (55, 37, NULL, 3);
INSERT INTO groups VALUES (56, 38, NULL, 1);
INSERT INTO groups VALUES (57, 38, NULL, 2);
INSERT INTO groups VALUES (58, 39, NULL, 1);
INSERT INTO groups VALUES (59, 39, NULL, 2);
INSERT INTO groups VALUES (60, 39, NULL, 3);
INSERT INTO groups VALUES (61, 40, NULL, 1);
INSERT INTO groups VALUES (62, 40, NULL, 2);


--
-- Data for Name: lessontype; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lessontype VALUES (1, 'ЛЕК');
INSERT INTO lessontype VALUES (2, 'ПЗ');


--
-- Data for Name: specialties; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO specialties VALUES (1, 'Информатика');
INSERT INTO specialties VALUES (2, 'Прикладная математика');
INSERT INTO specialties VALUES (3, 'Математика');
INSERT INTO specialties VALUES (4, 'Механика');


--
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subjects VALUES (6, 'Методы оптимизации');
INSERT INTO subjects VALUES (7, 'Теория компиляции');
INSERT INTO subjects VALUES (8, 'Программирование');
INSERT INTO subjects VALUES (9, 'Сети');
INSERT INTO subjects VALUES (10, 'Теория случайных процессов');


--
-- Data for Name: subjectyear; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subjectyear VALUES (1, 21, 7);
INSERT INTO subjectyear VALUES (2, 21, 8);
INSERT INTO subjectyear VALUES (3, 21, 10);
INSERT INTO subjectyear VALUES (4, 22, 9);
INSERT INTO subjectyear VALUES (5, 23, 6);
INSERT INTO subjectyear VALUES (6, 24, 7);
INSERT INTO subjectyear VALUES (7, 24, 8);
INSERT INTO subjectyear VALUES (8, 24, 9);
INSERT INTO subjectyear VALUES (9, 24, 10);
INSERT INTO subjectyear VALUES (10, 25, 6);
INSERT INTO subjectyear VALUES (11, 25, 7);
INSERT INTO subjectyear VALUES (12, 25, 8);
INSERT INTO subjectyear VALUES (13, 25, 9);
INSERT INTO subjectyear VALUES (14, 26, 6);
INSERT INTO subjectyear VALUES (15, 26, 10);
INSERT INTO subjectyear VALUES (16, 27, 6);
INSERT INTO subjectyear VALUES (17, 27, 8);
INSERT INTO subjectyear VALUES (18, 28, 6);
INSERT INTO subjectyear VALUES (19, 28, 8);
INSERT INTO subjectyear VALUES (20, 29, 6);
INSERT INTO subjectyear VALUES (21, 29, 8);
INSERT INTO subjectyear VALUES (22, 30, 7);
INSERT INTO subjectyear VALUES (23, 30, 9);
INSERT INTO subjectyear VALUES (24, 30, 10);
INSERT INTO subjectyear VALUES (25, 31, 6);
INSERT INTO subjectyear VALUES (26, 31, 7);
INSERT INTO subjectyear VALUES (27, 33, 7);
INSERT INTO subjectyear VALUES (28, 33, 8);
INSERT INTO subjectyear VALUES (29, 33, 10);
INSERT INTO subjectyear VALUES (30, 34, 6);
INSERT INTO subjectyear VALUES (31, 34, 7);
INSERT INTO subjectyear VALUES (32, 34, 8);
INSERT INTO subjectyear VALUES (33, 34, 9);
INSERT INTO subjectyear VALUES (34, 35, 6);
INSERT INTO subjectyear VALUES (35, 35, 7);
INSERT INTO subjectyear VALUES (36, 35, 9);
INSERT INTO subjectyear VALUES (37, 35, 10);
INSERT INTO subjectyear VALUES (38, 36, 6);
INSERT INTO subjectyear VALUES (39, 36, 7);
INSERT INTO subjectyear VALUES (40, 36, 8);
INSERT INTO subjectyear VALUES (41, 37, 7);
INSERT INTO subjectyear VALUES (42, 38, 7);
INSERT INTO subjectyear VALUES (43, 38, 9);
INSERT INTO subjectyear VALUES (44, 38, 10);
INSERT INTO subjectyear VALUES (45, 39, 6);
INSERT INTO subjectyear VALUES (46, 39, 8);
INSERT INTO subjectyear VALUES (47, 39, 9);
INSERT INTO subjectyear VALUES (48, 40, 7);
INSERT INTO subjectyear VALUES (49, 40, 10);


--
-- Data for Name: subjectyearcathedra; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subjectyearcathedra VALUES (5, 3);
INSERT INTO subjectyearcathedra VALUES (5, 4);
INSERT INTO subjectyearcathedra VALUES (1, 2);
INSERT INTO subjectyearcathedra VALUES (1, 3);
INSERT INTO subjectyearcathedra VALUES (6, 2);
INSERT INTO subjectyearcathedra VALUES (6, 3);
INSERT INTO subjectyearcathedra VALUES (7, 3);
INSERT INTO subjectyearcathedra VALUES (3, 1);
INSERT INTO subjectyearcathedra VALUES (3, 2);
INSERT INTO subjectyearcathedra VALUES (3, 3);
INSERT INTO subjectyearcathedra VALUES (14, 1);
INSERT INTO subjectyearcathedra VALUES (14, 2);
INSERT INTO subjectyearcathedra VALUES (14, 3);
INSERT INTO subjectyearcathedra VALUES (16, 4);
INSERT INTO subjectyearcathedra VALUES (18, 2);
INSERT INTO subjectyearcathedra VALUES (18, 4);
INSERT INTO subjectyearcathedra VALUES (20, 2);
INSERT INTO subjectyearcathedra VALUES (17, 1);
INSERT INTO subjectyearcathedra VALUES (17, 2);
INSERT INTO subjectyearcathedra VALUES (17, 4);
INSERT INTO subjectyearcathedra VALUES (19, 1);
INSERT INTO subjectyearcathedra VALUES (19, 3);
INSERT INTO subjectyearcathedra VALUES (19, 4);
INSERT INTO subjectyearcathedra VALUES (21, 1);
INSERT INTO subjectyearcathedra VALUES (21, 2);
INSERT INTO subjectyearcathedra VALUES (15, 2);
INSERT INTO subjectyearcathedra VALUES (15, 3);
INSERT INTO subjectyearcathedra VALUES (26, 1);
INSERT INTO subjectyearcathedra VALUES (26, 3);
INSERT INTO subjectyearcathedra VALUES (27, 3);
INSERT INTO subjectyearcathedra VALUES (31, 2);
INSERT INTO subjectyearcathedra VALUES (32, 3);
INSERT INTO subjectyearcathedra VALUES (29, 2);
INSERT INTO subjectyearcathedra VALUES (38, 1);
INSERT INTO subjectyearcathedra VALUES (38, 2);
INSERT INTO subjectyearcathedra VALUES (38, 3);
INSERT INTO subjectyearcathedra VALUES (45, 2);
INSERT INTO subjectyearcathedra VALUES (45, 3);
INSERT INTO subjectyearcathedra VALUES (39, 2);
INSERT INTO subjectyearcathedra VALUES (41, 1);
INSERT INTO subjectyearcathedra VALUES (41, 4);
INSERT INTO subjectyearcathedra VALUES (46, 2);
INSERT INTO subjectyearcathedra VALUES (46, 3);
INSERT INTO subjectyearcathedra VALUES (43, 1);
INSERT INTO subjectyearcathedra VALUES (43, 4);
INSERT INTO subjectyearcathedra VALUES (47, 3);
INSERT INTO subjectyearcathedra VALUES (44, 4);


--
-- Data for Name: subjectyearsemestertype; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subjectyearsemestertype VALUES (72, 2, 2, NULL, 1, 1, 1);
INSERT INTO subjectyearsemestertype VALUES (73, 4, 2, NULL, 2, 1, 2);
INSERT INTO subjectyearsemestertype VALUES (74, 1, 4, NULL, 1, 6, 2);
INSERT INTO subjectyearsemestertype VALUES (75, 6, 6, NULL, 2, 6, 2);
INSERT INTO subjectyearsemestertype VALUES (76, 3, 6, NULL, 2, 7, 2);
INSERT INTO subjectyearsemestertype VALUES (77, 2, 1, NULL, 2, 4, 2);
INSERT INTO subjectyearsemestertype VALUES (78, 6, 2, NULL, 1, 16, 2);
INSERT INTO subjectyearsemestertype VALUES (79, 6, 4, NULL, 2, 16, 2);
INSERT INTO subjectyearsemestertype VALUES (80, 4, 6, NULL, 2, 18, 1);
INSERT INTO subjectyearsemestertype VALUES (81, 2, 1, NULL, 1, 20, 2);
INSERT INTO subjectyearsemestertype VALUES (82, 1, 1, NULL, 2, 20, 1);
INSERT INTO subjectyearsemestertype VALUES (83, 1, 2, NULL, 1, 17, 1);
INSERT INTO subjectyearsemestertype VALUES (84, 4, 4, NULL, 2, 19, 2);
INSERT INTO subjectyearsemestertype VALUES (85, 2, 4, NULL, 1, 21, 1);
INSERT INTO subjectyearsemestertype VALUES (86, 2, 2, NULL, 1, 25, 1);
INSERT INTO subjectyearsemestertype VALUES (87, 1, 2, NULL, 1, 30, 1);
INSERT INTO subjectyearsemestertype VALUES (88, 2, 4, NULL, 1, 26, 2);
INSERT INTO subjectyearsemestertype VALUES (89, 4, 2, NULL, 2, 26, 1);
INSERT INTO subjectyearsemestertype VALUES (90, 1, 2, NULL, 2, 28, 2);
INSERT INTO subjectyearsemestertype VALUES (91, 1, 6, NULL, 1, 29, 1);
INSERT INTO subjectyearsemestertype VALUES (92, 1, 4, NULL, 2, 29, 1);
INSERT INTO subjectyearsemestertype VALUES (93, 1, 4, NULL, 2, 45, 2);
INSERT INTO subjectyearsemestertype VALUES (94, 3, 1, NULL, 2, 39, 2);
INSERT INTO subjectyearsemestertype VALUES (95, 6, 4, NULL, 1, 41, 2);
INSERT INTO subjectyearsemestertype VALUES (96, 4, 4, NULL, 2, 42, 2);
INSERT INTO subjectyearsemestertype VALUES (97, 6, 4, NULL, 2, 40, 2);
INSERT INTO subjectyearsemestertype VALUES (98, 4, 4, NULL, 2, 43, 1);
INSERT INTO subjectyearsemestertype VALUES (99, 3, 6, NULL, 1, 47, 2);
INSERT INTO subjectyearsemestertype VALUES (100, 1, 2, NULL, 2, 44, 1);


--
-- Data for Name: teachercathedra; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO teachercathedra VALUES (6, 1, NULL);
INSERT INTO teachercathedra VALUES (7, 1, NULL);
INSERT INTO teachercathedra VALUES (8, 1, NULL);
INSERT INTO teachercathedra VALUES (10, 1, NULL);
INSERT INTO teachercathedra VALUES (8, 2, NULL);
INSERT INTO teachercathedra VALUES (9, 2, NULL);
INSERT INTO teachercathedra VALUES (10, 2, NULL);
INSERT INTO teachercathedra VALUES (6, 3, NULL);
INSERT INTO teachercathedra VALUES (7, 3, NULL);
INSERT INTO teachercathedra VALUES (8, 3, NULL);
INSERT INTO teachercathedra VALUES (10, 3, NULL);
INSERT INTO teachercathedra VALUES (6, 4, NULL);
INSERT INTO teachercathedra VALUES (8, 4, NULL);


--
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO teachers VALUES (6, 'Ирина', 'Зарецкая', 'Тимофеевна', NULL, NULL, NULL, NULL);
INSERT INTO teachers VALUES (7, 'Кирилл', 'Руккас', 'Маркович', NULL, NULL, NULL, NULL);
INSERT INTO teachers VALUES (8, 'Артем', 'Янцевич', 'Артемович', NULL, NULL, NULL, NULL);
INSERT INTO teachers VALUES (9, 'Татьяна', 'Дуравкина', 'Викторовна', NULL, NULL, NULL, NULL);
INSERT INTO teachers VALUES (10, 'Сергей', 'Окрут', 'Иванович', NULL, NULL, NULL, NULL);


--
-- Data for Name: years; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO years VALUES (21, 1, 1);
INSERT INTO years VALUES (22, 1, 2);
INSERT INTO years VALUES (23, 1, 3);
INSERT INTO years VALUES (24, 1, 4);
INSERT INTO years VALUES (25, 1, 5);
INSERT INTO years VALUES (26, 2, 1);
INSERT INTO years VALUES (27, 2, 2);
INSERT INTO years VALUES (28, 2, 3);
INSERT INTO years VALUES (29, 2, 4);
INSERT INTO years VALUES (30, 2, 5);
INSERT INTO years VALUES (31, 3, 1);
INSERT INTO years VALUES (32, 3, 2);
INSERT INTO years VALUES (33, 3, 3);
INSERT INTO years VALUES (34, 3, 4);
INSERT INTO years VALUES (35, 3, 5);
INSERT INTO years VALUES (36, 4, 1);
INSERT INTO years VALUES (37, 4, 2);
INSERT INTO years VALUES (38, 4, 3);
INSERT INTO years VALUES (39, 4, 4);
INSERT INTO years VALUES (40, 4, 5);


--
-- Name: academicdegrees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY academicdegrees
    ADD CONSTRAINT academicdegrees_pkey PRIMARY KEY (id_degree);


--
-- Name: auditory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY auditory
    ADD CONSTRAINT auditory_pkey PRIMARY KEY (id_auditory);


--
-- Name: auditorytype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY auditorytype
    ADD CONSTRAINT auditorytype_pkey PRIMARY KEY (id_type);


--
-- Name: cathedras_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cathedras
    ADD CONSTRAINT cathedras_pkey PRIMARY KEY (id_cathedra);


--
-- Name: concretelessons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY concretelessons
    ADD CONSTRAINT concretelessons_pkey PRIMARY KEY (id_lesson);


--
-- Name: concretelessonuniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY concretelessons
    ADD CONSTRAINT concretelessonuniq UNIQUE (ref_syst, subgroupnumber, hournumber);


--
-- Name: grouplesson_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grouplesson
    ADD CONSTRAINT grouplesson_pkey PRIMARY KEY (ref_lesson, ref_group);


--
-- Name: groups_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (id_group);


--
-- Name: groupsuniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groupsuniq UNIQUE (ref_year, group_number);


--
-- Name: lessontype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lessontype
    ADD CONSTRAINT lessontype_pkey PRIMARY KEY (id_type);


--
-- Name: specialties_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY specialties
    ADD CONSTRAINT specialties_pkey PRIMARY KEY (id_specialty);


--
-- Name: subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (id_subject);


--
-- Name: subjectyear_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjectyear
    ADD CONSTRAINT subjectyear_pkey PRIMARY KEY (id_sy);


--
-- Name: subjectyearcathedra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjectyearcathedra
    ADD CONSTRAINT subjectyearcathedra_pkey PRIMARY KEY (ref_sy, ref_cathedra);


--
-- Name: subjectyearsemestertyoeuniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjectyearsemestertype
    ADD CONSTRAINT subjectyearsemestertyoeuniq UNIQUE (ref_type, ref_sy, semester);


--
-- Name: subjectyearsemestertype_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjectyearsemestertype
    ADD CONSTRAINT subjectyearsemestertype_pkey PRIMARY KEY (id_syst);


--
-- Name: subjectyearuniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subjectyear
    ADD CONSTRAINT subjectyearuniq UNIQUE (ref_year, ref_subject);


--
-- Name: teachercathedra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY teachercathedra
    ADD CONSTRAINT teachercathedra_pkey PRIMARY KEY (ref_teacher, ref_cathedra);


--
-- Name: teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id_teacher);


--
-- Name: uniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY grouplesson
    ADD CONSTRAINT uniq UNIQUE (ref_lesson, ref_group);


--
-- Name: years_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY years
    ADD CONSTRAINT years_pkey PRIMARY KEY (id_year);


--
-- Name: yearsuniq; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY years
    ADD CONSTRAINT yearsuniq UNIQUE (ref_specialty, year);


--
-- Name: auditory_ref_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY auditory
    ADD CONSTRAINT auditory_ref_type_fkey FOREIGN KEY (ref_type) REFERENCES auditorytype(id_type);


--
-- Name: concretelessons_ref_aud_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY concretelessons
    ADD CONSTRAINT concretelessons_ref_aud_fkey FOREIGN KEY (ref_aud) REFERENCES auditory(id_auditory);


--
-- Name: concretelessons_ref_syst_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY concretelessons
    ADD CONSTRAINT concretelessons_ref_syst_fkey FOREIGN KEY (ref_syst) REFERENCES subjectyearsemestertype(id_syst);


--
-- Name: concretelessons_ref_teacher_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY concretelessons
    ADD CONSTRAINT concretelessons_ref_teacher_fkey FOREIGN KEY (ref_teacher) REFERENCES teachers(id_teacher);


--
-- Name: grouplesson_ref_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY grouplesson
    ADD CONSTRAINT grouplesson_ref_group_fkey FOREIGN KEY (ref_group) REFERENCES groups(id_group);


--
-- Name: grouplesson_ref_lesson_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY grouplesson
    ADD CONSTRAINT grouplesson_ref_lesson_fkey FOREIGN KEY (ref_lesson) REFERENCES concretelessons(id_lesson);


--
-- Name: groups_ref_year_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_ref_year_fkey FOREIGN KEY (ref_year) REFERENCES years(id_year);


--
-- Name: subjectyear_ref_subject_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyear
    ADD CONSTRAINT subjectyear_ref_subject_fkey FOREIGN KEY (ref_subject) REFERENCES subjects(id_subject);


--
-- Name: subjectyear_ref_year_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyear
    ADD CONSTRAINT subjectyear_ref_year_fkey FOREIGN KEY (ref_year) REFERENCES years(id_year);


--
-- Name: subjectyearcathedra_ref_cathedra_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyearcathedra
    ADD CONSTRAINT subjectyearcathedra_ref_cathedra_fkey FOREIGN KEY (ref_cathedra) REFERENCES cathedras(id_cathedra);


--
-- Name: subjectyearcathedra_ref_sy_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyearcathedra
    ADD CONSTRAINT subjectyearcathedra_ref_sy_fkey FOREIGN KEY (ref_sy) REFERENCES subjectyear(id_sy);


--
-- Name: subjectyearsemestertype_ref_audtype_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyearsemestertype
    ADD CONSTRAINT subjectyearsemestertype_ref_audtype_fkey FOREIGN KEY (ref_audtype) REFERENCES auditorytype(id_type);


--
-- Name: subjectyearsemestertype_ref_sy_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyearsemestertype
    ADD CONSTRAINT subjectyearsemestertype_ref_sy_fkey FOREIGN KEY (ref_sy) REFERENCES subjectyear(id_sy);


--
-- Name: subjectyearsemestertype_ref_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subjectyearsemestertype
    ADD CONSTRAINT subjectyearsemestertype_ref_type_fkey FOREIGN KEY (ref_type) REFERENCES lessontype(id_type);


--
-- Name: teachercathedra_ref_cathedra_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachercathedra
    ADD CONSTRAINT teachercathedra_ref_cathedra_fkey FOREIGN KEY (ref_cathedra) REFERENCES cathedras(id_cathedra);


--
-- Name: teachercathedra_ref_teacher_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachercathedra
    ADD CONSTRAINT teachercathedra_ref_teacher_fkey FOREIGN KEY (ref_teacher) REFERENCES teachers(id_teacher);


--
-- Name: teachers_academic_degree_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT teachers_academic_degree_fkey FOREIGN KEY (academic_degree) REFERENCES academicdegrees(id_degree);


--
-- Name: years_ref_specialty_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY years
    ADD CONSTRAINT years_ref_specialty_fkey FOREIGN KEY (ref_specialty) REFERENCES specialties(id_specialty);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

