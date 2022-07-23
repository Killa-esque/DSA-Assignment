import java.util.NoSuchElementException;
import java.util.Stack;

public class StudentLinkedList {
    private Node head;
    private Stack<Pair> actions;

    public StudentLinkedList() {
        this.head = null;
        actions = new Stack<Pair>();
    }

    //PRIVATE METHODS
    private void addFirst(Student sv){
        head = new Node(sv,head);
    }

    private boolean constain(Student sv) throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException("Empty linked list");
        Node runNode = head;
        while (runNode.getNext() != null){
            if (runNode.getData().getId() == sv.getId()){
                return true;
            }
            runNode = runNode.getNext();
        }
        return false;
    }

    private boolean constain(int id) throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException("Empty linked list");
        Node runNode = head;
        while (runNode != null){
            if (runNode.getData().getId() == id){
                return true;
            }
            runNode = runNode.getNext();
        }
        return false;
    }

    private boolean constain(String name) throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException("Empty linked list");
        Node runNode = head;
        while (runNode != null){
            if (runNode.getData().getName() == name){
                return true;
            }
            runNode = runNode.getNext();
        }
        return false;
    }

    private void removeFirst()throws RuntimeException{
        if(isEmpty()) throw new RuntimeException("Empty Linked List");
        else{
            head = head.getNext();
        }
    }

    private Node getLast (){
        if (isEmpty()) return null;
        Node runNode = head;
        while (runNode.getNext() != null){
            runNode = runNode.getNext();
        }
        return runNode;
    }

    private void removeLast() throws NoSuchElementException{
        if (isEmpty()) throw new NoSuchElementException();
        Node currentNode = head;
        Node preNode = null;
        if (currentNode.getNext() == null){
            removeFirst();
        }
        while (currentNode.getNext() != null){
            preNode = currentNode;
            currentNode = currentNode.getNext();
        }
        preNode.setNext(currentNode.getNext());
    }


    // Requirement 1
    public boolean addStudent(Student sv) {
        if (isEmpty() || head.getData().getId() > sv.getId()){
            addFirst(sv);
            return true;
        }
        else if (head.getData().getId() == sv.getId()){
            return false;
        }
        else {
            Node current = head;
            while (current.getNext() != null && current.getNext().getData().getId() < sv.getId() ){
                if (constain(sv)) return false;
                current = current.getNext();
            }
            Node newNode = new Node(sv,current.getNext());
            current.setNext(newNode);
        }
        return true;
    }

    // Requirement 2
    public boolean deleteStudent(int id) {
        // code here
        if (isEmpty()) throw new NoSuchElementException();
        else if (!constain(id)){
            System.out.println("1. Student not found");
            return false;
        }
        else if (head.getData().getId() == id){
            System.out.println("2. Head deleted");
            removeFirst();
            return true;
        }
        else if (getLast().getData().getId() == id){
            System.out.println("3. Tail deleted");
            removeLast();
            return true;
        }
        else {
            System.out.println("4. Student deleted");
            Node current = head;
            Node preNode = null;
            while (current.getNext() != null && current.getData().getId() != id){
                preNode = current;
                current = current.getNext();
            }
            preNode.setNext(current.getNext());
            return true;
        }
    }

    // Requirement 3
    public boolean modifyName(int id, String name) {
        // code here
        if (isEmpty()) throw new NoSuchElementException();
        else if (!constain(id)){return false;}
        else {
            Node current = head;
            while (current != null){
                if (current.getData().getId() == id){
                    System.out.println(current.getData().getId());
                    current.getData().setName(name);
                    return true;
                }
                current = current.getNext();
            }
            return true;
        }
    }

    // Requirement 4
    public Student getHighestScore() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();
        Node currentNode = head;
        double max = head.getData().getGpa();
        Student studentReturn = null;
        while (currentNode.getNext() != null){
            if (currentNode.getData().getGpa() > max){
                max = currentNode.getData().getGpa();
                studentReturn = currentNode.getData();
            }
            currentNode = currentNode.getNext();
        }
        return studentReturn;
    }

    // Requirement 5
    public StudentLinkedList getGraduateStudents(int year)  {
        // code here
        StudentLinkedList newList = new StudentLinkedList();
        Node currentNode = head;
        while (currentNode != null){
            if (year - currentNode.getData().getEnrollmentYear() > 4){
                newList.addStudent(currentNode.getData());
            }
            currentNode = currentNode.getNext();
        }
        return newList;
    }

    // Requirement 6
    public StudentLinkedList findByName(String str) {
        // code here
        if (isEmpty()) return null;
        StudentLinkedList newList = new StudentLinkedList();
        Node currentNode = head;
        while (currentNode != null){
            if (currentNode.getData().getName().contains(str)){
                newList.addStudent(currentNode.getData());
            }
            currentNode = currentNode.getNext();
        }
        return newList;
    }

    // Requirement 7
    public boolean undo() {
        // code here
        return true;
    }

    // Student don't modify the methods below

    public String toString() {
        String result = "";
        if (head == null) {
            return "No item";
        }
        Node temp = head;
        while (temp.getNext() != null) {
            result += temp.getData() + "\n";
            temp = temp.getNext();
        }
        return result += temp.getData();
    }

    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        return false;
    }
}
