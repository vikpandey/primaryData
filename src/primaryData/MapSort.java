package primaryData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 
 * @author vikas
 *
 */

public class MapSort {

	private Map<String, Integer> map;
	private List<Entry<String, Integer>> listOfEntries;
	private Set<Entry<String, Integer>> entries;
	private LinkedHashMap<String, Integer> sortedByValue;
	private Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {

		@Override
		public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
			Integer v1 = e1.getValue();
			Integer v2 = e2.getValue();
			return v1.compareTo(v2);
		}
	};

	public MapSort(Map<String, Integer> map) {
		this.map = map;
	}

	private List<Entry<String, Integer>> listEntries() {
		// Sort method needs a List, so let's first convert Set to List in Java
		entries = map.entrySet();
		listOfEntries = new ArrayList<>(entries);

		return listOfEntries;
	}

	private List<Entry<String, Integer>> sort() {
		// sorting HashMap by values using comparator
		List<Entry<String, Integer>> listEntries = listEntries();
		Collections.sort(listEntries, valueComparator);
		Collections.reverse(listEntries);

		return listEntries;
	}

	private LinkedHashMap<String, Integer> sortByValue(List<Entry<String, Integer>> sortResult) {

		sortResult = sort();

		sortedByValue = new LinkedHashMap<String, Integer>(sortResult.size());

		// copying entries from List to Map
		for (Entry<String, Integer> entry : sortResult) {
			sortedByValue.put(entry.getKey(), entry.getValue());
		}

		return sortedByValue;
	}

		public void printSorted(int n) {

			sortedByValue = sortByValue(listOfEntries);
			System.out.println("Frequency of words from highest to lowest ");
			entries = sortedByValue.entrySet();
		
	        int i = 0;
			for (Entry<String, Integer> mapping : entries) {
				if(i < n) {
				System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
				i++;
				}
			}
		}
	 
	public Map<String, Integer> getMap() {
		return map;
	}

	public void setMap(Map<String, Integer> map) {
		this.map = map;
	}

}
