package com.example.android_advancedgrigview2;

import java.util.ArrayList;

public class Group 
{
	ArrayList <Person> people = new ArrayList <Person>();
	
	public void addPerson(String name, String lastName, int id)
	{
		Person person = new Person(name, lastName, id);
		people.add(person);
	}
}
