package infopulse.galkin.lesson6;

class ListFullException extends Exception{}
class Queue{
    private Node first;
    int size=0;
    int maxCapacity;
    private class Node{
        int data;
        Node next;
    }

    public Queue(int maxCapacity){
        this.maxCapacity=maxCapacity;
    }

    public void add(int element){
        try{
            if(size==maxCapacity){
                throw new ListFullException();
            }
            Node temp=first;
            while(temp.next!=null){
                temp=temp.next;
            }
            temp.next=new Node();
            temp=temp.next;
            temp.data=element;

        } catch(ListFullException e){

        }
    }

}

    class Test1 {
        public static void main(String[] args) {

        }
    }
