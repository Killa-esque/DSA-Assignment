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

    public void addLast(Student sv) throws NoSuchElementException {
        if(isEmpty()) {addFirst(sv);}
        Node runNode = head;
        while (runNode.getNext() != null){
            runNode = runNode.getNext();
        }
        Node newNode = new Node(sv,runNode.getNext());
        runNode.setNext(newNode);
    }

    private boolean constain(Student sv){
        if (isEmpty()) return false;
        Node runNode = head;
        while (runNode.getNext() != null){
            if (runNode.getData().getId() == sv.getId()){
                return true;
            }
            runNode = runNode.getNext();
        }
        return false;
    }

    private boolean constain(int id){
        if (isEmpty()) return false;
        Node runNode = head;
        while (runNode != null){
            if (runNode.getData().getId() == id){
                return true;
            }
            runNode = runNode.getNext();
        }
        return false;
    }

    private void removeFirst()throws NoSuchElementException{
        if(isEmpty()) throw new NoSuchElementException();
        else{
            head = head.getNext();
        }
    }

    private void storeAction(Node node, String action){
        actions.push(new Pair(node, action));
    }



    // Requirement 1
    public boolean addStudent(Student sv) {
        if (isEmpty() || head.getData().getId() > sv.getId()){
            addFirst(sv);
            storeAction(head, "add");

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
            storeAction(newNode, "add");
        }
        return true;
    }

    // Requirement 2
    public boolean deleteStudent(int id) throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        else if (!constain(id)){
            return false;
        }
        else if (head.getData().getId() == id){
            removeFirst();
            storeAction(head, "remove");
            return true;
        }
        else {
            Node current = head;
            Node pre = null;
            while (current.getNext() != null && current.getData().getId() != id){
                pre = current;
                current = current.getNext();
            }
            pre.setNext(current.getNext());
            storeAction(current, "remove");
            return true;
        }
    }

    // Requirement 3
    public boolean modifyName(int id, String name) {
        if (!constain(id)){return false;}
        else {
            Node current = head;
            while (current != null){
                if (current.getData().getId() == id){
                    current.getData().setName(name);
                    return true;
                }
                current = current.getNext();
            }
            return true;
        }
    }

    // Requirement 4
    public Student getHighestScore() {
        Node currentNode = head;
        double max = head.getData().getGpa();
        Student studentReturn = head.getData();
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
        if (actions.isEmpty()){
            return false;
        }
        else {
            Pair pair = actions.pop();
            Pair tmp;
            switch (pair.getAction()){
                case "add":
                    deleteStudent(pair.getNode().getData().getId());
                    tmp = actions.pop();
                    return true;
                case "remove":
                    addStudent(pair.getNode().getData());
                    tmp = actions.pop();
                    return true;
                default:
                    return false;
            }
        }
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
