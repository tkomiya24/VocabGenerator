package com.tkomiya.infrastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ComparatorSortedList<E> {
	
	private ArrayList<E> list;
	private Comparator<E> comparator;
	
	public ComparatorSortedList(Comparator<E> comparator) {
		this.comparator = comparator;
		list = new ArrayList<E>();
	}
	
	public ComparatorSortedList(ArrayList<E> unSortedList, Comparator<E> comparator) {
		this.list = unSortedList;
		this.comparator = comparator;
		sort();
	}
	
	public void add(E e) {
		int index = findIndex(e);
		list.add(index, e);
	}
	
	public E get(int index) {
		return list.get(index);
	}

	public void addAll(Collection<E> es) {
		list.addAll(es);
		sort();
	}

	private int findIndex(E e) {
		int index = 0;
		for(; index < list.size(); index++) {
			if (comparator.compare(e, list.get(index)) <= 0) {
				return index;
			}
		}
		return index;
	}
	
	private void sort() {
		Collections.sort(list, comparator);
	}
}
