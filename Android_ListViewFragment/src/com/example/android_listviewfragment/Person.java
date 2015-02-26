package com.example.android_listviewfragment;

class Person
{
	public String firstName;
	public String lastName;
	
	Person(String fname, String lname)
	{
		firstName = fname;
		lastName = lname;
	}
	
	public String toString()
	{
		return firstName + " " + lastName;
	}
}
