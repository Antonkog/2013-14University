package com.example.android_advanced_listview;

import java.util.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.AdapterView.*;
import android.widget.AbsListView.OnScrollListener;
import android.widget.*;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener
{
	RelativeLayout layout;
	ListView listView1;
	Button button1;
	EditText edit1;
	EditText edit2;
	
	PersonAdapter personAdapter;
	ArrayList<Person> people=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		people = new ArrayList<Person>();
		people.add(new Person("Ivan", "Petrov"));
		people.add(new Person("Anna", "Karenina"));
		people.add(new Person("Vasya", "Pupkin"));
		
		listView1 = (ListView) findViewById(R.id.listView1);
		button1 = (Button) findViewById(R.id.button1);
		edit1 = (EditText) findViewById(R.id.editText1);
		edit2 = (EditText) findViewById(R.id.editText2);
		
		button1.setOnClickListener(this);
		
		// Указать xml-файл с шаблоном пункта
		personAdapter = new PersonAdapter(this, R.layout.person_item, people);

	    listView1.setAdapter(personAdapter);
	    
	    listView1.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) 
	{
		Person person = (Person)(listView1.getItemAtPosition(position));
	
		Toast.makeText(this, person.firstName + " " + person.lastName, Toast.LENGTH_SHORT).show();
		
		// Сообщить адаптеру, что пользователь нажал на пункт
		personAdapter.inverseSelection(position);
		
	    ArrayList<Integer> checkedPos = personAdapter.getCurrentCheckedPosition();
	    for(Integer pos : checkedPos)
	    {
	    	Log.d("OK!", people.get(pos).toString());
	    }
	}

	@Override
	public void onClick(View v) 
	{
		Editable fname = edit1.getText();
		Editable lname = edit2.getText();
		Person person = new Person(fname.toString(), lname.toString());
		personAdapter.add(person);
	}

}

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

class PersonAdapter extends ArrayAdapter<Person>
{
    int resource;
    String response;
    Context context;
    private HashMap<Integer, Boolean> selection = new HashMap<Integer, Boolean>();
    List<Person> people = null;

    public PersonAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
        this.resource=resource;
        people = items;
    }
    
    public void add(Person p)
    {
    	people.add(0, p);
    	selection.put(0, false);
		notifyDataSetChanged();
    }
    
    // Инвертировать выделение пункта
    public void inverseSelection(int position) 
    {
    	if(selection.containsKey(position))
    	{
    		boolean sel = selection.get(position);
	        selection.put(position, !sel);
    	} else selection.put(position, true);
    	notifyDataSetChanged();
    }
    
    // Изменить выделение пункта
    public void setNewSelection(int position, boolean value) {
        selection.put(position, value);
        notifyDataSetChanged();
    }

    // Выяснить, выделен ли пункт
    public boolean isPositionChecked(int position) {
        Boolean result = selection.get(position);
        return result == null ? false : result;
    }

    // Получить массив с номерами выделенных элементов
    public ArrayList<Integer> getCurrentCheckedPosition() {
    	ArrayList <Integer> res = new ArrayList<Integer>();
    	for(Integer n : selection.keySet()){
    		if(selection.get(n)) res.add(n);
    	}
        return res;
    }

    // Снять выделение пункта
    public void removeSelection(int position) {
        selection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }
     
    // Отрендерить пункт
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout personView;
        Person person = getItem(position);
         
        if(convertView==null)
        {
        	// Получить объект, загружающий layout из файла
            personView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            
            // Загрузить layout из xml-файла
            vi.inflate(resource, personView, true);
        }
        else
        {
            personView = (LinearLayout) convertView;
        }

        // Получить ссылки на элементы управления
        TextView fName = (TextView)personView.findViewById(R.id.firstName);
        TextView lName = (TextView)personView.findViewById(R.id.lastName);
        
        fName.setText(person.firstName);
        lName.setText(person.lastName);
        
        CheckBox checked = (CheckBox)personView.findViewById(R.id.isChecked);
        if (selection.containsKey(position)) checked.setChecked(isPositionChecked(position));
         
        // Возвратить отрендеренный пункт
        return personView;
    }

}
