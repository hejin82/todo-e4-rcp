package com.example.e4.rcp.todo.service;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.service.internal.MyTodoModelImpl;

/**
 * Provide access to the model service
 * 
 * @author vogella
 * 
 */
public class ToDoService {

	private static ITodoModel todoService = new MyTodoModelImpl();

	public static ITodoModel getInstance() {
		return todoService;
	}
}
