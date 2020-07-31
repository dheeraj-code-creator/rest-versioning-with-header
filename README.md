# rest-versioning-with-header

http://localhost:9091/user-management/swagger-ui/index.html?configUrl=%2Fuser-management%2Fv3%2Fapi-docs%2Fswagger-config 



======================================================================================================================

package com.springboot.arrayList.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class ArrayListDemo {

	// maintains no order, i.e displays data in insertion order.
	
	public static void main(String[] args) {
       List list = new ArrayList();
       list.add("A");
       list.add("Banti");
       list.add("Z");
       list.add("C");
       list.add("E");
       list.add("Dheeraj");
       
       Iterator itr = list.iterator();
   	  while(itr.hasNext()) {
   		  System.out.println(itr.next());
   	  }
	}

}
============================================================================================================

package com.springboot.hashMap.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HashMapExample {
 
/*	Map is a collection of Entry. (Map is a outer interface and Entry is a inner interfaces)
	    Each Entry is having a (key, value) pair.
	    Entry is having 3 methods(getKey(), getValue(), setKey())
	    for doing operation in keys and values we have to call Map.Entry (outer interface.inner interface)
	    example: 1. entrySet
	             2. Map.Entry
	             3. then apply Entry methods on keys and values.
	HashMap works on the priniple of hashing. (Hashing means conversion of big string to shorter one.)
	HashMap uses HashTable data structure.
	HashMap doesnot provide insertion oreder.
	HashMap display data based on the hashcode.
	    hashcode is generated only for keys not for values.
	    Bydefault capacity of HashMap is 16. (i.e 16 buckets will be created once we create the object of HashMap)
	    Bydefault fill ratio/load factor of HashMap is 0.75. (i.e once 75% of Entry<K, V> is filled in all the buckets, 
	                                                          after that it will create new object).
	    bucket is also called nodes.                                                      
	    if we want we can change both capacity and ratio by using different constructor.
	HashMap doesn't contain duplicate keys, but having duplicate values.
	    Ex:(int rollNumber, String names)
	HashMap contains only one null key. (if we provide more than one null key then it will not satisfy above line.) 
	HashMap is mostly use when we want to perform search operations.
	
	when we call put(key, value) method then,
	1. hashcode will be generated for the key, and using hashmap formula bucket/nodes will be decided.
	2. bucket/node capacity is 16 (i.e 0,1,2,3,4.....15)
	3. lets suppose formula calculated index is 4.
	4. then key,value and hashcode for the key all three(key,value,hascode) will be stored to bucket/node 4.
	
	when we call get(key) method then,
	1. already generated hashcode for the key is used.
	2. after using hashmap formula bucket/nodes will be decided.
	3. then get(key) hashcode and put(key) hascode will be check.
	4. if both hashcode are same, then .equals() methods is called, for cheking both key values.
	5. if both key are also same, then that (key,value) will be retruned.
	
	contract:
	* if two objects are equal then they must have same hashcode.
	
*/    
	    
	public static void main(String[] args) {
		
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		hashMap.put("Dheeraj", 500);
		hashMap.put("Banti", 1000);
		hashMap.put("ABC", 1500);
		hashMap.put("XYZ", 2000);
		
		// display whole hashMap object, No insertion order, based on the hashcode data will display.
		System.out.println(hashMap);
		System.out.println("=================================================================================");
		
		// display all Entry, No insertion order, based on the hashcode data will display.
		Set setObj1 = hashMap.entrySet();
		System.out.println(setObj1);
		System.out.println("=================================================================================");
		
		// display all keys, based on the hashcode keys will display.
		Set setObj = hashMap.keySet();
		System.out.println(setObj);
		System.out.println("=================================================================================");
		
		// display all values, based on the hashcode of keys.
		Collection collection = hashMap.values();
		System.out.println(collection);
		System.out.println("=================================================================================");
		
		// replace existing value of key and return same existing value. but in Entry new value will be stored.
		System.out.println(hashMap.put("ABC", 5555));
		System.out.println("=================================================================================");
		
		// Now in this Entry new value will come (ABC, 5555).
		Set setObj2 = hashMap.entrySet();
		System.out.println(setObj2);
		System.out.println("=================================================================================");
		
		// now its time for iteration.
		Set setObj3 = hashMap.entrySet();
		Iterator itr = setObj3.iterator();
		while(itr.hasNext()) {
			Map.Entry<String, Integer> m = (Entry<String, Integer>) itr.next();
			System.out.println("key=" +m.getKey() + " values=" + m.getValue());
		}
	}

}
===========================================================================================================
package com.springboot.hashSet.demo;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetDemo {

/*  HashSet doesn't allows duplicate elements.
	   if duplicate elements are present in th HashSet, then it will not throw any compile and runtime error, but duplicat will not be print.
	HashSet display data based on hashcode. 
*/
	   
	public static void main(String[] args) {
          HashSet hashSet = new HashSet();
      	  hashSet.add("A");
      	  hashSet.add("Banti");
      	  hashSet.add("Z");
      	  hashSet.add("D");
      	  hashSet.add("C");
      	  hashSet.add("Dheeraj");
      	  
      	  Iterator itr = hashSet.iterator();
      	  while(itr.hasNext()) {
      		  System.out.println(itr.next());
      	  }
	}

}
=============================================================================================================
package com.springboot.hashTable.demo;

import java.util.Hashtable;
import java.util.Map;

public class HashTableDemo {

/*  HashTable uses HashTable datastrucure.
	HashTable initial capacity is 11. (i.e 11 buckets will be created.)
	HashTable fill ratio/load factor is 0.75 (i.e after 75% of Entry will be filled, then new HashTable object will be created.)
	HashTable displays data based on hashcode.
	HashTable is synchronized.
*/
	
	public static void main(String[] args) {
		Hashtable<String, Integer> hashTable = new Hashtable<String, Integer>();
		hashTable.put("Dheeraj", 500);
		hashTable.put("Banti", 1000);
		hashTable.put("ABC", 1500);
		hashTable.put("XYZ", 2000);
		for(Map.Entry map: hashTable.entrySet()) {
			System.out.println(map);
		}

	}

}
==============================================================================================================
package com.springboot.linkedHashMap.demo;

import java.util.LinkedHashMap;

public class LinkedHashMapDemo {

	/*LinkedHashMap display data's in insertion order.
	   above is the difference for both LinkedHashMap and HashMap
	   
	*/
	public static void main(String[] args) {
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		linkedHashMap.put("Dheeraj", 500);
		linkedHashMap.put("Banti", 1000);
		linkedHashMap.put("ABC", 1500);
		linkedHashMap.put("XYZ", 2000);
		
		System.out.println(linkedHashMap);

	}

}
=================================================================================================================
package com.springboot.linkedHashSet.demo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class LinkedHashSetDemo {

	// LinkedHashSet displays data in insertion order.
	public static void main(String[] args) {
		 LinkedHashSet linkedHashSet = new LinkedHashSet();
     	  linkedHashSet.add("A");
     	  linkedHashSet.add("Banti");
     	  linkedHashSet.add("Z");
     	  linkedHashSet.add("D");
     	  linkedHashSet.add("C");
     	  linkedHashSet.add("Dheeraj");
     	  
     	 Iterator itr = linkedHashSet.iterator();
     	  while(itr.hasNext()) {
     		  System.out.println(itr.next());
     	  }
	}

}
======================================================================================================================
package com.springboot.linkedList.demo;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListDemo {

	// displays data in insertion order.
	
	public static void main(String[] args) {

		LinkedList linkedList = new LinkedList();
		 linkedList.add("A");
	     linkedList.add("Banti");
	     linkedList.add("Z");
	     linkedList.add("C");
	     linkedList.add("E");
	     linkedList.add("Dheeraj");
	     
	     Iterator itr = linkedList.iterator();
	   	  while(itr.hasNext()) {
	   		  System.out.println(itr.next());
	   	  }
	}

}
====================================================================================================================
package com.springboot.properties.demo;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesDemo {

	// Properties class is a subclass of HashTable.
	// Properties contains (k,v) pair both as String.
	public static void main(String[] args) {

		Properties prop = System.getProperties();
		System.out.println(prop);
		
		//by iterator
		/*Set set = prop.entrySet();
		Iterator itr = set.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}*/
		
		// by for-in loop
		for(Map.Entry map: prop.entrySet()) {
			System.out.println(map.getKey() + " = " + map.getValue());
		}
	}

}

