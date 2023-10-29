package com.example.edo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    // Обработка исключения общего типа Exception
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(HttpServletRequest request, Exception exception){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "Произошла ошибка.");
        return modelAndView;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle404Error(HttpServletRequest request, Exception exception){
        ModelAndView modelAndView = new ModelAndView("404Error");
        modelAndView.addObject("errorMessage", "Запрашиваемая страница не найдена.");
        return modelAndView;
    }
}
