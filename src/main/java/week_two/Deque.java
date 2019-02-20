package week_two;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private int size;
  private Node head, tail;

  public Deque() {
    size = 0;
    head = tail = null;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(Item item) {
    checkArgument(item);

    Node oldHead = head;

    head = new Node();
    head.item = item;
    head.next = oldHead;

    head.previous = null;

    if(oldHead != null) {
      oldHead.previous = head;
    }

    if(tail == null) {
      tail = head;
    }

    size++;
  }

  public void addLast(Item item) {
    if(item == null) {
      throw new IllegalArgumentException();
    }

    Node oldTail = tail;

    tail = new Node();
    tail.item = item;
    tail.previous = oldTail;
    tail.next = null;

    if(oldTail != null) {
      oldTail.next = tail;
    }

    if(head == null) {
      head = tail;
    }

    size++;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldHead = head;

    if (head.next != null) {
      head = head.next;
      head.previous = null;
    } else {
      tail = head = null;
    }

    size--;

    return oldHead.item;
  }

  public Item removeLast() {
    if(isEmpty()) {
      throw new NoSuchElementException();
    }

    Node oldTail = tail;

    if(tail.previous != null) {
      tail = tail.previous;
      tail.next = null;
    } else {
      head = tail = null;
    }
    size--;

    return oldTail.item;
  }

  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private void checkArgument(Item item) {
    if(item == null) {
      throw new IllegalArgumentException();
    }
  }

  private class Node {
    Item item;
    Node next;
    Node previous;
  }

  private class DequeIterator implements Iterator<Item> {

    private Node current = head;

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public Item next() {
      Item item = current.item;
      current = current.next;
      return item;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
