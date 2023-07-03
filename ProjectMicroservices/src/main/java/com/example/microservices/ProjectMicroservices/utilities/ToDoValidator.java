package com.example.microservices.ProjectMicroservices.utilities;
import com.example.microservices.ProjectMicroservices.entities.ToDo;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

    public class ToDoValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return ToDo.class.equals(clazz);
        }

        @Override
        public void validate(Object obj, Errors errors) {
            ToDo toDo = (ToDo) obj;
//            priority from ToDo entities
            String priority = toDo.getPriority();

            if(!"high".equals(priority) && !"low".equals(priority)){
                errors.rejectValue("priority", "Priority must be 'high' or 'low'!");
            }

        }



}
