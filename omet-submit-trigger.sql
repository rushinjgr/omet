--Justin Rushin III jgr10@pitt.edu
--this sql file must be executed to create the necessary constraint described in task 1
create or replace trigger omet_submit_trigger after insert or update ON surveydata
FOR EACH row

BEGIN

UPDATE Surveys
SET
sum_q1 = (sum_q1 + :new.q1),
sum_q2 = (sum_q2 + :new.q2),
sum_q3 = (sum_q3 + :new.q3),
sum_q4 = (sum_q4 + :new.q4),
num_submitted = (num_submitted + 1)
WHERE survey_id = :new.survey_id;
END;
/
