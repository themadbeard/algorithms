package week_two;

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

  private Object[] container;
  private int size = 0;


  public RandomizedQueue() {
    container = new Object[1];
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if(size == container.length) {
      resize(2 * container.length);
    }

    container[size++] = item;

    size++;
  }

  public Item dequeue() {
    assertNotEmpty();

    int randomIndex = randomIndex();
    Item result = (Item) container[randomIndex];

    if(randomIndex == size - 1){
      container[randomIndex] = null;
    } else {
      container[randomIndex] = container[size - 1];
      container[size - 1] = null;
    }
    size--;

    if(size == container.length / 4) {
      resize(container.length / 2);
    }

    return result;
  }

  public Item sample() {
    assertNotEmpty();
    return (Item) container[randomIndex()];
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private int randomIndex() {
    return StdRandom.uniform(size);
  }

  private void assertNotEmpty() {
    if(isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  private void resize(int capacity) {
    Object[] newContainer = new Object[capacity];

    for (int i = 0; i < size; i++) {
      newContainer[i] = container[i];
    }
    container = newContainer;
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    private int currentIndex;
    private Item[] items;

    public RandomizedQueueIterator() {
      copyArray();
      StdRandom.shuffle(items);
    }

    private void copyArray() {
      items = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        items[i] = (Item) container[i];
      }
    }

    @Override
    public boolean hasNext() {
      return currentIndex < size;
    }

    @Override
    public Item next() {
      if(!hasNext()) {
        throw new NoSuchElementException();
      }
      return items[currentIndex++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
