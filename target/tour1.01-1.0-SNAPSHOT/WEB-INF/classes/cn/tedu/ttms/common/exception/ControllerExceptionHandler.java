package cn.tedu.ttms.common.exception;

import cn.tedu.ttms.common.web.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//全局异常处理对象：通过此类实现所有controller中的异常处理
//@ControllerAdvice声明的类可以作为统一异常处理对象
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ServiceException.class)
	@ResponseBody
	public JsonResult handleServiceException(ServiceException e){
		e.printStackTrace();
		return new JsonResult(e);
	}
}
