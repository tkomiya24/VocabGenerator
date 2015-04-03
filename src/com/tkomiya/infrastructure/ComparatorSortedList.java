package com.tkomiya.infrastructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ComparatorSortedList<E> implements List<E> {
	
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
	
	public E get(int index) {
		return list.get(index);
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

	@Override
	public boolean add(E e) {
		int index = findIndex(e);
		list.add(index, e);
		return true;
	}

	@Override
	public void add(int arg0, E arg1) {
		throw new RuntimeException("Do not insert things in a specific index to a sorted list.");
	}

	@Override
	public boolean addAll(Collection<? extends E> es) {
		if (!list.addAll(es)) {
			return false;
		} else {
			sort();
			return true;
		}
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		throw new RuntimeException("Do not insert things in a specific index to a sorted list.");
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return list.containsAll(arg0);
	}

	@Override
	public int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}

	@Override
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	@Override
	public boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	@Override
	public E remove(int arg0) {
		return list.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return list.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	@Override
	public E set(int arg0, E arg1) {
		throw new RuntimeException("Do not insert things in a specific index to a sorted list.");
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public List<E> subList(int arg0, int arg1) {
		return list.subList(arg0, arg1);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		return list.toArray(arg0);
	}

	/**
	 * @return the list
	 */
	public ArrayList<E> getList() {
		return list;
	}

}

