package com.example.e4.rcp.todo.service.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.e4.rcp.todo.model.ITodoModel;
import com.example.e4.rcp.todo.model.Todo;


public class MyTodoModelImpl implements ITodoModel {
	static int current = 1;
	private List<Todo> model;

	public MyTodoModelImpl() {
		model = createInitialModel();
	}

	// Always return a new copy of the data
	@Override
	public List<Todo> getTodos() {
		ArrayList<Todo> list = new ArrayList<Todo>();
		for (Todo todo : model) {
			list.add(todo.copy());
		}
		return list;
	}

	@Override
	public boolean saveTodo(Todo todo) {
		deleteTodo(todo.getId());
		return model.add(todo);
	}

	@Override
	public Todo getTodo(long id) {
		for (Todo todo : model) {
			if (todo.getId() == id) {
				return todo.copy();
			}
		}
		return null;
	}

	@Override
	public boolean deleteTodo(long id) {

		for (Todo todo : model) {
			if (todo.getId() == id)
				return model.remove(todo);
		}
		return false;
	}

	// Example data, change if you like
	private List<Todo> createInitialModel() {
		try {
			Thread.sleep(00);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<Todo> list = new ArrayList<Todo>();
		list.add(createTodo("SWT", "Learn Widgets"));
		list.add(createTodo("JFace", "Especially Viewers!"));
		list.add(createTodo("DI", "@Inject looks interesting"));
		list.add(createTodo("OSGi", "Services"));
		list.add(createTodo("Compatibility Layer", "Run Eclipse 3.x plug-ins "));
		return list;
	}

	public Todo createTodo(String summary, String description) {
		return new Todo(current++, summary, description, false, new Date());
	}
}