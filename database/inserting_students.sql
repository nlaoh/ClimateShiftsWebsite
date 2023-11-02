--Inserting into Student table
INSERT INTO Student VALUES ("s4006840", "Natchanon", "Laoharawee", "s4006840@student.rmit.edu.au");
INSERT INTO Student VALUES ("s4018548", "Russell", "Sheikh", "s4018548@student.rmit.edu.au");

INSERT INTO Role VALUES ("Lead Designer"), ("Database Developer"), ("UX Designer");

INSERT INTO StudentRole VALUES ("Lead Designer", "s4006840"),
                                ("Lead Designer", "s4018548"),
                                ("Database Developer", "s4006840"),
                                ("Database Developer", "s4018548"),
                                ("UX Designer", "s4006840"),
                                ("UX Designer", "s4018548");
                                
INSERT INTO StudentCreatedPersona VALUES ("s4006840", "Academic Andy"), ("s4018548", "Linus Sebastian"), ("s4018548", "Elliot Wellick");