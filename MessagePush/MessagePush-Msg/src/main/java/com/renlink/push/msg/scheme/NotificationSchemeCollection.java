package com.renlink.push.msg.scheme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NotificationSchemeCollection implements List<NotificationScheme>,
		Serializable {

	private static final long serialVersionUID = 1L;
	private static List<NotificationScheme> list = new ArrayList<NotificationScheme>();

	@Override
	public boolean add(NotificationScheme element) {
		return list.add(element);
	}

	@Override
	public void add(int index, NotificationScheme element) {
		list.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends NotificationScheme> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int index,
			Collection<? extends NotificationScheme> collection) {
		return list.addAll(index, collection);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object obj) {
		return list.contains(obj);
	}

	@Override
	public boolean containsAll(Collection<?> conllection) {
		return list.containsAll(conllection);
	}

	@Override
	public NotificationScheme get(int index) {
		return list.get(index);
	}

	@Override
	public int indexOf(Object obj) {
		return list.indexOf(obj);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<NotificationScheme> iterator() {
		return list.iterator();
	}

	@Override
	public int lastIndexOf(Object obj) {
		return list.lastIndexOf(obj);
	}

	@Override
	public ListIterator<NotificationScheme> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<NotificationScheme> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public boolean remove(Object obj) {
		return list.remove(obj);
	}

	@Override
	public NotificationScheme remove(int index) {
		return list.remove(index);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return list.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return list.retainAll(collection);
	}

	@Override
	public NotificationScheme set(int index, NotificationScheme element) {
		return list.set(index, element);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public List<NotificationScheme> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

}
