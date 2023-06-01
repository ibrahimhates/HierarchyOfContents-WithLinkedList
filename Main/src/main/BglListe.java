
package main;

public class BglListe {
    Node Head;
    static class Node{
        BglListe altNext;
        private String title;
        Node Next;
        public Node(String title) {
            this.Next = null;
            altNext = null;
            this.title = title;
        }
        public String getTitle() {
            return title;
        }
    }
}
