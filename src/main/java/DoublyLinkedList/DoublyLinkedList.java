package DoublyLinkedList;

public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /*
    Clase Nodo que tiene como atributos el propio dato y los punteros al nodo anterior y al siguiente
     */
    public static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }

        public T getData(){
            return data;
        }

        public Node<T> getPrev(){
            return prev;
        }

        public Node<T> getNext(){
            return next;
        }
    }

    /*
    Metodo para anadir un nodo al principio de la lista
    Si la lista esta vacia se anade y head y tail son el nuevo nodo
    Si no el puntero next apunta a head y head es el nuevo nodo
     */

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /*
    Metodo para anadir un nodo al final de la lista
    Si la lista esta vacia se anade y head y tail son el nuevo nodo
    Si no el puntero prev apunta a tail y tail es el nuevo nodo
    */
    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    /*
    Metodo para anadir un nodo en la posicion indicada
    Si la posicion es menor que 0 o mayor que size se lanza la excepcion IndexOutOfBoundsException
    Si la posicion es 0 o size se utilizan los metodos creados anteriormente (addFirst, addLast)
    Sino se utiliza el metodo getNodeAt para obtener el nodo donde se va insertar y se ajusta el anterior,
    el siguiente y el size
     */
    public void addAt(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(data);
        } else if (index == size) {
            addLast(data);
        } else {
            Node<T> newNode = new Node<>(data);
            Node<T> current = getNodeAt(index);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;

            size++;
        }
    }

    /*
    Metodo para eliminar el primer nodo de la lista
    Si el primero de la lista esta vacio significa que la lista esta vacia y se lanza excepcion
     */
    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("The list is empty");
        }

        T data = head.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return data;
    }

    /*
    Metodo para eliminar el ultimo nodo de la lista
    Si la cola de la lista esta vacia significa que la lista esta vacia y se lanza excepcion
     */
    public T removeLast() {
        if (tail == null) {
            throw new IllegalStateException("The list is empty");
        }

        T data = tail.data;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return data;
    }

    /*
    Metodo para eliminar un nodo en la posicion indicada
    Si la posicion es menor que 0 o mayor que size se lanza la excepcion IndexOutOfBoundsException
    Si la posicion es 0 o size se utilizan los metodos creados anteriormente (removeFirst, removeLast)
    Sino se utiliza el metodo getNodeAt para obtener el nodo donde que se va a eliminar y se ajusta el anterior,
    el siguiente y el size
     */
    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> current = getNodeAt(index);
            T data = current.data;

            current.prev.next = current.next;
            current.next.prev = current.prev;

            size--;
            return data;
        }
    }

    /*
    Metodo para obtener el nodo en la posicion indicada
    Si la posicion es menor que 0 o mayor que size se lanza la excepcion IndexOutOfBoundsException
    Se recorre empezando por el primer nodo toda la lista hasta encontrar el de la posicion indicada y se devuelve
     */
    Node<T> getNodeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current;
    }
    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /*
    Metodo para imprimir la lista
     */
    public String printList() {
        Node<T> actual = head;
        StringBuilder result = new StringBuilder("DoublyLinkedList: [");
        
        while (actual != null) {
            result.append(actual.data);
            
            if (actual.next != null) {
                result.append(", ");
            }
            
            actual = actual.next;
        }
        result.append("]");
        
        return result.toString();
    }
}

