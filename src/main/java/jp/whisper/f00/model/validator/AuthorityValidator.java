package jp.whisper.f00.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import jp.whisper.f00.model.Authority;

public class AuthorityValidator implements Validator 
{
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz)
	{
		return Authority.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) 
	{
		//TODO
		//ValidationUtils.rejectIfEmpty(errors, "name", "NAME_REQUIRED", "名称不能为空");
	}

}

