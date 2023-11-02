--Inserting into Persona table
INSERT INTO Persona VALUES ("Linus Sebastian", "croppedpersona1.png", "27-year-old environmental scientist"),
                            ("Elliot Wellick", "croppedpersona2.png", "48-year-old high school science teacher"),
                            ("Academic Andy", "croppedpersona3.png", "Young adult higher-education student");

SELECT * FROM Persona;

--Inserting into PersonaAttribute table 
--!--(redundant now due to helper CSV file code.)--!
INSERT INTO PersonaAttribute (PersonaName, AttributeType, Description) VALUES ("Linus Sebastian", "Description", "Linus, a 27-year-old environmental activist, is dedicated to reducing climate change. He holds a degree in environmental science and has been employed by a non-profit that conducts research into climate change."),
                                    ("Linus Sebastian", "Needs", "To find trends and patterns in temperature data."), 
                                    ("Linus Sebastian", "Needs", "Linus needs a simple tool that he can use to rapidly and properly assess the data."),
                                    ("Linus Sebastian", "Needs", "He is interested in using graphs and visuals to assist in spreading awareness of the importance of combating climate change while he works as an environmental activist."),
                                    ("Linus Sebastian", "Goals", "Linus's goal is to use his knowledge and abilities to improve the environment. He wants to take part in a movement that promotes action to combat climate change and increases public awareness of it. He needs a simple tool that will enable him to precisely and quickly assess temperature data."),
                                    ("Linus Sebastian", "Skills & Experience", "Linus has a degree in Environmental Science and some experience working in climate change research. He is comfortable working with data and has some experience with data analysis tools. However, he is not an expert in this area and would benefit from a tool that is easy to use and requires minimal technical expertise."),
                                    ("Elliot Wellick", "Description", "Elliot Wellick is a 48-year-old high school science teacher with a strong interest in climate change. He has been teaching for over a decade and is always looking for new ways to engage his students in learning about science and the environment using graphs."),
                                    ("Elliot Wellick", "Needs", "Access to up-to-date and accurate information on climate change and its impacts on the environment. Clear and concise educational resources that can be easily integrated into his lesson plans. Tools and visual aids that can help him explain concepts in a way that is easy for his students to understand like graphs."),
                                    ("Elliot Wellick", "Goals", "To educate his students about the science of climate change and its potential impacts on their lives. To find new and innovative ways to teach his students about climate change."),
                                    ("Elliot Wellick", "Skills & Experience", "He is a teacher. Familiar with a range of educational technology tools and comfortable incorporating them into lesson plans."),
                                    ("Academic Andy", "Description", "Andy is a young adult higher-education student that has a deep interest in self-improvement and learning new things."),
                                    ("Academic Andy", "Needs", "Accurate information. A good level of detail in data. References to avoid plagiarism and verify the credibility."),
                                    ("Academic Andy", "Goals", "To read meaningful and useful information. To see clear diagrams and data visualisations."),
                                    ("Academic Andy", "Skills & Experience", "Good reading comprehension. Experienced with technology, uses an LMS, eCommerce sites, social media sites, etc.");
                                
SELECT Name FROM Persona ORDER BY Image_FilePath;
SELECT AttributeType FROM PersonaAttribute WHERE AttributeType ="Description";
SELECT AttributeType FROM PersonaAttribute WHERE AttributeType = 'Description';
